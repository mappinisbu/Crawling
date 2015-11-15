package tests;

import java.util.List;
import java.util.Map;

//import org.jsoup.Connection.Response;

import objects.Result;

public class ContentSecurityPolicy {

	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {
		System.out.print("Content-Security-Policy header: ");
		if(urlRespMap.containsKey("Content-Security-Policy")){
			System.out.print("Found\n");
		    resultObj.setCspEnabled(true); 
		}else{
			System.out.print("Not found!\n");
			resultObj.setCspEnabled(false);
		}	
    }
}
