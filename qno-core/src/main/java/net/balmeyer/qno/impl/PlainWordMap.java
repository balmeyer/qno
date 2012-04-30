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
import java.util.Random;

import net.balmeyer.qno.Word;
import net.balmeyer.qno.WordMap;
import net.balmeyer.qno.WordRequest;

/**
 * 
 * @author Word Map
 *
 */
public class PlainWordMap implements WordMap {

	private Random rand;
	private List<Word> words;
	
	public PlainWordMap(){
		this.words = new ArrayList<Word>();
		this.rand = new Random(System.currentTimeMillis());
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
	public Word next(){
		
		int i = this.rand.nextInt(this.words.size());
		
		return words.get(i);
	}
	
	/** next word */
	@Override
	public Word next(WordRequest request)
	{
		return next();
	}
	
}
