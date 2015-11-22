package tests;

import java.util.List;
import java.util.Map;
import main.ResultHelper;

//import org.jsoup.Connection.Response;

import objects.Result;

public class ContentSecurityPolicy {

	
	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {
		

		boolean cspEnabled = false;
		String details = "Not found";
		
		System.out.print("Content-Security-Policy header: ");
		
		System.out.println("urlRespMap contents:");
		for (Map.Entry<String, List<String>> mapEntry: urlRespMap.entrySet()){
			if (mapEntry.getKey() != null) {
					System.out.println("Key= "+mapEntry.getKey() + "Value= "+ mapEntry.getValue());
				}
		}	
	
		
		if(urlRespMap.containsKey("Content-Security-Policy")) {
			cspEnabled = true;
		}else{
			cspEnabled = false;
		}
		
		resultObj.setCspEnabled(cspEnabled); 
		details = ResultHelper.addDetails(urlRespMap, resultObj, "Content-Security-Policy");
		if (details.length()==0)
			details="Content Security Policy header not found!";
		
		resultObj.setCspDetails(details);
    }
}
