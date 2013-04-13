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
package net.balmeyer.qno.pattern;

import java.util.ArrayList;
import java.util.List;

import net.balmeyer.qno.Utils;

/**
 * The PatternBuilder parse formula 
 * 
 * Terms separated by "|" (pipe) represents a list of options : only one is chosen.  
 *  - example : "bar|foo", the result is "bar" or "foo".
 *  
 * Terms between square brackets are optional : [word]
 *  - example : "hello[ everybody]". The result is "hello" OR "hello everybody".
 *  
 * Terms between braces (curly brackets) are given one time : {word}
 *  - example : "hello{ world| everybody}" gives "hello world" OR "hello everybody"
 *  
 *  other example :
 *  "hello[ world| you]" gives "hello" OR "hello world" OR "hello you"
 *  "hello[ world| {you|everybody}]" gives "hello" OR "hello world" OR "hello you" OR "hello everybody"
 * 
 * @author JB Balmeyer
 * 2012-05-04
 */
public final class SimplePatternBuilder implements PatternBuilder {

	//delimiters
	private List<Delimiter> delimiters;

	public SimplePatternBuilder() {

		// init markers
		delimiters = new ArrayList<Delimiter>();

		delimiters.add(new Delimiter('[', ']', 0 , 1));
		delimiters.add(new Delimiter('{', '}' ,1 , 1 ));

		// pattern

	}

	/**
	 * Build a final pattern, without brackets (but with variables), regarding a base pattern. <br/>
	 * 1 - split the high level text in several nodes using the pipe("|") delimiter, and choose one the nodes.<br/>
	 * 2 - browse into the chosen node and do recursively the step 1<br/>
	 * @param text
	 * @return
	 */
	@Override
	public String buildPattern(String text){
		StringBuilder sb = new StringBuilder(text);
		
		browseAndSplit(sb, null);
		
		return sb.toString();
	}
	
	/**
	 * .
	 */
	@Override
	public void buildPattern(StringBuilder sb){
		this.browseAndSplit(sb, null);
	}
	
	/**
	 * Analyze a pattern and split it with the "|" (pipe) delimiter,
	 * and then return one of the splited pattern.
	 * Example : if text is "one | two | three" return "one" or "two" or "three".
	 * @param text
	 * @return
	 */
	private void browseAndSplit(StringBuilder sb, Occurrence occurence){
		boolean intext = true;
		int level = 0;
		int start = 0;
		
		List<Node> currentNode = new ArrayList<Node>();
		
		for (int i = 0; i < sb.length(); i++) {
			
			char c = sb.charAt(i);
			
			if (c == '|' && intext){
				Node n = new Node();
				n.setText(sb.substring(start, i));
				n.setStart(start);
				n.setEnd(i);
				currentNode.add(n);
				start = i + 1;
				continue;
			}
			
			//check delimiters like [] or {} to see if we're in text
			for (Delimiter m : this.delimiters) {
				if (m.start == c) {
					level++;
					intext = false;
					break;
				}

				if (c == m.end) {
					level--;
					if (level == 0) {
						intext = true;
					} 
					break;
				}
			}
			
		}
		
		//final node
		if (start < sb.length()){
			Node n = new Node();
			n.setText(sb.substring(start));
			n.setStart(start);
			n.setEnd(sb.length() -1);
			currentNode.add(n);
		}
		
		
		//Choose NODE
		int n = Utils.getRandInstance().nextInt(currentNode.size());
		Node node = currentNode.get(n);
		
		int min = 1;
		int max = 1;
		
		if (occurence != null){
			min = occurence.getMin();
			max = occurence.getMax();
		}
		
		start = 0;
		if (max - min > 0){
			start = Utils.getRandInstance().nextInt(max - min + 1);
		}
		int loop = min + start;
		
		sb.setLength(0);
		for (int i = 0 ; i < loop ; i++ ){
			StringBuilder sub = new StringBuilder(node.toString());
			browseBrackets(sub);
			sb.append(sub);
		}
		
		
	}
	
	/**
	 * Browse terms inside brackets ("{}" or "[]")
	 * @param text
	 * @return
	 */
	private void browseBrackets(StringBuilder  sb) {
		// current node


		boolean insideVar = false;
		int start = -1;
		Delimiter currentMark = null;
		int level = 0;
		int i = -1;

		while (++i < sb.length()) {
			
			char c = sb.charAt(i);
			
			if (c == '$'){
				insideVar = true;
				continue;
			}
			//when inside a variable as "${...}"
			if (insideVar){
				if (c == '{') continue;
				if (c == '}'){
					insideVar = false;
					continue;
				}
				continue;
			}
			
			for (Delimiter m : this.delimiters) {
				if (m.start == c) {
					if (m.equals(currentMark))
						level++;
					else {
						if (level == 0 && currentMark == null) {
							currentMark = m;
							start = i;
						}
					}
					break;
				}

				if (currentMark != null && c == currentMark.end) {
					if (level == 0) {
						//replace pattern
						StringBuilder inside = new StringBuilder(sb.substring(start + 1, i));
						this.browseAndSplit(inside , currentMark.getOccurence());
						sb.replace(start  , i +1,inside.toString());
						i--;
						currentMark = null;
						
					} else {
						level--;
					}

					break;
				}
			}

			//
			
		}

	}



}
