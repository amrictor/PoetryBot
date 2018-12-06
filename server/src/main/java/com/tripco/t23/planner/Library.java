// PoetryBot Assignment
// Author: Abigail Rictor
// Date: Nov 4, 2018
// Class: CS164
// Email: amrictor@rams.colostate.edu
package com.tripco.t23.planner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Library {
	final int TOTAL_POEMS = 10214;

	Poem[] library = new Poem[TOTAL_POEMS];
	ArrayList<String> poems = new ArrayList<String>();
	ArrayList<Integer> randomNumbers = new ArrayList<Integer>();

	public Library() {

		try {
			for(int i = 1; i<=library.length; i++) {
				System.out.printf("%.3f\n", 100*(double)i/library.length);
				JsonParser jP = new JsonParser();
				JsonElement poem = jP.parse(readResource("poems/"+ i +".json"));
				Gson gson = new Gson();
				Poem p = gson.fromJson(poem, Poem.class);
				p.clean();
				Collections.addAll(poems, p.text);
				library[i-1] = p;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Library initialization complete. Poems length: " + poems.size());

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
}
