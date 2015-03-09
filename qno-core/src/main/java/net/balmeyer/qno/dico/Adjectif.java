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


public class Adjectif implements TypedWord{

	private String base;
	private String text;
	private String feminin;
	private String pluriel;
	private String femininPluriel;
	
	private Definition definition;
	
	private boolean ispluriel;
	private boolean isfeminin;
	
	public Adjectif(String text){
		
		
		this.base = text;
		this.setPluriel(text + "s");
		if (text.endsWith("e")){
			this.setFeminin(text);
			this.setFemininPluriel(text + "s");
		}
		else { 
			this.setFeminin(text + "e");
			this.setFemininPluriel(text + "es");
		}
		
		
		this.text = this.base;
		
		this.definition = new Definition("adj");
	}
	
	@Override
	public String toString(){
		return this.text;
	}
	
	@Override
	public Definition getDefinition() {
		return this.definition;
	}
	
	public Type getType(){
		return Type.adjective;
	}

	public void setPluriel(String pluriel){
		this.pluriel = pluriel;
	}
	
	public void setFeminin(String feminin){
		this.feminin = feminin;
	}
	
	public void setFemininPluriel(String femininPluriel){
		this.femininPluriel = femininPluriel;
	}
	
	public void setPluriel(boolean value){
		this.ispluriel = value;
		this.rebase();
	}
	
	
	public void setFeminin(boolean value){
		this.isfeminin = value;
		this.rebase();
	}
	
	private void rebase(){
		if (!isfeminin && !ispluriel) this.text = this.base;
		if (!isfeminin && ispluriel) this.text = this.pluriel;
		if (isfeminin && !ispluriel) this.text = this.feminin;
		if (isfeminin && ispluriel) this.text = this.femininPluriel;
	}
	
	
}
