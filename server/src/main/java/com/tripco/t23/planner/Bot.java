package com.tripco.t23.planner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Bot {
	private static Library lib = new Library();
	private static ArrayList<ArrayList<String>> lineStarters = new ArrayList<ArrayList<String>>();
	private static Map<ArrayList<String>, ArrayList<String>> wordMap;
	private static Map<String, ArrayList<String>> oneWordMap;
	private static Map<String, Map<ArrayList<String>, ArrayList<String>>> authorMap;
	public static int complete = 0;
	public Bot(){
		generateMap();
	}



	public static void generateMap(){

		wordMap = new ConcurrentHashMap<ArrayList<String>, ArrayList<String>>();
		oneWordMap = new ConcurrentHashMap<String, ArrayList<String>>();
		authorMap = new ConcurrentHashMap<String, Map<ArrayList<String>, ArrayList<String>>>();

		for(int i = 0 ; i<lib.library.length; i++){
			Poem p = lib.library[i];
			if(!authorMap.containsKey(p.author)) authorMap.put(p.author, new ConcurrentHashMap<ArrayList<String>, ArrayList<String>>());
			System.out.printf("%.3f\n", 100*((double)complete/lib.library.length));

			String[] words = removePunctuation(p.poem).toLowerCase().split(" "); //[,:;\"]* +[\",;: ]*
			boolean endOfSentence = true;
			ArrayList<String> start = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(words, 0, 2)));
			lineStarters.add(start);
			for(int j = 0; j < words.length-2; j++){
				//check if at end of sentence
				if(words[j].isEmpty());
				else if(words[j].charAt(words[j].length()-1)=='?'||words[j].charAt(words[j].length()-1)=='.'||words[j].charAt(words[j].length()-1)=='!'){
					endOfSentence = true;
					words[j]=words[j].substring(0, words[j].length()-1);
				}
				//add words to map
				if(!oneWordMap.containsKey(words[j])) oneWordMap.put(words[j], new ArrayList<String>());

				String[] temp = {words[j], words[j+1]};
				ArrayList<String> key =  new ArrayList<String>(Arrays.asList(temp));
				if(endOfSentence){
					lineStarters.add(key);
					endOfSentence = false;
				}
				if(!wordMap.containsKey(key)) wordMap.put(key, new ArrayList<String>());
				if(!authorMap.get(p.author).containsKey(key)) authorMap.get(p.author).put(key, new ArrayList<String>());

				wordMap.get(key).add(words[j+2]);
				oneWordMap.get(words[j]).add(words[j+1]);
				authorMap.get(p.author).get(key).add(words[j+2]);
			}
			if(words.length>1){
				if(!oneWordMap.containsKey(words[words.length-2])) oneWordMap.put(words[words.length-2], new ArrayList<String>());
				oneWordMap.get(words[words.length-2]).add(words[words.length-1]);
			}//finish oneWordMap for end of line here
			complete++;
		}

		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("wordMap.txt"));
			PrintWriter pw2 = new PrintWriter(new File("oneWordMap.txt"));
			for(Map.Entry<ArrayList<String>, ArrayList<String>> e : wordMap.entrySet()){
				pw.write("K: " + e.getKey() + "     V: " + e.getValue() +'\n');
			}
			pw.close();
			for(Map.Entry<String, ArrayList<String>> e : oneWordMap.entrySet()){
				pw2.write("K: " + e.getKey() + "     V: " + e.getValue() +'\n');
			}
			pw2.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}
	private static String removePunctuation(String line) {
		return line.replaceAll("\"", "")
				.replaceAll("”", "")
				.replaceAll("“", "")
				.replaceAll(";", "")
				.replaceAll("\\)", "")
				.replaceAll("\\(", "")
				.replaceAll(",", "");

	}

	private static boolean underLimit(ArrayList<String> arr, int limit){
		return (String.join(" ", arr).length()<limit);
	}

	private static ArrayList<String> generateMessage(int limit){
		//random starting point
		Random r = new Random();
		int index = r.nextInt(lineStarters.size());
		ArrayList<String> poem = new ArrayList<String>();
		String first = lineStarters.get(index).get(0);
		String second = lineStarters.get(index).get(1);
		poem.add(first);
		poem.add(second);
		while(underLimit(poem, limit)){
			int endIndex = poem.size();
			String[] lastWords = {poem.get(endIndex-2), poem.get(endIndex-1)};
			ArrayList<String> realLastWords = new ArrayList<String>(Arrays.asList(lastWords));
			if(wordMap.containsKey(realLastWords) && oneWordMap.containsKey(poem.get(endIndex-1))){
				ArrayList<String> possibleNextWords = wordMap.get(realLastWords);
				int nextIndex = 0;
				//if(possibleNextWords.size()==1) possibleNextWords = oneWordMap.get(poem.get(endIndex-1));
				nextIndex = r.nextInt(possibleNextWords.size());
				String nextWord = possibleNextWords.get(nextIndex);
				poem.add(nextWord);
			}
			else return poem;
		}
		//make sure not over limit
		if(!underLimit(poem, limit)) return poem;
		return null;
	}
	public String writePoem(){
		Random r = new Random();
		String poem = "";
		int finalLength = r.nextInt(200) + 50;
		int length = 0;
		int lineLength = 0;
		while(length<=finalLength){
			boolean oneLine = false;
			for(String word : generateMessage(r.nextInt(1500)+300)) {
				length++;
				lineLength++;
				poem += word + " ";
				if(r.nextInt(100)>75 && (lineLength>3 || !oneLine)) {
					poem += '\n';
					lineLength = 0;
					oneLine = true;
				}
			}
		}
		System.out.println(poem);
		return poem;
	}
}