package tests;

import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import main.PageCrawler;

//import org.jsoup.Connection.Response;

import objects.Result;

public class HttpOnlySecureCookies {
	public static StringBuilder details;
	
	public static void StartTest(Map<String, List<String>> urlRespMap, String rawHTML, Result resultObj) {
		
		details=new StringBuilder();
		int[] cookieCounts= examineHTMLCookies(urlRespMap);
		int cookieCount = cookieCounts[0];
		int secureCookieCount = cookieCounts[1];
		int httpOnlyCookieCount = cookieCounts[2];
		
		if(cookieCount>0 && secureCookieCount >0 && httpOnlyCookieCount > 0){
			System.out.print("HttpOnly / secure cookies: ");
			System.out.print("Found\n");
		    resultObj.setHttpOnlyEnabled(true); 
		}else{
			System.out.print("HttpOnly / secure cookies: ");
			System.out.print("Not found!\n");
			resultObj.setHttpOnlyEnabled(false);
		}	
		
		details.append("Total Cookies = "+ cookieCount +"<br>"+
					   "Secure cookies = "+secureCookieCount+"<br>"+
					   "HttpOnly cookies = "+ httpOnlyCookieCount);
		
		examineScriptCookies(rawHTML);
		
		resultObj.setHttpOnlyDetails(details.toString());
		
	}	
	
	public static int[] examineHTMLCookies(Map<String, List<String>> urlRespMap ) {
		
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
		if (cookieContent==null)
			cookieContent = urlRespMap.get("SET-COOKIE");
		
		if (cookieContent != null) 
			cookieCount=cookieContent.size();
		
	
		if (cookieCount>0){
			details.append("----------Cookie values:-----------<br>");
			for(String str: cookieContent){
				details.append(str+"<br>");
				if(str.contains("HttpOnly")) httpOnlyCookieCount++;
				if(str.contains("Secure")) secureCookieCount++;
			}
			
			details.append("----------End of cookies-----------<br>");
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
	
	public static void examineScriptCookies(String rawHTML){
		System.out.println("Examining script coookies");
		Document doc = Jsoup.parse(rawHTML);
		Elements scriptElements = doc.getElementsByTag("script");
		for (org.jsoup.nodes.Element element : scriptElements){
			System.out.println(element.toString());
		}
			
	}
	
}
