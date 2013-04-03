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
import java.util.List;

public class SimpleFormater implements Formater {

	@Override
	public void format(StringBuilder sb){

		//
		boolean startQuote = true;
		
		//MAJUSCULE
		for(int i = 0 ; i < sb.length(); i++){
			char c = sb.charAt(i);
			
			if (c == ' ') continue;
			
			if (c == '.' || c == '\n'){
				startQuote = true;
				continue;
			}
			
			if (startQuote){
				sb.setCharAt(i, Character.toUpperCase(c));
				startQuote = false;
			}
			
		}

		this.replaceChar(sb);
		
		//Trim
		while (sb.toString().startsWith(" ")){
			sb.replace(0, 1, "");
		}
		
		while (sb.toString().endsWith(" ")){
			sb.setLength(sb.length() - 1);
		}
		
		//Punctuation
	}
	
	
	
	private void replaceChar(StringBuilder sb){
		
		List<Replacer> replacers = new ArrayList<Replacer>();
		replacers.add(new Replacer("  ", " "));
		replacers.add(new Replacer(" .", ". "));
		replacers.add(new Replacer("\n ", "\n"));
		
		
		boolean change = true;
		
		while (change){
		
			change = false;
		for(Replacer rep : replacers){
			
			do{
				
				int a = sb.indexOf(rep.oldtext);
				if(a < 0) break;
				
				sb.replace(a, a + rep.oldtext.length(), rep.newtext);
				change = true;
				
			} while (true);
			
		}
		
		}
		
		
	}
	/**
	 * 
	 * @author Balmeyer
	 *
	 */
	private class Replacer{
		
		public Replacer(String oldtext, String newtext){
			this.oldtext = oldtext;
			this.newtext = newtext;
		}
		
		public String oldtext;
		public String newtext;
	}
}
