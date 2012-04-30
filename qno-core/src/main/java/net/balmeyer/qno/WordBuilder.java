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

/**
 * 
 * @author JB Balmeyer
 *
 */
public class WordBuilder {

	/**
	 * Load configuration for generating text
	 * @param path
	 * @throws IOException
	 */
	public void load(String path) throws IOException{
		
		//find text pattern
		URL url = WordBuilder.class.getClassLoader().getResource(path);
		
		InputStream inputStream = url.openStream();
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream)
				);
		
		String line = "";
		
		WordMap currentMap = null;
		
		//text pattern
		List<String> patterns = new ArrayList<String>();
		
		//current expression
		StringBuilder currentExpression = new StringBuilder();
		boolean inExpression = false;
		
		//read line
		while (line != null){
			line = reader.readLine();
			if (line != null){
				line = line.trim();
				
				//new word
				if (line.startsWith("[")){
					
				}
				
				//new pattern
				
				if (line.equals("{")){
					inExpression = true;
					continue;
				}
				
				if (line.endsWith("}")){
					inExpression = false;
				}
				
				
				//new word
				
				
				//current word
			}
		}
		
		
	}
	
	
	
}
