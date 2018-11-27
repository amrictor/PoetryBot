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
	private static ArrayList<String[]> lineStarters = new ArrayList<String[]>();
	private static Map<String[], ArrayList<String>> wordMap;
	public static int complete = 0;
	public Bot(){
		generateMap();
	}

	public void write() {


	}
	
	public static void generateMap(){
		
//		Thread[] threads = new Thread[4];
//		for(int i = 0; i<threads.length; i++) {
//			threads[i] = new MapThread(wordMap, lib.poems.subList(i*(lib.poems.size()/4), (i+1)*(lib.poems.size()/4));
//		}
//		int numThreads = 9;
		wordMap = new ConcurrentHashMap<String[], ArrayList<String>>();
//		
//		ExecutorService exServer = Executors.newFixedThreadPool(numThreads);
//		 
//		for (; j < numThreads-1; j++) {
//			System.out.println(j*(lib.poems.size()/numThreads));
//			System.out.println(((j+1)*((lib.poems.size()-1)/numThreads)));
//			System.out.println(lib.poems.size());
//			exServer.execute(new Runnable() {
//				
//				@SuppressWarnings("unused")
//				@Override
//				public void run() {
//					for(int i = j*(lib.poems.size()/numThreads); i<((j+1)*((lib.poems.size()-1)/numThreads)); i++){

					for(int i = 0; i<lib.poems.size(); i++){
						System.out.printf("%.3f\n", 100*((double)complete/lib.poems.size()));
						String line = lib.poems.get(i);
						String[] words = line.replaceAll("\"", "").replaceAll("�", "").replaceAll("�", "").split("[\"*]*[ ]+\"*"); //[,:;\"]* +[\",;: ]*
						boolean endOfSentence = true;
						for(int j = 0; j < words.length-2; j++){
							String[] key = {words[j], words[j+1]};
							if(endOfSentence){
								lineStarters.add(key);
								endOfSentence = false;
							}
							//check if at end of sentence 
							if(words[j].isEmpty());
							else if(words[j].charAt(words[j].length()-1)=='?'||words[j].charAt(words[j].length()-1)=='.'||words[j].charAt(words[j].length()-1)=='!'){
								endOfSentence = true;
								words[j]=words[j].substring(0, words[j].length()-1);
							}
							//add words to map
							ArrayList<String> temp;
							if(containsKey(key)) {
								temp = getKey(key);
							}
							else temp = new ArrayList<String>();
							temp.add(words[j+2]); //problem
							wordMap.put(key, temp);
						}
						complete++;
					}
//				}
//			});
//		}
//		try {
//			exServer.awaitTermination(20, TimeUnit.SECONDS);
//		} catch (InterruptedException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		for(int i = 0; i<lib.poems.size(); i++){
//			System.out.println((double)i/lib.poems.size());
//			String line = lib.poems.get(i);
//			String[] words = line.replaceAll("\"", "").split("[\"*]*[ �]+\"*"); //[,:;\"]* +[\",;: ]*
//			boolean endOfSentence = true;
//			for(int j = 0; j<words.length-2; j++){
//				String[] key = {words[j], words[j+1]};
//				if(endOfSentence){
//					lineStarters.add(key);
//					endOfSentence = false;
//				}
//				//check if at end of sentence 
//				if(words[j].isEmpty());
//				else if(words[j].charAt(words[j].length()-1)=='?'||words[j].charAt(words[j].length()-1)=='.'||words[j].charAt(words[j].length()-1)=='!'){
//					endOfSentence = true;
//					words[j]=words[j].substring(0, words[j].length()-1);
//				}
//				//add words to map
//				ArrayList<String> temp;
//				if(containsKey(key)) {
//					temp = getKey(key);
//				}
//				else temp = new ArrayList<String>();
//				temp.add(words[j+2]); //problem
//				wordMap.put(key, temp);
//
//			}
//		}
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("map.txt"));
			for(Map.Entry<String[], ArrayList<String>> e : wordMap.entrySet()){
				System.out.println("K: " + Arrays.toString(e.getKey()) + "     V: " + e.getValue());
				pw.write("K: " + Arrays.toString(e.getKey()) + "     V: " + e.getValue());
				
			}
			pw.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	private static boolean underLimit(ArrayList<String> arr, int limit){
		return (String.join(" ", arr).length()<limit);
	}
	private static boolean checkKeys(String[] k0, String[]k1){
		if(k0.length!=k1.length) return false;
		boolean flag = true;
		for(int i =0; i<k0.length; i++){
			if(!k0[i].equals(k1[i])) flag = false;
		}
		return flag;
	}
	private static boolean containsKey(String[] key){
		for(Map.Entry<String[], ArrayList<String>> e : wordMap.entrySet()){
			if(checkKeys(e.getKey(), key)) return true;
		}
		return false;
	}
	
	//returns value from key
	private static ArrayList<String> getKey(String[] key){
		for(Map.Entry<String[], ArrayList<String>> e : wordMap.entrySet()){
			if(checkKeys(e.getKey(), key)) return e.getValue();
		}
		return null;
	}
	private static ArrayList<String> generateMessage(int limit){
		//random starting point
		Random r = new Random();
		int index = r.nextInt(lineStarters.size());
		ArrayList<String> poem = new ArrayList<String>();
		String first = lineStarters.get(index)[0];
		String second = lineStarters.get(index)[1];
		poem.add(first);
		poem.add(second);
		while(underLimit(poem, limit)){
			int endIndex = poem.size();
			String[] lastWords = {poem.get(endIndex-2), poem.get(endIndex-1)};
			if(containsKey(lastWords)){
				ArrayList<String> possibleNextWords = getKey(lastWords);
				int nextIndex = r.nextInt(possibleNextWords.size());
				String nextWord = possibleNextWords.get(nextIndex);
				poem.add(nextWord);
			}
			else return poem;
		}
		//make sure not over limit
		if(!underLimit(poem, limit)) return poem;
		return null;
	}
	public String nextMessage(int limit){
		String finalTweet = "";
		for(String s: generateMessage(limit)) finalTweet += s+" ";
		return finalTweet.substring(0, finalTweet.length()-1);
	}
}
