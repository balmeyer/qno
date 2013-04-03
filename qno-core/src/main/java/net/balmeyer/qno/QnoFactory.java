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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.balmeyer.qno.impl.PlainWord;
import net.balmeyer.qno.impl.WordBagImpl;
import net.balmeyer.qno.text.Formater;
import net.balmeyer.qno.text.Parser;
import net.balmeyer.qno.text.SimpleParser;

/**
 * 
 * @author JB Balmeyer
 *
 */
public class QnoFactory {

	private QnoFactory(){}

	public static Word word(String expression){
		return new PlainWord(expression);
	}

	
	public static Parser newParser(){
		return new SimpleParser();
	}
	

	/**
	 * Load Vocabulary since a configuration file
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Qno load(String path) throws IOException {
		
		Qno qno = new Qno();
		
		//find text pattern
		URL url = Utils.url(path);
		
		add(qno,url);
		
		return qno;
	}
	
	private static void add(Qno qno , String path) throws IOException{
		URL url = Vocabulary.class.getClassLoader().getResource(path);
		add(qno, url);
	}
	
	/**
	 * Load configuration for generating text
	 * @param path
	 * @throws IOException
	 */
	private static void add(Qno qno , URL url) throws IOException{

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
				if (line.startsWith("@import")){
					add(qno, line.substring(7).trim());
					continue;
				}
				
				//formater
				if (line.startsWith("@format")){
					qno.addFormater(createFormaterFromClassName(line.substring(7).trim()));
					continue;
				}
				

				
				
				//new word
				if (line.startsWith("%")){
					currentMap = (WordBag) WordSourceFactory.bag(line);
					allmaps.add(currentMap);
					continue;
				}
				
				//new pattern
				if (line.equals("<")){
					inExpression = true;
					currentExpression.setLength(0);
					continue;
				}

				if (inExpression) {
					//end of expression
					if (line.equals(">")){
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
		qno.getVocabulary().add(allmaps);
		
	}

	private static Formater createFormaterFromClassName(String clazz){
		
		Formater instance = null;
		
		Class cl = null;
		try {
			cl = QnoFactory.class.getClassLoader().loadClass(clazz);
			instance = (Formater) cl.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		

		if (instance == null){
			throw new IllegalArgumentException("impossible to create class : " + clazz);
		}
		
		return instance;
		
	}
	
}
