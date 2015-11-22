package tests;

import java.util.List;
import java.util.Map;
import main.ResultHelper;

//import org.jsoup.Connection.Response;

import objects.Result;

public class HttpStrictTransportPolicy {
	
	
	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {

		boolean strictEnabled = false;
		String details = "";
		
		System.out.print("Strict-Transport-Security header: ");
		 
		details = ResultHelper.addDetails(urlRespMap, resultObj, "Strict-Transport-Security");
		
		if (details.length()==0){
			details="Strict Transport Security header not found!";
			strictEnabled = false;
			System.out.print("Not Found\n");
		}else{
			strictEnabled = true;
			System.out.print("Found\n");
		}
		resultObj.setStrictEnabled(strictEnabled);
		resultObj.setStrictDetails(details);
	}
}
