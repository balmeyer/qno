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

import net.balmeyer.qno.impl.WordBagImpl;

/**
 * Build any WordSource from name / class
 * @author JB Balmeyer
 *
 */
public class WordSourceFactory {

	private WordSourceFactory(){}

	public static WordBag bag(String expression){
		
		//remove %
		expression = expression.replace(";", "").replace(",", "") ;
		String name = expression.replace("%","").trim();
		
		if (!expression.contains(" ")) return bag(name, "");
		
		///
		name = name.substring(0, name.indexOf(" ")+1).trim();
		
		
		String claz = null;
		if (expression.indexOf(" ") < expression.length()){
			claz = expression.substring(expression.indexOf(" ")+1);
		}
		
		return bag(name, claz);
	}
	
	@SuppressWarnings("unchecked")
	public static WordBag bag(String name , String clazName){
		
		if (clazName == null || clazName.trim().length() == 0){
			return bag(name, WordBagImpl.class);
		}
		
		Class<WordSource> claz = null;
		try {
			claz = (Class<WordSource>) WordSourceFactory.class.getClassLoader().loadClass(clazName);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Class not found : " + clazName,e);
		}
		return bag(name,claz);
		
	}
	
	/**
	 * Provides a @WordBag containing patterns (basic text structure )
	 * @return
	 */
	public static WordBag patterns(){
		return bag(Vocabulary.PATTERN_ID, "");
	}
	
	public static WordBag bag(String name , @SuppressWarnings("rawtypes") Class claz){
		WordBag bag = null;
		try {
			//new instance
			bag = (WordBag) claz.newInstance();
			bag.setID(name);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return bag;
	}
}
