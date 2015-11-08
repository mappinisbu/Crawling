package main;

import java.io.IOException;
import java.util.HashSet;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class PageCrawler {
	
	static int maxPages = 30;
	static String domain = "https://paypal.com";
	static HashSet<String> urlsTraversed = new HashSet<String>();
	
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
			try {
				urlConn = Jsoup.connect(url);
				doc = urlConn.get();
			} catch (IOException e) {
				
				e.printStackTrace();
				return;
			}
			Response urlResp = urlConn.response();
			if(urlResp.hasHeader("Strict-Transport-Security"))
				System.out.println("secure header: " + url);
			else
				System.out.println("non secure header: "+ url);
		
			
			//get all links and recursively call the processPage method
			Elements questions = doc.select("a[href]");
			for(Element link: questions){
				//if(link.attr("href").contains("stonybrook.edu"))
					processPage(link.attr("abs:href"));
			}
		}
		
		System.out.println("Crawling Finished");
		
		setUrls(urlsTraversed);
		
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

}
