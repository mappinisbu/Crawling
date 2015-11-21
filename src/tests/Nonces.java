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
		boolean containsFormElements = false;
		boolean containsAtleastoneFormNonce = false;
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
						containsAtleastoneFormNonce = true;
						String formName = element.attr("id");
						details = details + (formName.isEmpty()?"A form":"the form :" + formName )+ " has nonce: " + felem.attr("value") + "\n";
					}
				}
			}
			
			if(!isFormNonceEnabled)
			{
				String formName = element.attr("id");
				details = details + (formName.isEmpty()?"A form":"the form :" + formName )+ " may not have a nonce enabled. \n";

			}

		}
		
		if(!containsFormElements)
		{
			resultObj.setNoncesEnabled(true);
			details = details + "No form elements have been found on this page. \n";
		}
		else
		{
			resultObj.setNoncesEnabled(containsAtleastoneFormNonce);
		}
		
		Elements scriptElements = doc.getElementsByTag("script");
		boolean isScriptNoncepresent = false;
		for (org.jsoup.nodes.Element element : scriptElements)
		{
		    String nonce = element.attr("nonce");
		    if(nonce != null && !nonce.isEmpty())
		    {
		    	isScriptNoncepresent = true;
		    }
		}
		
		if(isScriptNoncepresent)
			details = details + "The script nonce is present.\n";
		else
			details = details + "The script nonce is absent.\n";

	    
	    resultObj.setNoncesDetails(details);
		
	}
	
	private static boolean isNonce(String inputValue) {
		// check if input value is an url
		if(isURL(inputValue))
			return false;
		String patt = "[a-zA-Z0-9,=,+,/,-,_,!,.,~,:]{22,40}";
		Pattern r = Pattern.compile(patt);
		Matcher match = r.matcher(inputValue);
		return match.find();
	}

	private static boolean isURL(String inputValue) {
		String urlPatt = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern r = Pattern.compile(urlPatt);
		Matcher match = r.matcher(inputValue);
		return match.find();
	}
}
