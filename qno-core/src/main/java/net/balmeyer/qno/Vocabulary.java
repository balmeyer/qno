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
package net.balmeyer.qno;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.balmeyer.qno.dico.Dictionary;
import net.balmeyer.qno.query.Query;
import net.balmeyer.qno.query.SimpleQuery;

/**
 * A Vocabulary is every data needed to generate text (pattern
 * @author JB Balmeyer
 *
 */
public class Vocabulary implements WordSource {

	public static final String PATTERN_ID = "*";
	public static final String DICTIONARY = "#";
	
	private Map<String,WordSource> idToBag;
	
	/**
	 * 
	 */
	@Override
	public Word get(Query request) {
		
		if (this.idToBag == null) {
			throw new IllegalArgumentException("there is no data to provide !");
		}
		
		WordSource bag = this.idToBag.get(request.getVariableName());
		
		if (bag == null){
			
			if (request.getVariableName().equals(Vocabulary.DICTIONARY)){
				throw new IllegalArgumentException("No Dictionary was provided");
			}
			
			throw new IllegalArgumentException("ID not found : " + request.getVariableName() + "[" + request 
					+"]");
		}

		Word w = bag.get(request);
		
		Utils.check(w != null , "Word is null. Request : " + request);
		
		return w;
	}

	/**
	 * Add a map to the current list
	 * @param map
	 */
	public void add(WordBag map){
		this.add(map.getID(), map);

	}
	
	public void add(Dictionary dico){
		this.add(DICTIONARY , dico);
	}
	
	private void add(String name ,WordSource worder){
		if (this.idToBag == null) this.idToBag = new HashMap<String,WordSource>();

		this.idToBag.put(name,worder);
	}
	
	public void add(Collection<WordBag> maps){
		for(WordBag bag : maps) add(bag);
	}
	

	public Collection<WordSource> getMaps(){
		return this.idToBag.values();
	}
	

	public Word getPattern(){
		return this.get(new SimpleQuery(PATTERN_ID));
	}
	
	
	
}
