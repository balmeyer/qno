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

	@Test
	public void accordSimple(){
		DictionaryCSV csv = new DictionaryCSV();
		csv.addRawData("texte	type	groupe	pluriel	feminin	femininpluriel");
		csv.addRawData("vert	adj");
		
		EntryQuery q = new EntryQuery("adj:f");
		Word w = csv.get(q);
		assertNotNull(w);
		assertEquals("verte", w.toString());
		
		q = new EntryQuery("adj:m");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("vert", w.toString());

		q = new EntryQuery("adj:p");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("verts", w.toString());
		
		q = new EntryQuery("adj:fp");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("vertes", w.toString());
	}
	
	@Test
	public void adjectifAccent(){
		DictionaryCSV csv = new DictionaryCSV();
		csv.addRawData("texte;type;groupe;pluriel;feminin;femininpluriel");
		csv.addRawData("passé;adj");
		
		EntryQuery q = new EntryQuery("adj:f");
		Word w = csv.get(q);
		assertNotNull(w);
		assertEquals("passée", w.toString());
		
		q = new EntryQuery("adj:m");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("passé", w.toString());

		q = new EntryQuery("adj:p");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("passés", w.toString());
		
		q = new EntryQuery("adj:fp");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("passées", w.toString());
	}
	
	@Test
	public void accordComplique(){
		DictionaryCSV csv = new DictionaryCSV();
		csv.addRawData("texte	type	groupe	pluriel	feminin	femininpluriel");
		csv.addRawData("rouge	adj");
		
		EntryQuery q = new EntryQuery("adj:f");
		Word w = csv.get(q);
		assertNotNull(w);
		assertEquals("rouge", w.toString());
		
		q = new EntryQuery("adj:m");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("rouge", w.toString());

		q = new EntryQuery("adj:p");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("rouges", w.toString());
		
		q = new EntryQuery("adj:fp");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("rouges", w.toString());
	}
	
	@Test
	public void accordPluriel(){
		DictionaryCSV csv = new DictionaryCSV();
		csv.addRawData("texte,type,groupe,pluriel,feminin,femininpluriel");
		csv.addRawData("vieux,adj,,,vieille,");
		
		EntryQuery q = new EntryQuery("adj:f");
		Word w = csv.get(q);
		assertNotNull(w);
		assertEquals("vieille", w.toString());
		
		q = new EntryQuery("adj:m");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("vieux", w.toString());

		q = new EntryQuery("adj:p");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("vieux", w.toString());
		
		q = new EntryQuery("adj:fp");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("vieilles", w.toString());
		
		///------------------
		csv = new DictionaryCSV();
		csv.addRawData("texte,type,groupe,pluriel,feminin,femininpluriel");
		csv.addRawData("beau,adj,,beaux,belle,belles");
		
		q = new EntryQuery("adj:f");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("belle", w.toString());
		
		q = new EntryQuery("adj:m");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("beau", w.toString());

		q = new EntryQuery("adj:p");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("beaux", w.toString());
		
		q = new EntryQuery("adj:fp");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("belles", w.toString());
		
	}
	
	@Test
	public void invariable(){
		DictionaryCSV csv = new DictionaryCSV();
		csv.addRawData("texte,type,groupe,pluriel,feminin,femininpluriel");
		csv.addRawData("orange,adji,,,,");
		
		EntryQuery q = new EntryQuery("adj:f");
		Word w = csv.get(q);
		assertNotNull(w);
		assertEquals("orange", w.toString());
		
		q = new EntryQuery("adj:m");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("orange", w.toString());

		q = new EntryQuery("adj:p");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("orange", w.toString());
		
		q = new EntryQuery("adj:fp");
		w = csv.get(q);
		assertNotNull(w);
		assertEquals("orange", w.toString());
	}
	
	@Test
	public void emptyWord(){
		DictionaryCSV csv = new DictionaryCSV();
		csv.addRawData("texte,type,groupe,pluriel,feminin,femininpluriel");
		csv.addRawData("rien,null,,,,");
		csv.addRawData("patate,,,,,");
		csv.addRawData("truc,nm,,,,");
		
		EntryQuery q = new EntryQuery("nm");
		for(int i = 0 ; i < 200 ; i ++){
			Word w = csv.get(q);
			assertNotNull(w);
			assertEquals("truc", w.toString());
		}
	}
	
	@Test
	public void adverbe(){
		DictionaryCSV csv = new DictionaryCSV();
		csv.addRawData("texte,type,groupe,pluriel,feminin,femininpluriel");
		csv.addRawData("cordialement,adv");
		
		EntryQuery q = new EntryQuery("adv");
		Word w = csv.get(q);
		assertNotNull(w);
		assertEquals("cordialement", w.toString());
	}
}
