/*
 Copyright 2012 Jean-Baptiste Balmeyer - http://www.balmeyer.net

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package net.balmeyer.qno;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.balmeyer.qno.dico.Dictionary;
import net.balmeyer.qno.dico.Entry;
import net.balmeyer.qno.impl.PlainWord;
import net.balmeyer.qno.impl.WordBagImpl;

/**
 * 
 * @author JB Balmeyer
 *
 */
public final class Utils {

	private Utils(){}
	
	public static void check(boolean expression, String msg){
		if (!expression) throw new IllegalArgumentException(msg);
	}
	
	public static Vocabulary load(String path) throws IOException {
		//find text pattern
		URL url = url(path);
		Vocabulary vocab = new Vocabulary();
		
		add(vocab,url);
		
		return vocab;
	}
	
	private static void add(Vocabulary vocab , String path) throws IOException{
		URL url = Vocabulary.class.getClassLoader().getResource(path);
		add(vocab, url);
	}
	
	/**
	 * Load configuration for generating text
	 * @param path
	 * @throws IOException
	 */
	private static void add(Vocabulary vocab , URL url) throws IOException{

		InputStream inputStream = url.openStream();
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, "UTF-8")
				);
		
		String line = "";
		
		WordBag patterns = new WordBagImpl();
		patterns.setID(Vocabulary.PATTERN_ID);
		
		WordBag currentMap = patterns;
		List<WordBag> allmaps = new ArrayList<WordBag>();
		allmaps.add(patterns);

		//current expression
		StringBuilder currentExpression = new StringBuilder();
		boolean inExpression = false;
		
		//read line
		while (line != null){
			line = reader.readLine();
			if (line != null){
				line = line.trim();
				
				//import
				if (line.startsWith("@")){
					add(vocab, line.substring(1));
					continue;
				}
				
				//new word
				if (line.startsWith("[")){
					currentMap = (WordBag) WorderFactory.bag(line);
					allmaps.add(currentMap);
					continue;
				}
				
				//new pattern
				if (line.equals("{")){
					inExpression = true;
					currentExpression.setLength(0);
					continue;
				}

				if (inExpression) {
					//end of expression
					if (line.equals("}")){
						inExpression = false;
					} else {
						if (currentExpression.length() > 0) currentExpression.append("\r\n");
						currentExpression.append(line);
						
						continue;
					}
				}
				else {
					//simple line
					currentExpression = new StringBuilder(line);
				}
				
				if (currentExpression.length() > 0) {
				currentMap.addRawData(currentExpression.toString());
				}

			}
		}
		
		//keep all maps
		vocab.add(allmaps);
		
	}
	
	public static URL url(String path){
		return Utils.class.getClassLoader().getResource(path);
	}
	

}
