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

import net.balmeyer.qno.Word;

/**
 * Word that can be typed (verb, noun ,adjective, etc), with a strign definition.
 * @author balmeyer
 *
 */
public interface TypedWord extends Word {

	/** 
	 * Get raw definition (or type) in dictionary.
	 * Type is found in column "1" (second column) in the dictionary.csv file.
	 * @return
	 */
	public Definition getDefinition();
	
	/**
	 * Type of word
	 * @return
	 */
	public Type getType();
	
}
