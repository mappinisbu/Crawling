package tests;

import org.jsoup.Connection.Response;

public class HttpStrictTransportPolicy {
	
	public static void StartTest(Response urlResp, Result resultObj) {
		System.out.print("Strict-Transport-Security header: ");
		if(urlResp.hasHeader("Strict-Transport-Security")){
			System.out.print("Found\n");
		    resultObj.setStrictEnabled(true); 
		}else{
			System.out.print("Not found!\n");
			resultObj.setStrictEnabled(false);
		}	
	}
	
	
	
	
	
}
