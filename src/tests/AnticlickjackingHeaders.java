package tests;

import java.util.List;
import java.util.Map;

//import org.jsoup.Connection.Response;

import objects.Result;

public class AnticlickjackingHeaders {

	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {
		System.out.print("Anti-Click-Jacking header: ");
		if(urlRespMap.containsKey("???")){
			System.out.print("Found\n");
		    resultObj.setAntiClickEnabled(true); 
		}else{
			System.out.print("Not found!\n");
			resultObj.setAntiClickEnabled(false);
		}	
	
	}
}
