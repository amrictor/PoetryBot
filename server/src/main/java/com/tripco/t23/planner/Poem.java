package com.tripco.t23.planner;

public class Poem {
	String author;
	String classification;
	String[] keywords;
	String period;
	String reference;
	String region;
	String[] text;
	String poem = "";
	String title;
	String year;
	
	public void clean() {
		for(int i = 0; i<text.length; i++) {
			poem += text[i].replace("\u2019\ufeff", "\'")+" ";
		}
	}
	
	public void summary() {
		System.out.println("Author: " + author);
		System.out.println("classification: " + classification);
		System.out.println("period: " + period);
		System.out.println("reference: " + reference);
		System.out.println("region: " + region);
		System.out.println("year: " + year);
		System.out.println("title: " + title);
		System.out.println("text: ");
		for(String line : text) {
			System.out.println(line);
		}
		
	}

}
