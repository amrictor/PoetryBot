// PoetryBot Assignment
// Author: Abigail Rictor
// Date: Nov 4, 2018
// Class: CS164
// Email: amrictor@rams.colostate.edu
package com.tripco.t23.planner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Library {
	Random r = new Random();
	Poem[] library = new Poem[10216];
	String[] allText;
	ArrayList<String> poems = new ArrayList<String>();
	public Library() {

		try {
			for(int i = 1; i<=10216; i+=(r.nextInt(25)+25)) {
					JsonParser jP = new JsonParser();
					JsonElement poem = jP.parse(readResource("poems/"+i+".json"));
					Gson gson = new Gson();
					Poem p = gson.fromJson(poem, Poem.class);
					p.clean();
					Collections.addAll(poems, p.text);
					library[i-1] = p;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Library initialization complete.");
		
	}
	private String readResource(String filename) {
		String line;
		StringBuilder strBuild = new StringBuilder();
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename),
							Charset.defaultCharset()));
			while ((line = bufferedReader.readLine()) != null) {
				strBuild.append(line).append('\n');
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return strBuild.toString();
	}
	private static String readFile(String path/*, Charset encoding*/) throws IOException {
	    byte[] encoded = Files.readAllBytes(Paths.get(path));
	    return new String(encoded/*, encoding*/);
	}

	  private static File[] getResourceFolderFiles (String folder) {
		    ClassLoader loader = Thread.currentThread().getContextClassLoader();
		    URL url = loader.getResource(folder);
		    System.out.println(url.toString());
		    String path = url.getPath();
		    File f = new File(path);
		    
		    System.out.println(f.exists());
		    
		    return new File(path).listFiles();
		  }
}
