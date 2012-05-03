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

public class Root {

	private Node root;

	private List<Marker> markers;

	public Root() {

		// init markers
		markers = new ArrayList<Marker>();

		markers.add(new Marker('[', ']'));
		markers.add(new Marker('{', '}'));

		// pattern

	}

	
	public List<Node> buildOrNodes(String text){
		
		boolean intext = true;
		int level = 0;
		StringBuilder sb = new StringBuilder();
		
		List<Node> currentNode = new ArrayList<Node>();
		
		for (int i = 0; i < text.length(); i++) {
			
			char c = text.charAt(i);
			
			if (c == '|' && intext && sb.length() > 0){
				Node n = new Node();
				n.setText(sb.toString());
				sb.setLength(0);
				currentNode.add(n);
				continue;
			}
			
			//
			for (Marker m : this.markers) {
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
			
			
			if (intext){
				sb.append(c);
			}

			
		}
		
		//final node
		if (sb.length() > 0){
			Node n = new Node();
			n.setText(sb.toString());
			currentNode.add(n);
		
		}
		
		
		//examine sub nodes
		for(Node n : currentNode){
			n.addChildren(buildOrNodes(n.getText()));
			
		}
		
		
		return currentNode;
		
	}
	
	public Node analyze(String text) {
		// current node
		Node doc = new Node();
		doc.setText(text);
		
		StringBuilder sb = new StringBuilder();

		boolean insideVar = false;
		int start = -1;
		Marker currentMark = null;
		int level = 0;

		for (int i = 0; i < text.length(); i++) {
			
			char c = text.charAt(i);
			

			if (c == '$'){
				insideVar = true;
				continue;
			}
			
			if (insideVar){
				if (c == '{') continue;
				if (c == '}'){
					insideVar = false;
					continue;
				}
			}
			
			for (Marker m : this.markers) {
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
						Node sub = this.analyze(text, start, i);
						doc.addChild(sub);
						currentMark = null;
						
					} else {
						level--;
					}

					break;
				}
			}

			//
			
		}

		return doc;
	}

	private Node analyze(String text, int start, int end) {
		String inside = text.substring(start + 1, end);

		Node n = this.analyze(inside);
		n.setStart(start);
		n.setEnd(end);
		
		return n;
	}

}
