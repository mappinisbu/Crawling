import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;


public class TestDictionary {

	static HashSet hs = new HashSet();
	public static void main(String[] args) {
		
		
		try {
			File file = new File("words.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				hs.add(line);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String nonce = "fgty/loginqwef";
		printDictionaryMatch(nonce);		
	}
	
	public static void printDictionaryMatch(String word) {
	    for (int from = 0; from < word.length(); from++) {
	        for (int to = from + 1; to <= word.length(); to++) {
	            if(hs.contains(word.substring(from, to)))
	            {
	            	System.out.println(word.substring(from, to));
	            }
	        }
	    }
	}
	
	


}
