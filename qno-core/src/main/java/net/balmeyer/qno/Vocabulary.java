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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.balmeyer.qno.impl.PlainRequest;

/**
 * A Vocabulary is every data needed to generate text (pattern
 * @author JB Balmeyer
 *
 */
public class Vocabulary implements Worder {

	public static final String PATTERN_ID = ".";
	
	private List<WordBag> maps;
	
	private Map<String,WordBag> idToBag;
	
	@Override
	public Word get(Request request) {
		
		WordBag bag = this.idToBag.get(request.getVariableName());
		
		if (bag == null){
			throw new IllegalArgumentException("ID not found : " + request.getVariableName() + "[" + request 
					+"]");
		}

		return bag.get(request);
	}


	/**
	 * Add a map to the current list
	 * @param map
	 */
	public void add(WordBag map){
		if (this.maps == null) this.maps = new ArrayList<WordBag>();
		this.maps.add(map);
		this.build();
	}
	
	public void setMaps(List<WordBag> maps){
		this.maps = maps;
		this.build();
	}
	
	public List<WordBag> getMaps(){
		return this.maps;
	}
	

	public Word getPattern(){
		return this.get(new PlainRequest(PATTERN_ID));
	}
	

	private void build(){
		this.idToBag = new HashMap<String,WordBag>();
		for (WordBag bag : this.getMaps()){
			this.idToBag.put(bag.getID(),bag);
		}
	}
	
	
}
