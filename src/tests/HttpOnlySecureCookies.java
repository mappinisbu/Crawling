package tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Map;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import java.net.MalformedURLException;

import main.PageCrawler;

//import org.jsoup.Connection.Response;

import objects.Result;

public class HttpOnlySecureCookies {

	
	public static void StartTest(URL urlobj, Map<String, List<String>> urlRespMap, String rawHTML, Result resultObj) {
		
		StringBuilder details=new StringBuilder();
		int[] cookieCounts= examineHTMLCookies(urlRespMap, details);
		int cookieCount = cookieCounts[0];
		int secureCookieCount = cookieCounts[1];
		int httpOnlyCookieCount = cookieCounts[2];
		
		details.append("Cookies set in HTML response header:<br>");
		details.append("Total Cookies = "+ cookieCount +"<br>"+
					   "Secure cookies = "+secureCookieCount+"<br>"+
					   "HttpOnly cookies = "+ httpOnlyCookieCount+ "<br>");
		details.append("-------------------------------------<br>");
		
		int[] scriptCookieCounts= examineScriptCookies(rawHTML,urlobj);
		details.append("Javascript Cookies:<br>");
		details.append("Total Cookies = "+ scriptCookieCounts[0] +"<br>"+
					   "Secure cookies = "+scriptCookieCounts[1]+"<br>");
		
		cookieCount+=scriptCookieCounts[0];
		secureCookieCount+=scriptCookieCounts[1];
		
		if(cookieCount>0 && secureCookieCount >0 && httpOnlyCookieCount > 0){
			//System.out.print("HttpOnly / secure cookies: ");
			//System.out.print("Found\n");
		    resultObj.setHttpOnlyEnabled(true); 
		}else if(cookieCount==0){
			resultObj.setHttpOnlyEnabled(true);
		}else{
			//System.out.print("HttpOnly / secure cookies: ");
			//System.out.print("Not found!\n");
			resultObj.setHttpOnlyEnabled(false);
		}	
		
	
			
		resultObj.setHttpOnlyDetails(details.toString());
		
	}	
	
	public static int[] examineHTMLCookies(Map<String, List<String>> urlRespMap, StringBuilder details ) {
		
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
		if (cookieContent==null)
			cookieContent = urlRespMap.get("Set-cookie");
		
		if (cookieContent != null) 
			cookieCount=cookieContent.size();
		
	
		if (cookieCount>0){
			details.append("----------Cookie values:-----------<br>");
			for(String str: cookieContent){
				details.append(str+"<br>");
				if(str.contains("HttpOnly")||str.contains("httponly")) httpOnlyCookieCount++;
				if(str.contains("Secure")||str.contains("secure")) secureCookieCount++;
			}
			
			details.append("----------End of cookies-----------<br>");
		}
		
		
		//System.out.println("Total number of cookies= "+cookieCount );
		//System.out.println("Total number of secure cookies= "+secureCookieCount );
		//System.out.println("Total number of HttpOnly cookies= "+httpOnlyCookieCount );
		
		int[] cookieCounts=new int[3];
		cookieCounts[0]=cookieCount ;
		cookieCounts[1]=secureCookieCount ;
		cookieCounts[2]=httpOnlyCookieCount ;
		
		return cookieCounts;
	}
	
	
	public static int[] examineScriptCookies(String rawHTML,URL urlobj){
		//System.out.println("Examining script coookies");
		
		int[] scriptCookieCounts=new int[2];
		scriptCookieCounts[0]=0 ;
		scriptCookieCounts[1]=0 ;
		
		
		Document doc = Jsoup.parse(rawHTML);
		Elements scriptElements = doc.getElementsByTag("script");
		
		for (org.jsoup.nodes.Element element : scriptElements) {
		
			String javascriptString = "";
			String jsSrc = element.attr("src");
						
			if(jsSrc != null && !jsSrc.isEmpty())
		    {
		    	// fetch the java script source and sanitize url
				//System.out.println(" examining java script source: " + jsSrc);
				jsSrc = PageCrawler.filterLinks(jsSrc, urlobj);
				//System.out.println(" examining java script absolute source: " + jsSrc);
				URL url;
				if(jsSrc != null && !jsSrc.isEmpty())
				{
				    try {
					    url = new URL(jsSrc);
					    URLConnection connection = url.openConnection();
					    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
					    BufferedReader br = new BufferedReader(isr);
					    javascriptString = PageCrawler.getStringFromBufferedReader(br);
				    } catch (MalformedURLException e) {					
					    e.printStackTrace();
				    } catch (IOException e) {
					    // printing and ignoring error
					    e.printStackTrace();
				    }
		    	
		        }
		    }
			
			else
			{
				 for (DataNode node : element.dataNodes()) {
			            //System.out.println(node.getWholeData());
					 javascriptString = javascriptString + node.getWholeData();
			        }
			
			}
			
			if(javascriptString == null || javascriptString.isEmpty())
			{
				continue;
			}
			
			String docCookie1 = "document.cookie =";
			String docCookie2 = "document.cookie=";
			String secureCookie = ";secure"; // even when cookies are read by js this might be falsely set. Should be removed?
			
			int lastIndex = 0;
			while(lastIndex != -1){

			    lastIndex = javascriptString.indexOf(docCookie1,lastIndex);

			    if(lastIndex != -1){
			    	scriptCookieCounts[0] = scriptCookieCounts[0] + 1;
			        lastIndex += docCookie1.length();
			    }
			}
			
			lastIndex = 0;
			while(lastIndex != -1){

			    lastIndex = javascriptString.indexOf(docCookie2,lastIndex);

			    if(lastIndex != -1){
			    	scriptCookieCounts[0] = scriptCookieCounts[0] + 1;
			        lastIndex += docCookie2.length();
			    }
			}
			
			lastIndex = 0;
			while(lastIndex != -1){

			    lastIndex = javascriptString.indexOf(secureCookie,lastIndex);

			    if(lastIndex != -1){
			    	scriptCookieCounts[1] = scriptCookieCounts[1] + 1;
			        lastIndex += secureCookie.length();
			    }
			}
		}
		//System.out.println("Total number of cookies= "+scriptCookieCounts[0] );
		//System.out.println("Total number of secure cookies= "+scriptCookieCounts[1]);
		return scriptCookieCounts;
	}

}
