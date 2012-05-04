package net.balmeyer.qno;

import static org.junit.Assert.*;
import net.balmeyer.qno.text.Formater;
import net.balmeyer.qno.text.SimpleFormater;

import org.junit.Test;

public class TestFormater {

	@Test
	public void testSimpleFormat() {
		Formater f = new SimpleFormater();
		
		//majuscule
		StringBuilder sb = new StringBuilder("le petit chien. la vache.");
		f.format(sb);
		assertEquals("Le petit chien. La vache.", sb.toString());
		
		//double space
		sb = new StringBuilder("le petit  chien.  la vache.");
		f.format(sb);
		assertEquals("Le petit chien. La vache.", sb.toString());
	}

}
