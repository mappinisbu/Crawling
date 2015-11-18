package tests;

import java.util.List;
import java.util.Map;
import main.ResultHelper;

//import org.jsoup.Connection.Response;

import objects.Result;

public class ContentSecurityPolicy {

	private static boolean cspEnabled = false;
	private static String details = "Not found";
	
	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {
		
		System.out.print("Content-Security-Policy header: ");
		
		if(urlRespMap.containsKey("Content-Security-Policy")) {
			cspEnabled = true;
		}
		
		resultObj.setCspEnabled(cspEnabled); 
		details = ResultHelper.addDetails(urlRespMap, resultObj, "Content-Security-Policy");
		resultObj.setCspDetails(details);
    }
}
