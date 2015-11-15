package tests;

import java.util.List;
import java.util.Map;

//import org.jsoup.Connection.Response;

import objects.Result;

public class HttpStrictTransportPolicy {
	
	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {
		System.out.print("Strict-Transport-Security header: ");
		if(urlRespMap.containsKey("Strict-Transport-Security")){
			System.out.print("Found\n");
		    resultObj.setStrictEnabled(true); 
		}else{
			System.out.print("Not found!\n");
			resultObj.setStrictEnabled(false);
		}	
	}
	
	
	
	
	
}
