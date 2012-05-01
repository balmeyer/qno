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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import net.balmeyer.qno.dico.Dictionary;
import net.balmeyer.qno.dico.Entry;
import net.balmeyer.qno.dico.EntryQuery;
import net.balmeyer.qno.dico.Genre;
import net.balmeyer.qno.dico.Type;
import net.balmeyer.qno.query.Query;
import net.balmeyer.qno.query.QueryFactory;
import net.balmeyer.qno.text.Variable;

import org.junit.Test;

public class TestDictionary {

	@Test
	public void testBuildEntry() {
		String nom = "maison";
		String def = "nf";
		
		Word w = Dictionary.buildEntry(nom, def);
		assertNotNull(w);
		
		Entry e = (Entry) w;
		assertNotNull(e);
		
		assertEquals(Type.nom , e.getType());
		assertEquals(Genre.feminin , e.getGenre());
		assertEquals(nom , e.toString());
		assertEquals(nom , w.toString());
		
		//
		nom = "outil";
		def = "nm";
		e = Dictionary.buildEntry(nom, def);
		
		assertEquals(Type.nom , e.getType());
		assertEquals(Genre.masculin , e.getGenre());
		assertEquals(nom , e.toString());
		
		//
		nom = "petit";
		def = "adj";
		e = Dictionary.buildEntry(nom, def);
		
		assertEquals(Type.adjectif , e.getType());
		assertNull(e.getGenre());
		assertEquals(nom , e.toString());
		
	}

	@Test
	public void testAdd() {
		String nom = "outil";
		String def = "nm";
		Entry e = Dictionary.buildEntry(nom, def);
		
		assertEquals(Type.nom , e.getType());
		assertEquals(Genre.masculin , e.getGenre());
		assertEquals(nom , e.toString());
		
		Dictionary d = new Dictionary();
		assertNotNull(d.getEntries());
		assertEquals(0, d.getEntries().size());
		d.add(e);
		assertEquals(1, d.getEntries().size());
		d.add(e);
		assertEquals(1, d.getEntries().size());
	}

	@Test
	public void testEntryQuery(){
		EntryQuery eq = new EntryQuery("nm");
		
		assertEquals(Vocabulary.DICTIONARY , eq.getVariableName());
		assertEquals(Type.nom, eq.getType());
		assertEquals(Genre.masculin, eq.getGenre());
	}
	
	@Test
	public void testAddVocabulary() throws UnsupportedEncodingException, IOException{
		Dictionary d = Utils.build("dictionary.txt");
		Vocabulary v = new Vocabulary();
		
		v.add(d);
		
		Word w1 = v.get(new EntryQuery("nm"));
		assertNotNull(w1);
		
		Word w2 = v.get(QueryFactory.query(".","nm"));
		assertNotNull(w2);
	}
	
	
	
	@Test
	public void testBuildAll() throws UnsupportedEncodingException, IOException{
		Dictionary d = Utils.build("dictionary.txt");
		
		assertTrue(d.getEntries().size() > 1000);
		
		//test request
		Query r = QueryFactory.query("","nm");
		assertNotNull(r);
		assertEquals(Vocabulary.DICTIONARY, r.getVariableName());
		
		Word w = d.get(r);
		assertNotNull(w);
		
		Entry e = (Entry) w;
		assertNotNull(e);
		assertEquals(Type.nom, e.getType());
		assertEquals(Genre.masculin, e.getGenre());
		
		//-----
		Variable v = new Variable();
		v.setProperty("nf");
		r = QueryFactory.query(v);
		assertNotNull(r);
		assertEquals(Vocabulary.DICTIONARY, r.getVariableName());
		
		w = d.get(r);
		assertNotNull(w);
		
		e = (Entry) w;
		assertNotNull(e);
		assertEquals(Type.nom, e.getType());
		assertEquals(Genre.feminin, e.getGenre());
		
	}
	
}
