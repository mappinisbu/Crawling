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
		String details = "";
		
		//System.out.print("Anti-Click-Jacking header: ");
		antiClickEnabled = false;
		
		details = ResultHelper.addDetails(urlRespMap, resultObj, "X-Frame-Options");
		
		if (details.length()==0){
			details="X-Frame-Options header not found!";
			antiClickEnabled = false;
			//System.out.println("not found ");
		}else{
			antiClickEnabled = true;
			//System.out.println("Found ");
		}
		resultObj.setAntiClickEnabled(antiClickEnabled);
		resultObj.setAntiClickDetails(details);
	}
}
