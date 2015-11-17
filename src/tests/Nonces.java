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

		// System.out.println(rawHTML);
		Document doc = Jsoup.parse(rawHTML);
		boolean isPageNonceEnabled = true;
		boolean containsFormElements = false;

		Elements elements = doc.body().select("form");

		for (org.jsoup.nodes.Element element : elements) 
		{
			containsFormElements = true;
			System.out.println(" The form name is " + element.attr("name"));
			Elements formElements = element.getAllElements();
			boolean isFormNonceEnabled = false;
			for (org.jsoup.nodes.Element felem : formElements)
			{
				//TODO: check if forms with no action also be protected with nonce?
				// if yes, then dont enforce the form to have nonce field
				if (felem.attr("type").equals("hidden"))
				{
					System.out.println("Checking value: " + felem.attr("value"));
					if(isNonce(felem.attr("value")))
					{
						isFormNonceEnabled = true;
						System.out.println("Nonce Enabled");
					}
				}
			}
			
			if(!isFormNonceEnabled && isPageNonceEnabled)
			{
				System.out.println("The form whose name is :" + element.attr("name") + " has no nonce enabled");
				isPageNonceEnabled = false;
			}

		}
		
		System.out.print("Nonces header: ");
		
		if(!containsFormElements)
			isPageNonceEnabled = false;
		
	    resultObj.setNoncesEnabled(isPageNonceEnabled);
		
	}
	
	private static boolean isNonce(String inputValue) {
		String patt = "[a-zA-Z0-9,=,+,/]{22,40}";
		Pattern r = Pattern.compile(patt);
		Matcher match = r.matcher(inputValue);
		return match.find();
	}
}
