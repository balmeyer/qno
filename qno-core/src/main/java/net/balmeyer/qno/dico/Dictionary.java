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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.balmeyer.qno.Word;
import net.balmeyer.qno.Worder;
import net.balmeyer.qno.query.Query;

public class Dictionary implements Worder {

	private Set<Entry> entries ;
	private Map<String,List<Entry>> selected;
	private Random rand;
	
	public Dictionary(){
		this.entries = new HashSet<Entry>();
		this.rand = new Random(System.currentTimeMillis());
	}

	public Set<Entry> getEntries(){
		return this.entries;
	}

	/**
	 * 
	 * @param word
	 * @param definition
	 * @return
	 */
	public static Entry buildEntry(String word, String definition){
		Entry e = new Entry(word);
		e.setDefinition(definition);

		return e;
	}
	
	
	public void add(Entry e){
		this.entries.add(e);
	}

	
	
	@Override
	public Word get(Query request) {
		EntryQuery eq = (EntryQuery) request;
		
		if (this.selected == null) this.build();
		
		List<Entry> list = this.selected.get(getKey(eq));
		
		if (list != null && list.size() > 0 ){
			int i = this.rand.nextInt(list.size());
			return list.get(i);
		}
		
		return null;
	}
	
	
	private void build(){
		this.selected = new HashMap<String,List<Entry>>();
		
		for(Entry e : this.entries){
			String key = getKey(e);
			List<Entry> list = this.selected.get(key);
			if (list == null ){
				list = new ArrayList<Entry>();
				this.selected.put(key,list);
			}
			list.add(e);
		}
		
	}
	
	private String getKey(TypeAndGenre tag){
		return tag.getType() + "-" + tag.getGenre();
	}
	
}
