package tests;

import java.util.List;
import java.util.Map;

import main.PageCrawler;

//import org.jsoup.Connection.Response;

import objects.Result;

public class HttpOnlySecureCookies {
	
	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {
		
		
		int[] cookieCounts= PageCrawler.examineCookies(urlRespMap);
		int cookieCount = cookieCounts[0];
		int secureCookieCount = cookieCounts[1];
		int httpOnlyCookieCount = cookieCounts[2];
		
		if(secureCookieCount >0 && httpOnlyCookieCount > 0){
			System.out.print("HttpOnly / secure cookies: ");
			System.out.print("Found\n");
		    resultObj.setHttpOnlyEnabled(true); 
		}else{
			System.out.print("HttpOnly / secure cookies: ");
			System.out.print("Not found!\n");
			resultObj.setHttpOnlyEnabled(false);
		}	
		
		String details="<html>"+"Total Cookies = "+ cookieCount +"<br>"+
					   "Secure cookies = "+secureCookieCount+"<br>"+
					   "HttpOnly cookies = "+ httpOnlyCookieCount+"</html>";
		
		resultObj.setHttpOnlyDetails(details);
		
	}	
}
