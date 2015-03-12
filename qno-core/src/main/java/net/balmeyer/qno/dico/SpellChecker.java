package net.balmeyer.qno.dico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class SpellChecker {

	public static final String FILE = "french_words.txt";
	

	private Set<String> words;
	
	public SpellChecker(){
	}
	
	
	
	public boolean check(String word){
		if (words == null)
			try {
				charge();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return words.contains(word.toString());
	}
	
	private void charge() throws IOException{
		words = new HashSet<String>();
		
		InputStream inputStream = this.getURL().openStream();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream, "UTF-8"));

		String line = "";
		
		while (line != null){
			line = reader.readLine();
			if (line == null) break;
			if (line.contains("\t")) {
				String [] words = line.split("\t");
				line = words[0];
			}
			this.words.add(line);
		}
		
		reader.close();
		inputStream.close();
		
	}
	
	private URL getURL(){
		URL result = SpellChecker.class.getClassLoader().getResource(FILE);
		if (result == null) throw new IllegalArgumentException("SpellChecker. File not found : " + FILE);
		return result;
	}
	
}
