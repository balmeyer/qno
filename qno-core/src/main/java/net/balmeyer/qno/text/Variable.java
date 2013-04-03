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

import net.balmeyer.qno.Vocabulary;

public class Variable {

	private String ID;
	private String text;
	private String property;
	private int start;
	private int end;
	
	public String getID() {
		return this.ID;
	}

	public String getProperty() {
		return property;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
		this.ID = null;
		this.build();
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public String toString(){
		return this.getText();
	}
	
	
	private void build(){

		//bulld id
		String inside = text.substring(text.indexOf("{")+1);
		inside = inside.substring(0 , inside.indexOf("}")).toLowerCase();
		
		if (inside.indexOf(".") >= 0){
			this.ID = inside.substring(0, inside.indexOf("."));
			this.property = inside.substring(inside.indexOf(".")+1);
			//dictionary
			if (this.ID.length() == 0) this.ID = Vocabulary.DICTIONARY;
		} else {
			this.ID = inside;
		}
		
		
	}
}
