package main;

import main.PageCrawler;
import tests.*;

public class Initialize {
	
	public static void StartCrawler(String domainName, int numberPages) {
		
		PageCrawler.Crawl(domainName, numberPages);
		StartSecurityChecks();
	}
	
	private static void StartSecurityChecks() {
		
		AnticlickjackingHeaders.StartTest();
		ContentSecurityPolicy.StartTest();
		HttpOnlySecureCookies.StartTest();
		HttpStrictTransportPolicy.StartTest();
		Nonces.StartTest();
	}

}
