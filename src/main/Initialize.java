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
		
		PageCrawler.Crawl(domainName, numberPages);
		//StartSecurityChecks();
	}
	
	public static Result StartSecurityChecks(Map<String, List<String>> urlRespMap) {
		Result resultObj = new Result();
		AnticlickjackingHeaders.StartTest(urlRespMap,resultObj);
		ContentSecurityPolicy.StartTest(urlRespMap,resultObj);
		HttpOnlySecureCookies.StartTest(urlRespMap,resultObj);
		HttpStrictTransportPolicy.StartTest(urlRespMap,resultObj);
		Nonces.StartTest(urlRespMap,resultObj);
		
		return resultObj;
	}

}
