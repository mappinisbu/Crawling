package main;

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

//import org.jsoup.Connection;
//import org.jsoup.Connection.Response;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

import java.net.URL;
import java.net.URLConnection;

import objects.Result;


public class PageCrawler {
	
	static int maxPages = 30;
	static String domain = "https://paypal.com";
	static HashSet<String> urlsTraversed = new HashSet<String>();
	static HashSet<Result> results = new HashSet<Result>();
	public static void Crawl(String domainName, int numberPages)  {
    
		domain = domainName;
		maxPages = numberPages;
		
		processPage(domain);

	}

	private static void processPage(String url) {
		
		while(!PageCrawler.urlsTraversed.contains(url))
		{
			if(urlsTraversed.size() == maxPages)
				return;
			
			PageCrawler.urlsTraversed.add(url);
			//Connection urlConn = null;
			HTMLDocument doc = null;
			
			System.out.println("==================================");
			System.out.println("Trying to connect to  url:" + url);
			try {
				
				//urlConn = Jsoup.connect(url);
				//doc = urlConn.get();
				
				URL obj = new URL(url);
				URLConnection conn = obj.openConnection();
				InputStreamReader rd = new InputStreamReader(conn.getInputStream());
				BufferedReader br = new BufferedReader(rd);
				String rawHTML = getStringFromBufferedReader(br);
				
				EditorKit kit = new HTMLEditorKit();
			    doc = (HTMLDocument) kit.createDefaultDocument();
			    doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
			    kit.read(new InputStreamReader(new ByteArrayInputStream(rawHTML.getBytes())), doc, 0);
			    
			    // Also read into a jsoup object for parsing
			   
				/*
				String contentType= conn.getContentType();
				if (contentType.contains("html")) {...} 
				*/
				
				Map<String, List<String>> urlRespMap = conn.getHeaderFields();
				
				printHeaders(urlRespMap);
								
				Result resultObj=Initialize.StartSecurityChecks(url, urlRespMap, rawHTML);
				resultObj.setUrlName(url);
				PageCrawler.results.add(resultObj);
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
				return;
			}
			
			HTMLDocument.Iterator i = doc.getIterator(HTML.Tag.A);
			
			while (i.isValid()){
				SimpleAttributeSet saSet = (SimpleAttributeSet) i.getAttributes();
				String hyperlink = (String) saSet.getAttribute(HTML.Attribute.HREF);
				//add code to handle relative links by prepending the current page's URL
				
				String newLink=filterLinks(hyperlink);
				if (newLink != null){
					processPage(newLink);
				}
				i.next();
			}
			
		}
		
		System.out.println("Crawling Finished");
		
	}
	
	private static String getStringFromBufferedReader(BufferedReader br) {
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

	public static String filterLinks(String hyperlink ) {
		if(hyperlink == null)
			return null;
		
		if (hyperlink.equals(null)) 
			return null;
		
		if(hyperlink.isEmpty())
			return null;
		
		if (hyperlink.charAt(0)=='#') return null;
		
		if(hyperlink.contains("javascript"))
		{
			return null;
		}
		
		if (hyperlink.charAt(0)=='/'){
			//Add code to process relative links
			return null;
		}
		
		if(hyperlink.contains("shtml"))
		{
			return null;
		}
		
		return hyperlink;	
	}
	
	public static void printHeaders(Map<String, List<String>> urlRespMap ) {
		
		System.out.println("Printing All Response Headers");
		
		for (Map.Entry<String, List<String>> mapEntry: urlRespMap.entrySet()){
			System.out.println(mapEntry.getKey() + " : " + mapEntry.getValue());
		}
		
		System.out.println("----------End of headers-----------");
		
	}
	
	
	
	public static int[] examineCookies(Map<String, List<String>> urlRespMap ) {
		
		int cookieCount=0,secureCookieCount=0,httpOnlyCookieCount=0;
		List<String> cookieContent = null;
		/*
		for (Map.Entry<String, List<String>> mapEntry: urlRespMap.entrySet()){
			//System.out.println(mapEntry.getKey() + " : " + mapEntry.getValue());
			if (mapEntry.getKey()!=null){
				if (mapEntry.getKey().equals("Set-Cookie")){
					cookieContent = mapEntry.getValue();
					cookieCount=cookieContent.size();
				}
			}
			
		}
		*/
		
		cookieContent = urlRespMap.get("Set-Cookie");
		if (cookieContent != null) 
			cookieCount=cookieContent.size();
		

		if (cookieCount>0){
			System.out.println("----------Cookie values:-----------");
			for(String str: cookieContent){
				System.out.println(str);
				if(str.contains("HttpOnly")) httpOnlyCookieCount++;
				if(str.contains("Secure")) secureCookieCount++;
			}
			
			System.out.println("----------End of cookies-----------");
		}
		
		
		System.out.println("Total number of cookies= "+cookieCount );
		System.out.println("Total number of secure cookies= "+secureCookieCount );
		System.out.println("Total number of HttpOnly cookies= "+httpOnlyCookieCount );
		
		int[] cookieCounts=new int[3];
		cookieCounts[0]=cookieCount ;
		cookieCounts[1]=secureCookieCount ;
		cookieCounts[2]=httpOnlyCookieCount ;
		
		return cookieCounts;
	}
	
	
	
	
	
	/*
	 * Clear results from previous crawl
	 */
	public static void clearResults() {
		PageCrawler.urlsTraversed = new HashSet<String>();
		PageCrawler.results = new HashSet<Result>();
	}
		
	/*
	 * Get urls that where previously traversed
	 */
	public static HashSet<String> getUrls() {
		return PageCrawler.urlsTraversed;
	}

	public static HashSet<Result> getResults() {
		return PageCrawler.results;
	}
}
