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

import static org.junit.Assert.*;
import net.balmeyer.qno.text.ElisionFormater;
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
		
		//trim
		sb = new StringBuilder("   hel  lo ");
		f.format(sb);
		assertEquals("Hel lo", sb.toString());
		
		//point
		sb = new StringBuilder("hello .world. ");
		f.format(sb);
		assertEquals("Hello. World.", sb.toString());
		
		//lines
		sb = new StringBuilder("haha.\r\n hihi.");
		f.format(sb);
		assertEquals("Haha.\r\nHihi.", sb.toString());
	}

	@Test
	public void testElisionFormater(){
	
		Formater ef = new ElisionFormater();
		
		StringBuilder pattern = new StringBuilder(
				"Le oiseau et la aube, le pain, le avion de oncle toto de le homme."
				);
		ef.format(pattern);
		assertEquals("L'oiseau et l'aube, le pain, l'avion d'oncle toto de l'homme.",pattern.toString());
		
		//
		pattern = new StringBuilder(
				"Aile oiseau, le abris, Bella avion."
				);
		ef.format(pattern);
		assertEquals("Aile oiseau, l'abris, Bella avion.",pattern.toString());
		
		
		pattern = new StringBuilder(
				"...le ouzo. Le a."
				);
		ef.format(pattern);
		assertEquals("...l'ouzo. L'a.",pattern.toString());
	}
	
	@Test
	public void testEngineWithFormaters(){

	}
}
