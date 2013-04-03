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

import static net.balmeyer.qno.QnoFactory.word;
import static net.balmeyer.qno.WordSourceFactory.bag;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestEngine {

	@Test
	public void test() {
		Qno engine = new Qno();
		Vocabulary v = new Vocabulary();

		WordBag b1 = bag("a");
		WordBag b2 = bag("b");

		b1.add(word("alpha"));
		b2.add(word("beta"));

		v.add(b1);
		v.add(b2);
		engine.setVocabulary(v);

		String text = "hello ${a} hello ${b} hello ${a} hello ${b}";

		String result = engine.execute(text);

		assertEquals("hello alpha hello beta hello alpha hello beta", result);

	}

	@Test
	public void testPatternWithWar() {
		Qno engine = new Qno();
		Vocabulary v = new Vocabulary();

		WordBag b1 = bag("a");
		WordBag b2 = bag("b");

		b1.add(word("alpha"));
		b2.add(word("beta"));

		v.add(b1);
		v.add(b2);
		engine.setVocabulary(v);

		for (int i = 0; i < 50; i++) {
			String text = "${a}|${b}";
			String result = engine.execute(text);

			assertTrue(result.equals("alpha") || result.equals("beta"));

			text = "{${a}|${b}}";
			result = engine.execute(text);
			assertTrue(result.equals("alpha") || result.equals("beta"));

			text = "{${a}|${b}}|alpha|${b}";
			result = engine.execute(text);
			assertTrue(result.equals("alpha") || result.equals("beta"));

			text = "{${a}|${b}}|alpha|${a}${b}|${a}[${b}]";
			result = engine.execute(text);
			assertTrue(result.equals("alpha") || result.equals("beta")
					|| result.equals("alphabeta"));

		}
	}

}
