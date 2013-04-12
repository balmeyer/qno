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
package net.balmeyer.qno.pattern;

/**
 * 
 * @author jean-baptiste
 *
 */
public class Delimiter {

	public char start;
	public char end;

	private Occurrence occurence;
	
	public Delimiter(){}
	
	public Delimiter(char start, char end, int min , int max){
		this.start = start;
		this.end = end;
		this.setOccurence(new Occurrence(min, max));
	}

	/**
	 * Occurrence associated with this marker.
	 * Determine how the data inside marker are optional or not
	 * @return
	 */
	public Occurrence getOccurence() {
		return occurence;
	}

	public void setOccurence(Occurrence occurence) {
		this.occurence = occurence;
	}
	
	@Override
	public String toString(){
		return this.start + "..." + this.end;
	}
}
