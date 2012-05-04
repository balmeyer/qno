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

package net.balmeyer.qno.impl;

import java.util.ArrayList;
import java.util.List;

import net.balmeyer.qno.Utils;
import net.balmeyer.qno.Word;
import net.balmeyer.qno.WordBag;
import net.balmeyer.qno.query.Query;

/**
 * 
 * @author Word Map
 *
 */
public class WordBagImpl implements WordBag {

	private String id;
	
	private List<Word> words;
	
	public WordBagImpl(){
		this.words = new ArrayList<Word>();
		
	}
	
	
	@Override
	public String getID(){
		return this.id;
	}
	
	@Override
	public void setID(String id){
		this.id = id;
	}
	
	@Override
	public void addRawData(String data){
		this.add(new PlainWord(data));
	}
	
	/** Add */
	@Override
	public void add(Word w){
		this.words.add(w);
	}
	
	/**
	 * 
	 */
	@Override
	public Word get(){
		
		int i = Utils.getRandInstance().nextInt(this.words.size());
		
		return words.get(i);
	}
	
	/** next word */
	@Override
	public Word get(Query request)
	{
		return get();
	}

	
}
