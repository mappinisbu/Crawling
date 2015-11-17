package tests;

import java.util.List;
import java.util.Map;

//import org.jsoup.Connection.Response;

import objects.Result;

public class Nonces {
	
	public static void StartTest(Map<String, List<String>> urlRespMap, Result resultObj) {
		System.out.print("Nonces header: ");
		if(urlRespMap.containsKey("???")){
			System.out.print("Found\n");
		    resultObj.setNoncesEnabled(true); 
		}else{
			System.out.print("Not found!\n");
			resultObj.setNoncesEnabled(false);
		}	
	}	
}
