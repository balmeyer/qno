package net.balmeyer.qno;

import org.junit.Test;

import static net.balmeyer.qno.QnoFactory.*;
import static org.junit.Assert.assertEquals;

public class TestEngine {

	@Test
	public void test() {
		QnoEngine engine = new QnoEngine();
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
		
		assertEquals("hello alpha hello beta hello alpha hello beta",result);
		
	}

}
