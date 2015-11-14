package main;

import main.PageCrawler;
import objects.Result;
import tests.*;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;


public class Initialize {
	
	public static void StartCrawler(String domainName, int numberPages) {
		
		PageCrawler.Crawl(domainName, numberPages);
		//StartSecurityChecks();
	}
	
	public static Result StartSecurityChecks(Response urlResp) {
		
		Result resultObj = new Result();
		AnticlickjackingHeaders.StartTest(urlResp,resultObj);
		ContentSecurityPolicy.StartTest(urlResp,resultObj);
		HttpOnlySecureCookies.StartTest(urlResp,resultObj);
		HttpStrictTransportPolicy.StartTest(urlResp,resultObj);
		Nonces.StartTest(urlResp,resultObj);
		
		return resultObj;
	}

}
