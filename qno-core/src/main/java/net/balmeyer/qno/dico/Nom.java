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

import net.balmeyer.qno.Word;

public class Nom implements TypedWord {

	private String base ;
	private String pluriel;
	private String text;
	private Genre genre;
	
	private String type;
	
	private boolean ispluriel;
	
	public Nom(String text, Genre genre){
		this.base = text;
		this.setPluriel(text + "s");
		this.text = this.base;
		this.genre = genre;
	}
	
	@Override
	public String toString(){
		return this.text;
	}
	
	public void setPluriel(String text){
		if (text == null || text.length() == 0) return;
		this.pluriel = text;
	}
	
	/**
	 * Returns gender
	 * @return
	 */
	public Genre getGenre(){
		return this.genre;
	}

	@Override
	public String getDefinition() {
		return this.type;
	}

	@Override
	public void setDefinition(String definition){
		this.type = definition;
	}
	
	public void setPluriel(boolean value){
		this.ispluriel = value;
		this.rebase();
	}
	
	private void rebase(){
		if (!ispluriel) this.text = this.base;
		if (ispluriel) this.text = this.pluriel;
	}
	
	
}
