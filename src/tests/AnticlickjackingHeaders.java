package tests;

import java.util.List;
import java.util.Map;

//import org.jsoup.Connection.Response;

import objects.Result;

public class AnticlickjackingHeaders {

	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {
		List<String> xframe = null;
		boolean antiClickEnabled = false;
		System.out.print("Anti-Click-Jacking header: ");
		if(urlRespMap.containsKey("X-Frame-Options") || urlRespMap.containsKey("X-FRAME-OPTIONS") ){   
			xframe = urlRespMap.get("X-Frame-Options");
			if (xframe == null) 
				xframe = urlRespMap.get("X-FRAME-OPTIONS");
			if (xframe !=null){
				for (String value : xframe) {
				    if (value.equalsIgnoreCase("deny") || value.equalsIgnoreCase("sameorigin")){
				    	antiClickEnabled = true;
				    	break;
				    }
				}
			}
		}
		
		
		if(antiClickEnabled){
			System.out.print("Found\n");
		    resultObj.setAntiClickEnabled(true); 
		}else{
			System.out.print("Not found!\n");
			resultObj.setAntiClickEnabled(false);
		}	
	
	}
}
