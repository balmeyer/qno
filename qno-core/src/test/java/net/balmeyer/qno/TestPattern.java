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
import static org.junit.Assert.assertTrue;
import net.balmeyer.qno.pattern.PatternBuilder;
import net.balmeyer.qno.pattern.PatternBuilderFactory;
import net.balmeyer.qno.pattern.SimplePatternBuilder;

import org.junit.Test;

public class TestPattern {

	/**
	 * Test simple pattern browsing
	 */
	@Test
	public void simpleSimlePattern(){
		String pattern = "one|two|three";
		
		SimplePatternBuilder spb = new SimplePatternBuilder();
		
		//simple split
		for(int i = 0 ; i < 100 ; i++){
			String result = spb.buildPattern(pattern);
			boolean check = result.equals("one") || result.equals("two") || result.equals("three");
			assertTrue(check);
		}
		
		//most complex split
		pattern = "a|b|{c|d}";
		for(int i = 0 ; i < 100 ; i++){
			String result = spb.buildPattern(pattern);
			boolean check = result.equals("a") || result.equals("b") || result.equals("c") || result.equals("d");
			assertTrue(check);
		}
		
		//most most complex split
		pattern = "a|{a|{a|a}}|a|{a|{a|a|a}}";
		for(int i = 0 ; i < 100 ; i++){
			String result = spb.buildPattern(pattern);
			boolean check = result.equals("a");
			assertTrue(check);
		}
		
	}
	
	
	@Test
	public void testPattern() {
		String pattern = "hello !";
		PatternBuilder r = PatternBuilderFactory.get();
		String result = r.buildPattern(pattern);

		assertEquals(pattern, result);

		pattern = "1234|5678";
		result = r.buildPattern(pattern);

		assertNotNull(result);
		assertEquals(4, result.length());

		for (int i = 0; i < 40; i++) {

			// Optional condition
			pattern = "123|456|{789|0ab}";
			result = r.buildPattern(pattern);
			assertEquals(3, result.length());

			pattern = "{123|456}|{78|0a}b|{cde|f{gh|ij}}";
			result = r.buildPattern(pattern);
			assertEquals(3, result.length());

			pattern = "{cde|f{gh|ij}}|{cde|f{gh|ij}}";
			result = r.buildPattern(pattern);
			assertEquals(3, result.length());

			pattern = "ab|{cd|ef}|{g{h|{i|j}}}";
			result = r.buildPattern(pattern);
			assertEquals(2, result.length());

			//
			pattern = "12[34]";
			result = r.buildPattern(pattern);
			assertTrue(result.length() == 2 || result.length() == 4);

			pattern = "12[34]|56{78|[90|ab]}";
			result = r.buildPattern(pattern);
			assertTrue(result.length() == 2 || result.length() == 4);

			pattern = "{hello}";
			result = r.buildPattern(pattern);
			assertEquals("hello", result);

			pattern = "{abc|def}";
			result = r.buildPattern(pattern);
			assertTrue(result.equals("abc") || result.equals("def"));

			pattern = "[hello]";
			result = r.buildPattern(pattern);
			assertTrue(result.equals("hello") || result.equals(""));

			pattern = "[abc|def]";
			result = r.buildPattern(pattern);
			assertTrue(result.equals("abc") || result.equals("def") || result.equals(""));

		}
	}

	@Test
	public void testNodeSimple() {
		String pattern = "hello [, hello ]! ${world}.";

		Qno q = new Qno();
		q.setVocabulary(new Vocabulary());
		WordBag patterns = WordSourceFactory.patterns();
		q.getVocabulary().add(patterns);

		patterns.addRawData(pattern);

		// simple
		Object testp = q.getVocabulary().getPattern();
		assertEquals(pattern.toString(), testp.toString());

		//
		//PatternBuilder r = new PatternBuilder();
		// Node node = r.analyze(pattern);

	}

}

