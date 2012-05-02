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
import java.util.Random;
import java.util.Set;

import net.balmeyer.qno.Utils;
import net.balmeyer.qno.Vocabulary;
import net.balmeyer.qno.Word;
import net.balmeyer.qno.WordBag;
import net.balmeyer.qno.query.Query;

public final class Dictionary implements WordBag {

	//private static final String DICTIONARY_RESOURCE = "dictionary.txt";
	
	private Set<Entry> entries ;
	private Map<String,List<Entry>> selected;
	private Random rand;
	

	
	public Dictionary(){
		this.entries = new HashSet<Entry>();
		this.rand = new Random(System.currentTimeMillis());
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
		Entry e = new Entry(word);
		e.setDefinition(definition);

		return e;
	}
	
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return Vocabulary.DICTIONARY;
	}

	@Override
	public void setID(String id){
		//
	}
	
	@Override
	public void add(Word w) {
		// TODO Auto-generated method stub
		this.add((Entry) w);
	}

	@Override
	public Word get() {
		// TODO Auto-generated method stub
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
			int i = this.rand.nextInt(list.size());
			return list.get(i);
		}
		
		return null;
	}
	
	/**
	 * Build lists
	 */
	private void build(){
		this.selected = new HashMap<String,List<Entry>>();
		
		for(Entry e : this.entries){
			//
			String key = getKey(e);
			List<Entry> list = this.selected.get(key);
			if (list == null ){
				list = new ArrayList<Entry>();
				this.selected.put(key,list);
			}
			list.add(e);
		}
		
		//build complete list of "nom" (name)
		String key = Type.nom.toString();
		
		ArrayList<Entry> allNames = new ArrayList<Entry>();
		allNames.addAll(this.selected.get(Type.nom + "-" + Genre.masculin));
		allNames.addAll(this.selected.get(Type.nom + "-" + Genre.feminin));
		this.selected.put(key,allNames);
	}
	
	private String getKey(TypeAndGenre tag){
		
		if (tag.getGenre() == null) return tag.getType().toString();
		return tag.getType() + "-" + tag.getGenre();
	}


	
}
