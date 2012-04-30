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
package net.balmeyer.qno.impl;

import net.balmeyer.qno.Word;
import net.balmeyer.qno.text.Parser;
import net.balmeyer.qno.text.Variable;

public class SimpleParser implements Parser {

	private StringBuilder text;
	
	@Override
	public String getText() {
		return text.toString();
	}

	@Override
	public void setText(String text){
		this.text = new StringBuilder(text);
	}
	
	@Override
	public Variable nextVariable() {
		Variable v = null;
		
		int start = this.text.indexOf("$");
		
		if (start >= 0){
			int end = this.text.indexOf("}", start);
			if (end >= 0){
				v = new Variable();
				v.setStart(start);
				v.setEnd(end);
				v.setText(this.text.substring(start, end + 1));
			
			} else {
				throw new IllegalArgumentException("error when parsing variable");
			}
		}
		
		return v;
	}

	@Override
	public void replace(Variable var, Word word) {
		this.text.replace(var.getStart(), var.getEnd()+1, word.toString());

	}

}
