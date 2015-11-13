package tests;

import org.jsoup.Connection.Response;

public class ContentSecurityPolicy {

	public static void StartTest(Response urlResp, Result resultObj) {
		System.out.print("Content-Security-Policy header: ");
		if(urlResp.hasHeader("Content-Security-Policy")){
			System.out.print("Found\n");
		    resultObj.setCspEnabled(true); 
		}else{
			System.out.print("Not found!\n");
			resultObj.setCspEnabled(false);
		}	
    }
}
