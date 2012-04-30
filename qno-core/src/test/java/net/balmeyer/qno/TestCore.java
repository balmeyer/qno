package net.balmeyer.qno;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

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
		
		Word w1 = new PlainWord(variable);
		
		assertEquals(variable, w1.toString());
		
		WordMap map = new PlainWordMap();
		map.add(w1);
		
		Word testWord1 = map.next();
		Word testWord2 = map.next();
		
		assertEquals(testWord1, testWord2);
		assertEquals(w1, testWord1);
		assertEquals(w1, testWord2);
				
		
	}
	
	//@Test
	public void wordBuilder() {
		WordBuilder wb = new WordBuilder();
		try {
			wb.load("master.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail("Exception : " + e.toString());
		}
	}

}
