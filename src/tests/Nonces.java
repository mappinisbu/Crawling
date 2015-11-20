package tests;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

//import org.jsoup.Connection.Response;

import objects.Result;

public class Nonces {

	public static void StartTest(String url,
			Map<String, List<String>> urlRespMap, String rawHTML,
			Result resultObj) {

		Document doc = Jsoup.parse(rawHTML);
		boolean isPageNonceEnabled = true;
		boolean containsFormElements = false;
		String details = "";
		
		Elements elements = doc.body().select("form");

		for (org.jsoup.nodes.Element element : elements) 
		{
			containsFormElements = true;
			Elements formElements = element.getAllElements();
			boolean isFormNonceEnabled = false;
			for (org.jsoup.nodes.Element felem : formElements)
			{
				//TODO: check if forms with no action also be protected with nonce?
				// if yes, then dont enforce the form to have nonce field
				if (felem.attr("type").equals("hidden"))
				{
					if(isNonce(felem.attr("value")))
					{
						isFormNonceEnabled = true;
						details = details + felem.toString() + "\n";
					}
				}
			}
			
			if(!isFormNonceEnabled && isPageNonceEnabled)
			{
				isPageNonceEnabled = false;
			}

		}
		
		if(!containsFormElements)
			isPageNonceEnabled = false;
		
	    resultObj.setNoncesEnabled(isPageNonceEnabled);
	    resultObj.setNoncesDetails(details);
		
	}
	
	private static boolean isNonce(String inputValue) {
		String patt = "[a-zA-Z0-9,=,+,/]{22,40}";
		Pattern r = Pattern.compile(patt);
		Matcher match = r.matcher(inputValue);
		return match.find();
	}
}
