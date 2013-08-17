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
 * Noun
 * @author balmeyer
 *
 */
public class Noun extends TypeAndGenre {

	private String base ;
	private String pluriel;
	private String text;

	private boolean ispluriel;
	
	public Noun(String text, String definition){
		this.base = text;
		this.setPluriel(text + "s");
		this.text = this.base;
		
		this.defineMe(definition);
	}
	
	@Override
	public String toString(){
		return this.text;
	}
	
	public void setPluriel(String text){
		if (text == null || text.length() == 0) return;
		this.pluriel = text;
	}
	
	public Type getType(){
		return Type.noun;
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
