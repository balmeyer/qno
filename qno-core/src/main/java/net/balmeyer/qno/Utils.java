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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.balmeyer.qno.impl.WordBagImpl;

/**
 * 
 * @author JB Balmeyer
 *
 */
public final class Utils {

	private static Random rand;
	
	private Utils(){}
	
	
	public static Random getRandInstance(){
		if (rand == null){
			rand = new Random(System.currentTimeMillis());
		}
		return rand;
	}
	
	public static void check(boolean expression, String msg){
		if (!expression) throw new IllegalArgumentException(msg);
	}
	
	public static URL url(String path){
		return Utils.class.getClassLoader().getResource(path);
	}
	

}
