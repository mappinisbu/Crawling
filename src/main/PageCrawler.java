package main;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Map;
import java.util.List;

import javax.swing.text.EditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTML;
//import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import java.net.MalformedURLException;

//import org.jsoup.Connection;
//import org.jsoup.Connection.Response;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

import java.net.URL;
import java.net.URLConnection;

import objects.Result;


public class PageCrawler implements Runnable{
	
	ConcurrentLinkedQueue<String> queue;
	Set<String> urlsTraversed;
	int maxPage;
	AtomicIntegerArray array;
	int threadNum;
	Set<Result> resultSet;
	private volatile boolean flag = true;

	//static HashSet<String> urlsTraversed = new HashSet<String>();
	//static HashSet<Result> results = new HashSet<Result>();

	public PageCrawler(ConcurrentLinkedQueue<String> urlQueue, Set<String> urlSet, int maxPage, AtomicIntegerArray arr, int num, Set<Result> resultSet){
		this.queue = urlQueue;
		this.urlsTraversed = urlSet;
		this.maxPage = maxPage;
		this.array = arr;
		this.threadNum = num;
		this.resultSet = resultSet;
		
	}
	
	public void run(){
		//System.out.println("************************PageCrawler "+threadNum+" started***************************");
	/*	Pop from the queue and check if the url was already crawled.
		If not put it to the hashSet.
		Start crawling.
		Put all the found urls to the Queue if not in the hashset already.
		Check the 5 policies 
	*/
		
		String url;
		int count=1;
		int numPagesAdded = 0;
		while(resultSet.size() < maxPage && flag == true ){
			
			if(queue.size()>0){
				url = queue.poll();
				
				if(url!=null && !urlsTraversed.contains(url)){
					
					//if(urlsTraversed.size() == maxPages)
						//return;
					
					
					
					//System.out.println("Start crawling page "+url);
					count++;
					//Connection urlConn = null;
					HTMLDocument doc = null;
					URL urlobj = null;
					
					//System.out.println("==================================");
					System.out.println("PageCrawler "+threadNum+": "+"Crawling " + url);
					
					try {
						int numUrlAdded=0;
						//urlConn = Jsoup.connect(url);
						//doc = urlConn.get();
						
						urlobj = new URL(url);
						//System.out.println("Domain = "+ urlobj.getHost());
						URLConnection conn = urlobj.openConnection();
						conn.setRequestProperty("Accept", "application/xml,application/xhtml+xml,text/html,text/plain,*/*");
						InputStreamReader rd = new InputStreamReader(conn.getInputStream());
						BufferedReader br = new BufferedReader(rd);
						String rawHTML = getStringFromBufferedReader(br);
						
						EditorKit kit = new HTMLEditorKit();
					    doc = (HTMLDocument) kit.createDefaultDocument();
					    doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
					    kit.read(new InputStreamReader(new ByteArrayInputStream(rawHTML.getBytes())), doc, 0);
					    
					    // Also read into a jsoup object for parsing
					    Response res=Jsoup.connect(url).execute();
					    Map<String, String> cookies = res.cookies();
					    
					   
						/*
						String contentType= conn.getContentType();
						if (contentType.contains("html")) {...} 
						*/
						
					    
					    HTMLDocument.Iterator i = doc.getIterator(HTML.Tag.A);
						
						while (i.isValid()){
							SimpleAttributeSet saSet = (SimpleAttributeSet) i.getAttributes();
							String hyperlink = (String) saSet.getAttribute(HTML.Attribute.HREF);
							//add code to handle relative links by prepending the current page's URL
							String newLink=filterLinks(hyperlink,urlobj);
							System.out.println("Raw link= "+hyperlink+"\n"+"Parsed hyperlink = "+newLink+"\n");
							if (newLink != null && !queue.contains(newLink) && !urlsTraversed.contains(newLink)){
								//processPage(newLink);
								queue.add(newLink);
								numUrlAdded++;
							}
							i.next();
						}
						
						//******Write the number of pages added to the array******
						array.set(threadNum,numUrlAdded);
					    
					    
					    
						Map<String, List<String>> urlRespMap = conn.getHeaderFields();
						
						//printHeaders(urlRespMap);
										
						Result resultObj=Initialize.StartSecurityChecks(url, urlobj, urlRespMap, rawHTML);
						resultObj.setUrlName(url);
						urlsTraversed.add(url);
						
						if(resultSet.size()<maxPage)
							resultSet.add(resultObj);
						
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					
				}
			}
		}
		System.out.println("PageCrawler "+threadNum+" terminating.");
		//System.out.println("urlstraversed size: "+urlsTraversed.size());
		//Controller.finish();
		
	}
	
	public void finish() {
        flag = false;
    }

	public static String getStringFromBufferedReader(BufferedReader br) {
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			
			  while ((line = br.readLine()) != null)
			  {
				sb.append(line);
			  }
	
		} 
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
		return sb.toString();
	}
	
public static String filterLinks(String hyperlink,URL urlobj ) {
	
	
	
		//System.out.println("raw hyperlink = "+hyperlink);
		if(hyperlink == null) 		return null;
		if (hyperlink.equals(null)) return null;
		if (hyperlink.isEmpty())	return null;
		if (hyperlink.charAt(0)=='#') return null;
		
		
		if ( hyperlink.startsWith("http://")|| hyperlink.startsWith("https://")){
			

			if (hyperlink.endsWith(".js"))
				return hyperlink;  //domain check skipped for js files
			
			
			URL domainURL = null;
			try {
				domainURL = new URL(Controller.domainName);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			URL crawledURL = null;
			try {
				crawledURL = new URL(hyperlink);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String givenDomain= domainURL.getHost();
			String crawledUrlDomain = crawledURL.getHost();
			
			if(givenDomain.startsWith("www.")){
				givenDomain = givenDomain.substring(4);
				
			}
			
			//System.out.println("Given domain "+ givenDomain);
			int indexOfLastDot = givenDomain.lastIndexOf(".",givenDomain.length()-1);
			int indexOfSecondLastDot = givenDomain.lastIndexOf(".",indexOfLastDot-1);
			if(indexOfLastDot>-1 && indexOfSecondLastDot>=-1){
				givenDomain=givenDomain.substring(indexOfSecondLastDot+1, givenDomain.length());
			}
			
			if(crawledUrlDomain.startsWith("www.")){
				crawledUrlDomain = crawledUrlDomain.substring(4);
			}
			
			//System.out.println("crawledUrl Domain "+ crawledUrlDomain);
			indexOfLastDot = crawledUrlDomain.lastIndexOf(".",crawledUrlDomain.length()-1);
			indexOfSecondLastDot = crawledUrlDomain.lastIndexOf(".",indexOfLastDot-1);
			if(indexOfLastDot>-1 && indexOfSecondLastDot>=-1){
				crawledUrlDomain=crawledUrlDomain.substring(indexOfSecondLastDot+1, crawledUrlDomain.length());
			}
			
			if (givenDomain ==null || crawledUrlDomain ==null) return null;
			
			if(!givenDomain.equals(crawledUrlDomain) ){   
				System.out.println("###################different host: "+domainURL.getHost()+"   "+crawledURL.getHost());
				return null;
			}else
				return hyperlink;
			
		}
		
		else if (hyperlink.startsWith("../")){
			//System.out.println("Resolving link beginning with ../");
			String originalURL = urlobj.toString();
			int indexOfLastSlash=originalURL.lastIndexOf("/", originalURL.length()-1);
			//System.out.println("last slash index= "+indexOfLastSlash);
			int indexOfSecondLastSlash=originalURL.lastIndexOf("/", indexOfLastSlash-1);
			//System.out.println("Second last slash index= "+indexOfSecondLastSlash);
			if (indexOfSecondLastSlash>-1)
				return originalURL.substring(0,indexOfSecondLastSlash)+hyperlink.substring(2);
			else
				return null;
		}
		
		else if (hyperlink.startsWith("//")){  //protocol relative URL
			//System.out.println("Resolving link beginning with //");
			return filterLinks(urlobj.getProtocol()+":"+hyperlink,urlobj);  //could point to external domain, so pass again to filterLinks()
		}
		
		else if (hyperlink.charAt(0)=='/'){  //process relative links
					return urlobj.getProtocol()+"://"+urlobj.getAuthority()+hyperlink;
		}
		
		else{
			String originalURL = urlobj.toString();
			boolean endsWithSlash=false;
			int indexOfLastSlash =0;
			//check if originalurl ends with a /
			
			if (originalURL.charAt(originalURL.length()-1)=='/')
				endsWithSlash=true;
			
			if(endsWithSlash)
				return originalURL+hyperlink;

			indexOfLastSlash=originalURL.lastIndexOf("/", originalURL.length()-1);
			if (indexOfLastSlash>6) // http://
				return originalURL.substring(0,indexOfLastSlash)+"/"+hyperlink;
			else
				return originalURL+"/"+hyperlink;

				
			
			
		}	
	}	
	public static void printHeaders(Map<String, List<String>> urlRespMap ) {
		
		System.out.println("Printing All Response Headers");
		
		for (Map.Entry<String, List<String>> mapEntry: urlRespMap.entrySet()){
			System.out.println(mapEntry.getKey() + " : " + mapEntry.getValue());
		}
		
		System.out.println("----------End of headers-----------");
		
	}
	
	
	

	

}

