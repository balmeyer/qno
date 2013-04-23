package net.balmeyer.qno;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
		
	}

}
