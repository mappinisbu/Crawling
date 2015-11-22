package tests;

import java.util.List;
import java.util.Map;

import main.ResultHelper;

//import org.jsoup.Connection.Response;

import objects.Result;

public class AnticlickjackingHeaders {

	
	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {
		

		List<String> xframe = null;
		boolean antiClickEnabled = false;
		String details = "Not found";
		
		System.out.print("Anti-Click-Jacking header: ");
		antiClickEnabled = false;
		
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
		
		if (details.length()==0)
			details="X-Frame-Options header not found!";
		
		resultObj.setAntiClickDetails(details);
	}
}
