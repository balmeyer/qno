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
import java.util.Collection;
import java.util.List;

/**
 * Node
 * 
 * @author JB Balmeyer
 *
 */
public class Node {

	private String text;
	private boolean optional ;
	private Node parent;

	private List<Node> children;
	
	public Node(){
		children = new ArrayList<Node>();

	}
	
	@Override
	public String toString(){
		return this.getText();
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	
	public void addChild(Node node){
		node.setParent(this);
		this.children.add(node);
		

	}
	
	public List<Node> getChildren(){
		return this.children;
	}

}
