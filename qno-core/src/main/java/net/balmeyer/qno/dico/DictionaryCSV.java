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

/**
 * More complex dictionary than "Dictionary" class, based on the "dictionary.csv" files.
 * This file contains several column to decline words with gender and number.
 * @author vovau
 *
 */
public class DictionaryCSV implements WordBag {

	//name of column in file
	private static final String DIC_COLUMN_TEXT = "texte";
	private static final String DIC_COLUMN_TYPE = "type";
	private static final String DIC_COLUMN_PLURIEL = "pluriel";
	private static final String DIC_COLUMN_FEMININ = "feminin";
	private static final String DIC_COLUMN_FEMININPLURIEL = "femininpluriel";
	private static final String DIC_COLUMN_GROUP = "groupe";

	//column
	private Map<String,Integer> nameToColumn;
	private static final String SEPARATOR = "\t";
	
	//entries
	private Map<Definition,List<TypedWord>> entries;
	
	//word to definition
	private Map<String,Definition> wordToDefinition;
	
	public DictionaryCSV(){
		this.entries = new HashMap<Definition,List<TypedWord>>();
		this.wordToDefinition = new HashMap<String, Definition>();
	}
	
	@Override
	public Word get(Query query) {

		EntryQuery request = (EntryQuery) query;
		
		//find word
		List<TypedWord> list = this.entries.get(request.getDefinition());
		
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
		String type = getValue(words, DIC_COLUMN_TYPE);
		TypedWord word = null;
		
		if (type.startsWith("n")){
			//nom
			word = this.buildNoun(words);
		}
		
		if (type.startsWith("adj")){
			//adjectives
			word = this.buildAdjectif(words);
		}
		
		if (type.startsWith("v")){
			//Verb
			word = this.buildVerbe(words);
		}
		
		if (word != null) {
			//check def
			Utils.check(word.getDefinition() != null, "definition is null for word : " + word);
			
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
		
		//add word to defiintion
		this.wordToDefinition.put(w.toString(), word.getDefinition());
	}

	@Override
	public Word get() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("not implemented");
	}
	
	/**
	 * Extract pattern from plain text
	 * @param plain
	 * @return
	 */
	public String extractPattern(String plain){
		return plain;
	}
	
	/**
	 * Find type and genre from word
	 * @param word
	 * @return
	 */
	public Definition findWord(String word){
		if (this.wordToDefinition.containsKey(word)) return wordToDefinition.get(word);
		return null;
	}
	
	private TypedWord formalize(TypedWord word, String forme){
		if (forme == null) forme = "";
		if(word instanceof Adjectif){
			Adjectif adj = (Adjectif) word;
			
			adj.setPluriel(forme.contains("p"));
			adj.setFeminin(forme.contains("f"));
			
			return adj;
		}
		
		if(word instanceof Noun){
			Noun nom = (Noun) word;
			
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
	private Noun buildNoun(String[]words){
		
		String definition = getValue(words,"type");
		Noun noun = new Noun(words[nameToColumn.get(DIC_COLUMN_TEXT)], definition);
		
		if (getValue(words,DIC_COLUMN_PLURIEL) != null){
			noun.setPluriel(getValue(words,DIC_COLUMN_PLURIEL) );
		}
		
		return noun;
	}
	
	private Adjectif buildAdjectif(String[]words){
		Adjectif adj = new Adjectif(words[nameToColumn.get(DIC_COLUMN_TEXT)]);
		
		if (getValue(words,"pluriel") != null){
			adj.setPluriel(getValue(words,DIC_COLUMN_PLURIEL) );
		}
		
		if (getValue(words,"feminin") != null){
			adj.setFeminin(getValue(words,DIC_COLUMN_FEMININ) );
		}
		
		if (getValue(words,DIC_COLUMN_FEMININPLURIEL) != null){
			adj.setFemininPluriel(getValue(words,DIC_COLUMN_FEMININPLURIEL) );
		}
		
		return adj;
	}
	
	private Verb buildVerbe(String[]words){
		Verb verbe = new Verb(
				words[nameToColumn.get(DIC_COLUMN_TEXT)],
				words[nameToColumn.get(DIC_COLUMN_TYPE)]
				);
		
		if (getValue(words,DIC_COLUMN_GROUP) != null){
			verbe.setGroupe(Integer.valueOf(getValue(words,DIC_COLUMN_GROUP)));
		}
		
		return verbe;
	}

}
