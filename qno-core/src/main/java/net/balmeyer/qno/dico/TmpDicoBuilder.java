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

package net.balmeyer.qno.dico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TmpDicoBuilder {

	public static void main(String[] args) throws IOException {
		

		File dico = new File("D:/dictionary.txt");
		FileWriter fw = new FileWriter(dico);
		PrintWriter writer = new PrintWriter(fw);
		
		File f = new File("D:/DicFra.csv");

		FileInputStream fis = new FileInputStream(f);
		InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");

		BufferedReader reader = new BufferedReader(isr);
		try {
			do {
				String line = reader.readLine();
				if (line == null)
					break;
				writer.write(parse(line));
				writer.write("\n");

			} while (true);

		} catch (IOException ioex) {

		} finally {
			reader.close();
			writer.close();
			fis.close();
			fw.close();
		}
	}
	
	private static String parse(String text){
		
		String word = null;
		String def = null;
		boolean inword = false;
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			
			if (c == '"'){
				if (inword){
					//new word
					if (word == null) {
						word = sb.toString();
					} else {
						def = sb.toString();
						break;
					}
					sb.setLength(0);
				} 
				
				inword = !inword;
				continue;
			}
			
			if (inword) sb.append(c);
			
		}
		
		
		return word + "," + getType(def);
	}
	
	private static String getType(String def){
		
		
		String t = def;
		if (t.indexOf("\n") >=0) t = t.substring(0, t.indexOf("\n"));
		
		String invar = "";
		if (t.indexOf("inv.") >=0) invar = "i";
		
		if (t.contains("n. m.")) return "nm" + invar;
		if (t.contains("n. f.")) return "nf" + invar;
		if (t.contains("adj.")) return "adj" + invar;
		if (t.contains("adv.")) return "adv" + invar;
		if (t.contains("v. tr.")) return "v";
		if (t.contains("v. intr.")) return "vi";
		return null;
	}
}
