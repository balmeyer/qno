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
import static org.junit.Assert.assertEquals;
import net.balmeyer.qno.pattern.Node;
import net.balmeyer.qno.pattern.Root;

import org.junit.Test;

public class TestPattern {

	@Test
	public void testNodeSimple() {
		String pattern = "hello [, hello ]!";
		
		Qno q = new Qno();
		q.setVocabulary(new Vocabulary());
		WordBag patterns = WorderFactory.patterns();
		q.getVocabulary().add(patterns);
		
		patterns.addRawData(pattern);
		
		//simple
		Object testp = q.getVocabulary().getPattern();
		assertEquals(pattern.toString(), testp.toString());
		
		//
		Root r = new Root();
		Node node = r.analyze(pattern);
		
		assertNotNull(node);
		assertNotNull(node.getChildren());
		assertEquals(1 , node.getChildren().size());
		assertEquals(pattern, node.toString());
		assertEquals(pattern, node.getText());
		for (Node sub : node.getChildren()) assertEquals(", hello ", sub.toString());
		
		//
		pattern = "hello [, hello ]! [deux] x {trois | quatre }";
		node = r.analyze(pattern);
		assertNotNull(node.getChildren());
		assertEquals(3 , node.getChildren().size());
		assertEquals(pattern, node.toString());
		
		//under node
		pattern = "hello [, hello ]! [deux [pouet]] x {trois | quatre [hello {caca}] } [cinq [1][2]{3}]";
		node = r.analyze(pattern);
		assertNotNull(node.getChildren());
		assertEquals(4 , node.getChildren().size());
		assertEquals(pattern, node.toString());
		for (Node sub : node.getChildren()) assertNotNull(sub.getChildren());
		assertEquals(1, node.getChildren().get(1).getChildren().size()); // [deux [pouet]]
		assertEquals(3, node.getChildren().get(3).getChildren().size()); // [cinq [1][2]{3}]
		
		
	}

}
