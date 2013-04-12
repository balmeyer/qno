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
package net.balmeyer.qno.text;

import java.util.ArrayList;

/**
 * Classe pour formater l'élision ("le oiseau" -> "l'oiseau")
 * @author JB Balmeyer
 *
 */
public class ElisionFormater implements Formater {

	@Override
	public void format(StringBuilder text) {
		//articles soumis à l'élision 
		String [] articles = {"le","la","du","de"};
		//voyelle provoquant l'élision
		char [] v = {'a','e','i','o','y','h'};
		
		ArrayList<Character> voyelles = new ArrayList<Character>();
		for(char c : v) voyelles.add(c);
		
		//boucle sur les articles
		for(String art : articles){
			
			int a = 0;
			while (a < text.length()){
			a = text.toString().toLowerCase().indexOf(art + " ", a );
			if (a < 0) break;
			
			int space = a + art.length() ;
			if (space < text.length() - 1 && text.charAt(space) == ' ') {
				if (voyelles.contains(text.charAt(space + 1))){
					//check letter before
					char before = ' ';
					if (a > 0){
						before = text.charAt(a - 1);
					}
					
					if (Character.toLowerCase(before) < 'a' || Character.toLowerCase(before)>'z'){
						//do replace
						text.replace(a + 1, a+3, "'");
					}
				}
			}
			a++;
			}
			
			
		}

	}

}
