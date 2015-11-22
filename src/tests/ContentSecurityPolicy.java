package tests;

import java.util.List;
import java.util.Map;
import main.ResultHelper;

//import org.jsoup.Connection.Response;

import objects.Result;

public class ContentSecurityPolicy {

	
	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {
		

		boolean cspEnabled = false;
		String details = "";
		
		System.out.print("Content-Security-Policy header: ");

		details = ResultHelper.addDetails(urlRespMap, resultObj, "Content-Security-Policy");
		if (details.length()==0){
			details="Content Security Policy header not found!";
			cspEnabled = false;
			System.out.println("not found ");
		}else{
			cspEnabled = true;
			System.out.println("Found");
		}
		
		resultObj.setCspEnabled(cspEnabled);
		resultObj.setCspDetails(details);
    }
}
