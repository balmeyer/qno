/*
 Copyright 2012-2013 Jean-Baptiste Balmeyer - http://www.balmeyer.net

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
 * Word with a Type, and add a genre (for nouns and adjectives)
 * @author vovau
 *
 */
public abstract class TypeAndGenre implements TypedWord{
	
	private Gender genre;
	private Type type; 
	private Definition definition;
	
	/**
	 * Define type and genre on a word
	 * @param e
	 * @param definition
	 */
	protected void defineMe(String definition){
		
		if (definition.startsWith("n")) this.setType(Type.noun);
		if (definition.startsWith("v")) this.setType(Type.verb);
		if (definition.startsWith("vi")) this.setType(Type.verbIntransitive);
		if (definition.startsWith("adj")) this.setType(Type.adjective);
		if (definition.startsWith("adv")) this.setType(Type.adverb);
		
		if (definition.contains("f")) this.setGenre(Gender.feminin);
		if (definition.contains("m")) this.setGenre(Gender.masculin);
		
		this.definition = new Definition(definition);
	}
	
	
	public Gender getGenre() {
		return genre;
	}
	public void setGenre(Gender genre) {
		this.genre = genre;
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public Definition getDefinition(){
		return this.definition;
	}
}
