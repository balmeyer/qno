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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.balmeyer.qno.Utils;
import net.balmeyer.qno.Vocabulary;
import net.balmeyer.qno.Word;
import net.balmeyer.qno.WordBag;
import net.balmeyer.qno.query.Query;

/**
 * Simple dictionary (see "dictionary.txt" file), based on text/type.
 * @author vovau
 *
 */
public final class Dictionary implements WordBag {

	//private static final String DICTIONARY_RESOURCE = "dictionary.txt";
	
	private Set<Entry> entries ;
	private Map<Definition,List<Entry>> selected;


	public Dictionary(){
		this.entries = new HashSet<Entry>();
	}
	
	@Override
	public void addRawData(String data){
		
		if (data == null || data.startsWith("%")) return;
		
		String [] words = data.split("\t");
		if (words[1].equals("null")) return;
		
		Entry e = Dictionary.buildEntry(words[0], words[1]);
		this.add(e);
	}
	
	/**
	 * Load resource
	 * @param resource
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public void loadResource(String resource) throws UnsupportedEncodingException, IOException{

		URL url = Utils.url(resource);
		InputStreamReader isr = new InputStreamReader(url.openStream(),"ISO-8859-1");
		BufferedReader reader = new BufferedReader(isr);
		
		do {
			String line = reader.readLine();
			if (line == null) break;
			this.addRawData(line);
			
		} while(true);

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
		//TODO remove : doublon
		Entry e = Entry.buildEntry(word,definition);
		return e;
	}
	
	@Override
	public String getID() {
		return Vocabulary.DICTIONARY;
	}

	@Override
	public void setID(String id){
		//nothing to do
	}
	
	@Override
	public void add(Word w) {
		this.add((Entry) w);
	}

	@Override
	public Word get() {
		return get(new EntryQuery(""));
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
			int i = Utils.getRandInstance().nextInt(list.size());
			return list.get(i);
		}
		
		return null;
	}
	
	/**
	 * Build lists
	 */
	private void build(){
		this.selected = new HashMap<Definition,List<Entry>>();
		
		for(Entry e : this.entries){
			//
			Definition key = getKey(e);
			
			List<Entry> list = this.selected.get(key);
			if (list == null ){
				list = new ArrayList<Entry>();
				this.selected.put(key,list);
			}
			list.add(e);
		}
		
		//build complete list of "nom" (name)
		Definition key = new Definition(Type.noun);
		
		ArrayList<Entry> allNames = new ArrayList<Entry>();
		allNames.addAll(this.selected.get(new Definition(Type.noun , Gender.masculin)));
		allNames.addAll(this.selected.get(new Definition(Type.noun , Gender.feminin)));
		this.selected.put(key,allNames);
	}
	
	private Definition getKey(TypeAndGenre tag){
		if (tag.getGenre() == null) return new Definition(tag.getType());
		return new Definition(tag.getType(),  tag.getGenre());
		
	}


	
}
