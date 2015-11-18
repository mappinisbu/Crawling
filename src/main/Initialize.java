package main;

import main.PageCrawler;
import objects.Result;
import tests.*;

import java.util.List;
import java.util.Map;

//import org.jsoup.Connection;
//import org.jsoup.Connection.Response;


public class Initialize {
	
	public static void StartCrawler(String domainName, int numberPages) {
		
		System.out.println("Start crawler!!!!!!!!!!!!!!!!!1");
		Controller.initialize(domainName, numberPages);
		//StartSecurityChecks();
	}
	
	public static Result StartSecurityChecks(String url, Map<String, List<String>> urlRespMap, String rawHTML) {
		Result resultObj = new Result();
		AnticlickjackingHeaders.StartTest(urlRespMap,resultObj);
		ContentSecurityPolicy.StartTest(urlRespMap,resultObj);
		HttpOnlySecureCookies.StartTest(urlRespMap,resultObj);
		HttpStrictTransportPolicy.StartTest(urlRespMap,resultObj);
		Nonces.StartTest(url, urlRespMap,rawHTML, resultObj);
		
		return resultObj;
	}

}
