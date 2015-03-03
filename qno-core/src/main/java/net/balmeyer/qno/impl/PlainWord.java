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

import net.balmeyer.qno.Word;

/**
 * A simple Word in string value
 * @author Balmeyer
 *
 */
public class PlainWord implements Word {

	private String expr ;
	
	public PlainWord(String expression){
		this.expr = expression;
	}
	
	@Override
	public String toString(){
		return expr;
	}
	
}
