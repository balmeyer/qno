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

import static net.balmeyer.qno.WordSourceFactory.bag;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import net.balmeyer.qno.impl.PlainWord;
import net.balmeyer.qno.query.QueryFactory;
import net.balmeyer.qno.text.SimpleTextBuilder;
import net.balmeyer.qno.text.TextBuilder;
import net.balmeyer.qno.text.Variable;

import org.junit.Test;

/**
 * @author vovau
 *
 */
public class TestTextBuilder {

	/**
	 * Text simple replace
	 */
	@Test
	public void testSimpleVarAndReplace() {
		
		TextBuilder parser = new SimpleTextBuilder();
		
		assertNotNull(parser);
		
		String text = "test ${Test} tata";
		
		parser.setPattern(text);
		
		assertEquals(text,parser.getCurrentText());
		
		Variable var = parser.nextVariable();
		assertNotNull(var);
		assertEquals(5, var.getStart());
		assertEquals(11,var.getEnd());
		assertEquals("${Test}", var.getText());
		assertEquals("test", var.getID());
		
		//replace
		parser.replace(var, Qno.word("tut"));
		
		assertEquals("test tut tata", parser.getCurrentText());
		
	}

	@Test
	public void testDoubleVar(){
		TextBuilder parser = new SimpleTextBuilder();
		
		assertNotNull(parser);
		
		String text = "one ${two} three ${four}. five ${six}, ${two}! seven";
		parser.setPattern(text);
		
		assertEquals(text,parser.getCurrentText());
		
		int i = 0;
		
		Variable v = null;
		do {
			v = parser.nextVariable();
			if (v != null){
				assertTrue (v.getEnd() > v.getStart());
				assertTrue(v.getID().length() <= 4);
				assertTrue(v.getText().length() > 5);
				parser.replace(v, Qno.word("ok" + (i++)));
			}
		} while(v != null);
		
		assertEquals("one ok0 three ok1. five ok2, ok3! seven",parser.getCurrentText());
	}
	
	/**
	 * 
	 */
	@Test
	public void testComplete(){
		TextBuilder parser = new SimpleTextBuilder();
		
		assertNotNull(parser);
		
		String text = "hello ${a} hello ${b} hello ${a} hello ${b}";
		parser.setPattern(text);
		
		assertEquals(text,parser.getCurrentText());
		
		Vocabulary voca = new Vocabulary();
		WordBag bagA = bag("a");
		bagA.add(Qno.word("alpha"));
		WordBag bagB = bag("b");
		bagB.add(Qno.word("beta"));
		
		voca.add(bagA);
		voca.add(bagB);
		
		
		Variable v = null;
		do {
			v = parser.nextVariable();
			if (v != null){
				assertTrue (v.getEnd() > v.getStart());
				assertTrue(v.getID().length() ==1);
				assertTrue(v.getText().length() >= 4);
				
				assertNotNull(v.getID());
				assertNotNull(v.getText());
				Word w = voca.get(QueryFactory.query(v.getID()));
				
				parser.replace(v, w);
			}
		} while(v != null);
		
		assertEquals("hello alpha hello beta hello alpha hello beta",parser.getCurrentText());
	}
	
	@Test
	public void testVarNull(){
		String pattern = "${rien}";
		
		TextBuilder builder = new SimpleTextBuilder();
		
		builder.setPattern(pattern);
		Variable v = builder.nextVariable();
		
		assertNotNull(v);
		assertEquals("${rien}",v.getText());
		assertEquals("rien",v.getID());
		
		builder.setPattern("$");
		v = builder.nextVariable();
		assertNull(v);
	}
	
	@Test
	public void testLightVariable(){
		
		String pattern = "Test pattern $test pattern";
		
		TextBuilder builder = new SimpleTextBuilder();
		
		builder.setPattern(pattern);
		Variable v = builder.nextVariable();
		
		assertNotNull(v);
		assertEquals("$test", v.toString());
		assertEquals("test", v.getID());
		
		///-------------------
		builder.setPattern("aa $toto $tata. arg $titi! tutu");
		v = builder.nextVariable();
		assertEquals("toto", v.getID());
		builder.replace(v, new PlainWord("pouet"));
		assertEquals("aa pouet $tata. arg $titi! tutu" , builder.toString());
		v = builder.nextVariable();
		assertEquals("tata", v.getID());
		builder.replace(v, new PlainWord("urg"));
		assertEquals("aa pouet urg. arg $titi! tutu" , builder.toString());
		v = builder.nextVariable();
		assertEquals("titi", v.getID());
		builder.replace(v, new PlainWord("urg"));
		assertEquals("aa pouet urg. arg urg! tutu" , builder.toString());
		
		///-------------------
		builder.setPattern("$bip");
		v = builder.nextVariable();
		assertEquals("bip", v.getID());
		builder.replace(v, new PlainWord("foo"));
		assertEquals("foo" , builder.toString());
		
		builder.setPattern("hey $_bip !");
		v = builder.nextVariable();
		assertEquals("$_bip", v.toString());
		assertEquals(Vocabulary.DICTIONARY, v.getID());
		builder.replace(v, new PlainWord("foo"));
		assertEquals("hey foo !" , builder.toString());
		
		///-------------------
		
	}
}
