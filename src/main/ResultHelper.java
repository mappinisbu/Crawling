package main;

import java.util.List;
import java.util.Map;

import objects.Result;

public class ResultHelper {

	public static String addDetails(Map<String, List<String>> urlRespMap, Result resultObj, String testContains) {
		String details = "";
		for (Map.Entry<String, List<String>> mapEntry: urlRespMap.entrySet()){
			if (mapEntry.getKey() != null) {
				if (mapEntry.getKey().toLowerCase().contains(testContains.toLowerCase())) {
					details = details + "<strong>" + mapEntry.getKey() + " : </strong>" + mapEntry.getValue();
				}
			}
		}
		return details;
	}
}
