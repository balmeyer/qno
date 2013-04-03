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
package net.balmeyer.qno.dico;

import net.balmeyer.qno.Word;

/**
 * 
 * @author vovau
 *
 */
public class Entry extends TypeAndGenre implements Word  {

	private String word;
	private String definition;


	public Entry(String word){
		this.word = word;
	}
	
	@Override
	public String toString(){
		return this.word;
	}
	
	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
		TypeAndGenre.define(this, definition);
	}


	
}
