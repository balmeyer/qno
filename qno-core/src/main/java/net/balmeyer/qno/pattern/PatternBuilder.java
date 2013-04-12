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
 * Object that analyze a pattern with options possible and give "ready to replace" canva
 * with variables.
 * @author JB Balmeyer
 *
 */
public interface PatternBuilder {

	
	public String buildPattern(String text);
	
	public void buildPattern(StringBuilder sb);
	
}
