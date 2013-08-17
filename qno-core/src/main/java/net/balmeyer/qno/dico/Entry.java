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


/**
 * 
 * @author vovau
 *
 */
public class Entry extends TypeAndGenre implements TypedWord  {

	private String word;
	private Definition definition;

	public static Entry buildEntry(String word){
		return new Entry(word);
	}
	public static Entry buildEntry(String word, String definition){
		return new Entry(word,definition);
	}

	private Entry(String word){
		this.word = word;
	}
	
	private Entry(String word, String definition){
		this.word = word;
		this.definition = new Definition(definition);
		this.defineMe(definition);
	}
	
	@Override
	public String toString(){
		return this.word;
	}
	
	@Override
	public Definition getDefinition() {
		return definition;
	}


	
}
