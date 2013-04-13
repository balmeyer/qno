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
import java.util.List;

import net.balmeyer.qno.query.Query;
import net.balmeyer.qno.query.QueryFactory;
import net.balmeyer.qno.text.Formater;
import net.balmeyer.qno.text.Parser;
import net.balmeyer.qno.text.Variable;


/**
 * Main class of Qno engine.
 * 
 * @author JB Balmeyer
 *
 */
public class Qno  {

	//with a default Vocabulary
	private Vocabulary vocab = new Vocabulary();
	
	private List<Formater> formaters = new ArrayList<Formater>();
	
	public Qno(){

	}

	/**
	 * Current Vocabulary used to generate text.
	 * @return
	 */
	public Vocabulary getVocabulary() {
		return vocab;
	}

	public void setVocabulary(Vocabulary vocab) {
		this.vocab = vocab;
	}
	
	/**
	 * Formater list, used to enhance text when generation is finished.
	 * @return
	 */
	public List<Formater> getFormater() {
		return formaters;
	}

	/**
	 * Add a @Formater to list.
	 * @param formater
	 */
	public void addFormater(Formater formater) {
		this.formaters.add(formater);
	}

	/**
	 * Execute text generation, using a pattern provided by @Vocabulary instance.
	 * @return
	 */
	public String execute(){
		return this.execute(this.getVocabulary().getPattern().toString());
	}
	
	/**
	 * Execute a text generation based on a specified pattern. 
	 * 
	 * Replace variables in given pattern, then enhance final text with @Formater instances.
	 * 
	 * @param pattern
	 * @return
	 */
	public String execute(String pattern){
		
		//instantiate a new parser
		Parser parser = QnoFactory.newParser();
		//specify the pattern used to generate text -
		parser.setText(pattern); 
		
		//variable
		Variable v = null ;
		do {
			//find next variable in pattern 
			v = parser.nextVariable();
			
			if (v != null) {
				//build request from variable to fetch word
				Query r = QueryFactory.query(v);
				//find next word in Vocabulary with the query
				Word w = this.getVocabulary().get(r);
				//replace with the parser the variable found with the new word.
				parser.replace(v, w);
			}
			//loop until no variable is found
			//note : a variable can be replaced by a text containing also one or several variables !
		}while(v != null);
		
		//format final text
		StringBuilder formated = new StringBuilder(parser.toString());
		for(Formater f : this.formaters){
			f.format(formated);
		}

		return formated.toString();
	}
	
}
