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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.balmeyer.qno.Utils;
import net.balmeyer.qno.Vocabulary;
import net.balmeyer.qno.Word;
import net.balmeyer.qno.WordBag;
import net.balmeyer.qno.query.Query;

public class DictionaryCSV implements WordBag {

	//column
	private Map<String,Integer> nameToColumn;
	private static final String SEPARATOR = "\t";
	
	//entries
	private Map<String,List<TypedWord>> entries;
	
	public DictionaryCSV(){
		this.entries = new HashMap<String,List<TypedWord>>();
	}
	
	@Override
	public Word get(Query query) {

		EntryQuery request = (EntryQuery) query;
		
		//find word
		List<TypedWord> list = this.entries.get(request.toString());
		
		if (list != null && list.size() > 0 ){
			int i = Utils.getRandInstance().nextInt(list.size());
			TypedWord result = list.get(i);
			
			//forme ?
			return this.formalize(result, request.getForme());
			
		}
		
		throw new IllegalArgumentException("Request not found : " + request);
		//return null;
		
	}

	@Override
	public String getID() {
		return Vocabulary.DICTIONARY;
	}

	@Override
	public void setID(String id) {

	}

	/**
	 * Add word from Dictionary CSV
	 */
	@Override
	public void addRawData(String data) {

		//build columns
		if (this.nameToColumn == null){
			this.buildNametoPattern(data);
			return;
		}
		
		String [] words = data.split(SEPARATOR); 
		String type = getValue(words,"type");
		TypedWord word = null;
		
		if (type.startsWith("n")){
			//nom
			word = this.buildNom(words);
		}
		
		if (type.startsWith("adj")){
			//adj
			word = this.buildAdjectif(words);
		}
		
		if (type.startsWith("v")){
			//Verb
			word = this.buildVerbe(words);
		}
		
		if (word != null) {
			word.setDefinition(type);
			this.add(word);
		}
	}

	@Override
	public void add(Word w) {
		TypedWord word = (TypedWord) w;
		
		if (!entries.containsKey(word.getDefinition())){
			entries.put(word.getDefinition(), new ArrayList<TypedWord>());
		}
		
		entries.get(word.getDefinition()).add(word);
		
	}

	@Override
	public Word get() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("not implemented");
	}
	
	private TypedWord formalize(TypedWord word, String forme){
		if (forme == null) forme = "";
		if(word instanceof Adjectif){
			Adjectif adj = (Adjectif) word;
			
			adj.setPluriel(forme.contains("p"));
			adj.setFeminin(forme.contains("f"));
			
			return adj;
		}
		
		if(word instanceof Nom){
			Nom nom = (Nom) word;
			
			nom.setPluriel(forme.contains("p"));
			
			return nom;
		}
		
		
		return word;
	}
	
	private void buildNametoPattern(String data){
		this.nameToColumn = new HashMap<String,Integer>();
		
		String [] words = data.split(SEPARATOR);
		
		int i = 0;
		for(String w : words){
			this.nameToColumn.put(w, i++);
		}
	}
	
	private String getValue(String [] words, String name){
		
		if (!nameToColumn.containsKey(name)){
			System.out.println("WARNING : column not found : " + name);
		}
		
		int index = this.nameToColumn.get(name);
		if( index >= words.length) return null;
		String word = words[index];
		if (word == null || word.length() == 0) return null;
		return word;
	}
	
	/**
	 * build "un nom"
	 * @param words
	 * @return
	 */
	private Nom buildNom(String[]words){
		
		String type = getValue(words,"type");
		Genre genre = (type.equals("nf")) ? Genre.feminin : Genre.masculin;
		
		Nom nom = new Nom(words[nameToColumn.get("texte")], genre);
		
		if (getValue(words,"pluriel") != null){
			nom.setPluriel(getValue(words,"pluriel") );
		}
		
		return nom;
	}
	
	private Adjectif buildAdjectif(String[]words){
		Adjectif adj = new Adjectif(words[nameToColumn.get("texte")]);
		
		if (getValue(words,"pluriel") != null){
			adj.setPluriel(getValue(words,"pluriel") );
		}
		
		if (getValue(words,"feminin") != null){
			adj.setFeminin(getValue(words,"feminin") );
		}
		
		if (getValue(words,"femininpluriel") != null){
			adj.setFemininPluriel(getValue(words,"femininpluriel") );
		}
		
		return adj;
	}
	
	private Verbe buildVerbe(String[]words){
		Verbe verbe = new Verbe(words[nameToColumn.get("texte")]);
		
		if (getValue(words,"groupe") != null){
			verbe.setGroupe(Integer.valueOf(getValue(words,"groupe")));
		}
		
		return verbe;
	}

}
