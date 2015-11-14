package tests;

import org.jsoup.Connection.Response;

import objects.Result;

public class Nonces {
	
	public static void StartTest(Response urlResp, Result resultObj) {
		System.out.print("Nonces header: ");
		if(urlResp.hasHeader("???")){
			System.out.print("Found\n");
		    resultObj.setNoncesEnabled(true); 
		}else{
			System.out.print("Not found!\n");
			resultObj.setNoncesEnabled(false);
		}	
	}	
}
