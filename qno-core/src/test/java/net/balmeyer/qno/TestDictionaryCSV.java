package net.balmeyer.qno;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.balmeyer.qno.dico.DictionaryCSV;
import net.balmeyer.qno.dico.EntryQuery;

import org.junit.Test;

public class TestDictionaryCSV {

	@Test
	public void test() {
		DictionaryCSV csv = new DictionaryCSV();
		csv.addRawData("texte	type	groupe	pluriel	feminin	femininpluriel");
		csv.addRawData("maison	nf");
		
		assertEquals(Vocabulary.DICTIONARY,csv.getID());
		
		EntryQuery q = new EntryQuery("nf");
		Word w = csv.get(q);
		assertNotNull(w);
		assertEquals("maison", w.toString());
		
		csv.addRawData("verre	nm");
		q = new EntryQuery("nm");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("verre", w.toString());
		
		//adj
		csv.addRawData("vert	adj");
		q = new EntryQuery("adj");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("vert", w.toString());
		
		q = new EntryQuery("adj:f");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("verte", w.toString());
		
		q = new EntryQuery("adj:p");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("verts", w.toString());
		
		q = new EntryQuery("adj:pf");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("vertes", w.toString());
		
		q = new EntryQuery("adj:fp");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("vertes", w.toString());
		
		q = new EntryQuery("adj");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("vert", w.toString());
		
		q = new EntryQuery("nf:p");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("maisons", w.toString());
		
		q = new EntryQuery("nm:p");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("verres", w.toString());
		
		q = new EntryQuery("nf");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("maison", w.toString());
		
		q = new EntryQuery("nm");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("verre", w.toString());
	}

}
