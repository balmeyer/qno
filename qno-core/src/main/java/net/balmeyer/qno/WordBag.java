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

/**
 * A simple @Word container with one a several words for an specified ID.
 * 
 * This ID is the variable name found in patterns, for instance : @{name}, where "name" is the ID
 * @author jean-baptiste Balmeyer
 *
 */
public interface WordBag extends WordSource{

	/** Word ID (the variable name found in pattern). */
	public String getID();
	
	public void setID(String id);
	
	/** Add raw data : a string word. */
	public void addRawData(String data);
	
	/** Add a @Word object. */
	public void add(Word w);
	
	/** Return a @Word. */
	public Word get();

	
}
