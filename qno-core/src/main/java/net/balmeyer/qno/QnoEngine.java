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

import net.balmeyer.qno.text.Parser;
import net.balmeyer.qno.text.Variable;


/**
 * 
 * @author JB Balmeyer
 *
 */
public class QnoEngine  {

	private Vocabulary vocab;
	
	public QnoEngine(){

	}

	public QnoEngine(Vocabulary vocab){
		this.vocab = vocab;
	}
	
	public Vocabulary getVocabulary() {
		return vocab;
	}

	public void setVocabulary(Vocabulary vocab) {
		this.vocab = vocab;
	}
	
	/**
	 * Execute text !
	 * @return
	 */
	public String execute(){
		return this.execute(this.getVocabulary().getPattern().toString());
	}
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	public String execute(String text){
		//instantiate a new parser
		Parser parser = QnoFactory.newParser();
		parser.setText(text);
		
		//variable
		Variable v = null ;
		do {
			v = parser.nextVariable();
			
			if (v != null) {
				//build request from variable
				Request r = QnoFactory.request(v);
				//find next word
				Word w = this.getVocabulary().get(r);
				//replace
				parser.replace(v, w);
			}
		}while(v != null);
		
		return parser.toString();
	}
	
}
