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
package net.balmeyer.qno.query;

import net.balmeyer.qno.dico.EntryQuery;
import net.balmeyer.qno.text.Variable;

/**
 * Query builder
 * @author Balmeyer
 *
 */
public final class QueryFactory {

	private QueryFactory() {}
	
	public static Query query(Variable var){
		if (var.getProperty() != null){
			return query(null,var.getProperty());
		}
		return query(var.getID());
	}
	
	public static Query query(String expression){
		return new SimpleQuery(expression);
	}
	
	public static Query query(String name, String definition){
		return new EntryQuery(definition);
	}
}
