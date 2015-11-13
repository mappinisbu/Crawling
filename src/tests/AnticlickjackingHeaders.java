package tests;

import org.jsoup.Connection.Response;

public class AnticlickjackingHeaders {

	public static void StartTest(Response urlResp, Result resultObj) {
		System.out.print("Anti-Click-Jacking header: ");
		if(urlResp.hasHeader("???")){
			System.out.print("Found\n");
		    resultObj.setAntiClickEnabled(true); 
		}else{
			System.out.print("Not found!\n");
			resultObj.setAntiClickEnabled(false);
		}	
	
	}
}
