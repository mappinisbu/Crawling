package tests;

import org.jsoup.Connection.Response;

import objects.Result;

public class HttpOnlySecureCookies {
	
	public static void StartTest(Response urlResp, Result resultObj) {
		System.out.print("Http Only secure header: ");
		if(urlResp.hasHeader("???")){
			System.out.print("Found\n");
		    resultObj.setHttpOnlyEnabled(true); 
		}else{
			System.out.print("Not found!\n");
			resultObj.setHttpOnlyEnabled(false);
		}	
	}	
}
