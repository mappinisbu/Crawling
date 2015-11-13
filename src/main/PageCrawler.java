package main;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tests.Result;


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

	private static void processPage(String url){
		
		while(!PageCrawler.urlsTraversed.contains(url))
		{
			if(urlsTraversed.size() == maxPages)
				return;
			
			PageCrawler.urlsTraversed.add(url);
			Connection urlConn = null;
			Document doc;
			System.out.println("==================================");
			System.out.println("Trying to connect to  url:" + url);
			try {
				
				urlConn = Jsoup.connect(url);
				doc = urlConn.get();
			} catch (IOException e) {
				
				e.printStackTrace();
				return;
			}
			Response urlResp = urlConn.response();
			//System.out.println("URLResp-->" + urlResp.body());
			Map<String,String> urlRespMap = urlResp.headers();
			
			System.out.println("Printing All Response Headers for URL: " + url + "\n");
 
			for (Map.Entry<String,String> mapEntry: urlRespMap.entrySet()){
				System.out.println(mapEntry.getKey() + " : " + mapEntry.getValue());
			}
			
			System.out.println("----------End of headers-----------");
			
			Result resultObj=Initialize.StartSecurityChecks(urlResp);
			resultObj.setUrlName(url);
			PageCrawler.results.add(resultObj);
			
			/*
			if(urlResp.hasHeader("Strict-Transport-Security"))
				System.out.println("secure header: " + url);
			else
				System.out.println("non secure header: "+ url);
		
			*/
			
			
			//get all links and recursively call the processPage method
			Elements questions = doc.select("a[href]");
			for(Element link: questions){
				//if(link.attr("href").contains("stonybrook.edu"))
					processPage(link.attr("abs:href"));
			}
		}
		
		System.out.println("Crawling Finished");
		
		//setUrls(urlsTraversed);
		
	}
	
	/* Set urls that where traversed
	 * @param urlsTraversed
	 */
	public static void setUrls(HashSet<String> urlsTraversed) {
		PageCrawler.urlsTraversed = urlsTraversed;
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
