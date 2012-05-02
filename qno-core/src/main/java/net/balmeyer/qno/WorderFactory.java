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
 * Build any worder from name / class
 * @author JB Balmeyer
 *
 */
public class WorderFactory {

	private WorderFactory(){}

	public static WordBag bag(String expression){
		
		if (!expression.contains("[")) return bag(expression, "");
		
		String name = expression.substring(1, expression.indexOf("]"));
		String claz = null;
		if (expression.indexOf("]") < expression.length()){
			claz = expression.substring(expression.indexOf("]")+1);
		}
		
		return bag(name, claz);
	}
	
	@SuppressWarnings("unchecked")
	public static WordBag bag(String name , String clazName){
		
		if (clazName == null || clazName.trim().length() == 0){
			return bag(name, WordBagImpl.class);
		}
		
		Class<Worder> claz = null;
		try {
			claz = (Class<Worder>) WorderFactory.class.getClassLoader().loadClass(clazName);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Class not found : " + clazName,e);
		}
		return bag(name,claz);
		
	}
	
	public static WordBag bag(String name , Class claz){
		WordBag bag = null;
		try {
			//new instance
			bag = (WordBag) claz.newInstance();
			bag.setID(name);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bag;
	}
}
