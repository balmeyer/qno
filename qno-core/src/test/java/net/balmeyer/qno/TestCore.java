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
import static org.junit.Assert.fail;

import java.io.IOException;

import net.balmeyer.qno.impl.PlainRequest;
import net.balmeyer.qno.impl.PlainWord;
import net.balmeyer.qno.impl.PlainWordMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCore {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Simple word manipulation
	 */
	@Test
	public void plainWord(){
		
		final String variable = "Test !!";
		
		//create simple word et test "next"
		Word w1 = new PlainWord(variable);
		
		assertEquals(variable, w1.toString());
		
		WordBag map = new PlainWordMap("var1");
		
		assertEquals("var1", map.getID());
		
		map.add(w1);
		
		//loop to check random
		for (int i = 0 ; i < 20 ; i++) {
		
			Word testWord1 = map.get();
			Word testWord2 = map.get();
			
			assertEquals(testWord1, testWord2);
			assertEquals(w1, testWord1);
			assertEquals(w1, testWord2);
		}
				
		
	}
	
	@Test
	public void wordBuilder() {

		try {
			WorderBuilder.load("master.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail("Exception : " + e.toString());
		}
	}

	/**
	 * Test pattern
	 */
	@Test
	public void testPattern(){
		WordBag bag = WordBagFactory.build(Vocabulary.PATTERN_ID);
		bag.add(new PlainWord("toto"));
		
		//stuff
		WordBag words = WordBagFactory.build("test1");
		words.add(new PlainWord("pouet"));
		
		Vocabulary v = new Vocabulary();
		v.add(bag);
		
		String test = v.getPattern().toString();
		assertEquals("toto", test);
		
		
		
	}
	
	@Test
	public void testVar(){
		
		WordBag map1 = new PlainWordMap("a");
		WordBag map2 = new PlainWordMap("b");
		
		map1.add(new PlainWord("alpha"));
		map2.add(new PlainWord("beta"));
		
		//simple test
		for (int i = 0 ; i < 20 ; i++) {
		assertEquals("alpha", map1.get().toString());
		assertEquals("beta", map2.get().toString());
		}
		
		Vocabulary vocab = new Vocabulary();
		vocab.add(map1);
		vocab.add(map2);
		
		assertEquals(2, vocab.getMaps().size());
		
		//request on "a" map
		Request request = new PlainRequest("a");
		
		Word w1 = vocab.get(request);
		
	}
	
}
