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

public abstract class TypeAndGenre {
	private Genre genre;
	private Type type;
	
	/**
	 * Define type and genre on a word
	 * @param e
	 * @param definition
	 */
	public static void define(TypeAndGenre e, String definition){
		if (definition.startsWith("n")) e.setType(Type.nom);
		if (definition.startsWith("v")) e.setType(Type.verbe);
		if (definition.startsWith("vi")) e.setType(Type.verbeIntransitif);
		if (definition.startsWith("adj")) e.setType(Type.adjectif);
		if (definition.startsWith("adv")) e.setType(Type.adverbe);
		
		if (definition.startsWith("nf")) e.setGenre(Genre.feminin);
		if (definition.startsWith("nm")) e.setGenre(Genre.masculin);
	}
	
	
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
}
