package net.balmeyer.qno;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPattern {

	@Test
	public void testPatternSimple() {
		String pattern = "hello [, hello ]!";
		
		Qno q = new Qno();
		q.setVocabulary(new Vocabulary());
		WordBag patterns = WorderFactory.patterns();
		q.getVocabulary().add(patterns);
		
		patterns.addRawData(pattern);
		
		//simple
		Object testp = q.getVocabulary().getPattern();
		assertEquals(pattern.toString(), testp.toString());
		
	}

}
