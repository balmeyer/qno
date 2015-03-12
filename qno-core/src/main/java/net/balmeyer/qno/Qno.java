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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.balmeyer.qno.dico.Definition;
import net.balmeyer.qno.dico.DictionaryCSV;
import net.balmeyer.qno.impl.PlainWord;
import net.balmeyer.qno.impl.WordBagImpl;
import net.balmeyer.qno.query.Query;
import net.balmeyer.qno.query.QueryFactory;
import net.balmeyer.qno.text.ElisionFormater;
import net.balmeyer.qno.text.Formater;
import net.balmeyer.qno.text.SimpleFormater;
import net.balmeyer.qno.text.SimpleTextBuilder;
import net.balmeyer.qno.text.TextBuilder;
import net.balmeyer.qno.text.Variable;

/**
 * Main class of Qno engine.
 * 
 * @author JB Balmeyer
 * 
 */
public class Qno {

	// with a default Vocabulary
	private Vocabulary vocab = new Vocabulary();

	private List<Formater> formaters = new ArrayList<Formater>();

	private File base;

	public Qno() {
		// add default dictionary
		try {
			this.add("dico_fr.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Pattern path is missng");
			System.exit(1);
		}

		// generate text
		Qno qno = new Qno();
		try {

			File f = new File(args[0]);

			qno.load(f);
			String text = qno.execute();
			System.out.println(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		DictionaryCSV doc = (DictionaryCSV) qno.getVocabulary().getSource(
				Vocabulary.DICTIONARY);

		Definition tag = doc.findWord("poilue");
		System.out.println(tag);
		*/
	}

	/**
	 * Current Vocabulary used to generate text.
	 * 
	 * @return
	 */
	public Vocabulary getVocabulary() {
		return vocab;
	}

	public void setVocabulary(Vocabulary vocab) {
		this.vocab = vocab;
	}

	/**
	 * Formater list, used to enhance text when generation is finished.
	 * 
	 * @return
	 */
	public List<Formater> getFormater() {
		return formaters;
	}

	/**
	 * Add a @Formater to list.
	 * 
	 * @param formater
	 */
	public void addFormater(Formater formater) {
		// check if formater already exists.
		for (Formater f : this.formaters) {
			if (f.getClass().equals(formater.getClass()))
				return;
		}

		this.formaters.add(formater);
	}

	/**
	 * Execute text generation, using a pattern provided by @Vocabulary
	 * instance.
	 * 
	 * @return
	 */
	public String execute() {
		return this.execute(this.getVocabulary().getPattern().toString());
	}

	/**
	 * Execute a text generation based on a specified pattern.
	 * 
	 * Replace variables in given pattern, then enhance final text with @Formater
	 * instances.
	 * 
	 * @param pattern
	 * @return
	 */
	public String execute(String pattern) {

		// instantiate a new parser
		TextBuilder parser = Qno.newTextBuilder();
		// specify the pattern used to generate text -
		parser.setPattern(pattern);

		// variable
		Variable v = null;
		do {
			// find next variable in pattern
			v = parser.nextVariable();

			if (v != null) {
				// build request from variable to fetch word
				Query r = QueryFactory.query(v);
				// find next word in Vocabulary with the query
				Word w = this.getVocabulary().get(r);
				// replace with the parser the variable found with the new word.
				parser.replace(v, w);
			}
			// loop until no variable is found
			// note : a variable can be replaced by a text containing also one
			// or several variables !
		} while (v != null);

		// format final text
		StringBuilder formated = new StringBuilder(parser.toString());
		for (Formater f : this.formaters) {
			f.format(formated);
		}

		return formated.toString();
	}

	public static Word word(String expression) {
		return new PlainWord(expression);
	}

	/**
	 * Create a new instance of TextBuilder
	 * 
	 * @return
	 */
	private static TextBuilder newTextBuilder() {
		return new SimpleTextBuilder();
	}

	/**
	 * Load Vocabulary from a configuration file.
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public void load(File file) throws IOException {

		// dir
		this.setDirectory(file.getParentFile());

		if (!file.exists()) throw new FileNotFoundException(file.toString());
		
		URL url = file.toURI().toURL();
		if (url == null) {
			System.out.println("Can't create URL from : " + url);
		}
		// add config to the empty qno object
		add(url);

		// add formaters
		addFormater(new SimpleFormater());
		addFormater(new ElisionFormater());

	}

	public void setDirectory(File directory) {
		//System.out.println("Current directory is : " + directory);
		this.base = directory;
	}

	public File getDirectory() {
		return this.base;
	}

	/**
	 * Load a configuration file for text generation and add infos to a @Qno
	 * instance.
	 * 
	 * @param qno
	 * @param path
	 * @throws IOException
	 */
	private void add(String path) throws IOException {
		//System.out.println("Path : " + path);

		// current file ?
		File file = new File(path);

		if (file.exists()) {
			this.add(file);
		} else {
			// with base
			file = new File(this.getDirectory() + File.separator + path);
			if (file.exists()) {
				this.add(file);
			} else {
				//
				URL url = Vocabulary.class.getClassLoader().getResource(path);
				if (url == null) {
					throw new IllegalArgumentException("URL not found : " + path);
				}
				add(url);
			}
		}
	}

	/**
	 * Load a configuration file for text generation and add infos to a @Qno
	 * instance.
	 * 
	 * @param path
	 * @throws IOException
	 */
	private void add(URL url) throws IOException {
		
		if (url == null) throw new IllegalArgumentException("Url cant' be null");
		
		//System.out.println(url);
		// open input stream to read text file
		InputStream inputStream = url.openStream();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream, "UTF-8"));

		String line = "";

		// patterns found are added in a "WordBag" object.
		WordBag patterns = new WordBagImpl();
		patterns.setID(Vocabulary.PATTERN_ID);

		// currentMap is the list where words found arred currently added.
		// Fist list is the list pattern.
		WordBag currentMap = patterns;
		List<WordBag> allwords = new ArrayList<WordBag>();
		allwords.add(patterns);

		// current expression
		StringBuilder currentExpression = new StringBuilder();
		boolean inExpression = false;

		// read line
		while (line != null) {
			line = reader.readLine();

			if (line != null) {
				line = line.trim();

				// comments
				if (line.startsWith("#"))
					continue;

				// import a configuration file
				if (line.startsWith("@import")) {
					add(line.substring(7).trim());
					continue;
				}

				// add a formater
				if (line.startsWith("@format")) {
					this.addFormater(createFormaterFromClassName(line
							.substring(7).trim()));
					continue;
				}

				// new words list is found. The word after % symbol is the
				// variable/list name.
				if (line.startsWith("%")) {
					currentMap = (WordBag) WordSourceFactory.bag(line);
					allwords.add(currentMap);
					continue;
				}

				// new pattern
				if (line.startsWith("<")) {
					// check last
					if (inExpression) {
						throw new IllegalStateException(
								"Last sign '<' was not closed");
					}

					inExpression = true;
					currentExpression.setLength(0);
					// add rest of line
					if (line.length() > 1)
						currentExpression.append(line.substring(1));
					continue;
				}

				if (inExpression) {
					// end of expression
					if (line.endsWith(">")) {
						// end of expression
						inExpression = false;
						if (line.length() > 0)
							line = line.substring(0, line.length() - 1);
					}

					// in expression, waiting for closing sign
					if (currentExpression.length() > 0)
						currentExpression.append("\r\n");
					currentExpression.append(line);

					if (inExpression)
						continue;

				} else {
					// simple line
					currentExpression = new StringBuilder(line);
				}

				if (currentExpression.length() > 0) {
					currentMap.addRawData(currentExpression.toString());
				}

			}
		}

		// check
		if (inExpression) {
			throw new IllegalStateException("malformated text. "
					+ " Check patterns and < and > signs.");
		}

		// keep all maps
		this.getVocabulary().add(allwords);
	}

	private void add(File file) throws MalformedURLException, IOException {
		this.add(file.toURI().toURL());
	}

	/**
	 * Create a @Formater instance from a class name found in configuration
	 * file.
	 * 
	 * @param clazz
	 * @return
	 */
	private static Formater createFormaterFromClassName(String clazz) {

		Formater instance = null;

		Class<?> cl = null;
		try {
			cl = Qno.class.getClassLoader().loadClass(clazz);
			instance = (Formater) cl.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		if (instance == null) {
			throw new IllegalArgumentException("impossible to create class : "
					+ clazz);
		}

		return instance;

	}

}
