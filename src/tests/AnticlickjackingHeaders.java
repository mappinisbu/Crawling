package tests;

import java.util.List;
import java.util.Map;

import main.ResultHelper;

//import org.jsoup.Connection.Response;

import objects.Result;

public class AnticlickjackingHeaders {

	private static List<String> xframe = null;
	private static boolean antiClickEnabled = false;
	private static String details = "Not found";
	
	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {
		
		System.out.print("Anti-Click-Jacking header: ");
		if(urlRespMap.containsKey("X-Frame-Options") || urlRespMap.containsKey("X-FRAME-OPTIONS") ){   
			xframe = urlRespMap.get("X-Frame-Options");
			if (xframe == null) 
				xframe = urlRespMap.get("X-FRAME-OPTIONS");
			if (xframe != null){
				for (String value : xframe) {
				    if (value.equalsIgnoreCase("deny") || value.equalsIgnoreCase("sameorigin")){
				    	antiClickEnabled = true;
				    	break;
				    }
				}
			}
		}
		
		resultObj.setAntiClickEnabled(antiClickEnabled); 
		details = ResultHelper.addDetails(urlRespMap, resultObj, "X-Frame-Options");
		resultObj.setAntiClickDetails(details);
	}
}
