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

import net.balmeyer.qno.Utils;
import net.balmeyer.qno.Word;
import net.balmeyer.qno.pattern.PatternBuilder;
import net.balmeyer.qno.pattern.PatternBuilderFactory;

/**
 * Basic implementation of @TextBuilder. 
 * @author jean-baptiste
 *
 */
public class SimpleTextBuilder implements TextBuilder {

	private StringBuilder text;
	
	@Override
	public String getCurrentText() {
		return text.toString();
	}

	@Override
	public void setPattern(String text){
		this.text = new StringBuilder(text);
		this.rebuild();
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
				throw new IllegalArgumentException("error when parsing variable. text is <<<" + this.text + ">>>");
			}
		}
		
		return v;
	}

	@Override
	public void replace(Variable var, Word word) {
		Utils.check(var != null , "Var must not be null");
		Utils.check(word != null, "word must not be null");
		
		this.text.replace(var.getStart(), var.getEnd()+1, word.toString());
		
		this.rebuild();
	}

	@Override
	public String toString(){
		return this.getCurrentText();
	}
	
	private void rebuild(){
		//analyze pattern
		PatternBuilder pp = PatternBuilderFactory.get();
		pp.buildPattern(this.text);
	}
	

	
}
