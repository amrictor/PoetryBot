package com.tripco.t23.planner;
import java.util.Random;

// PoetryBot Assignment
// Author: Abigail Rictor
// Date: Nov 26, 2018
// Class: CS164
// Email: amrictor@rams.colostate.edu

public class Tester {
	public static void main(String[] args) {
		Bot b = new Bot();
		
		System.out.println("*******************************************************************************************************************************************\n"
				+ "*******************************************************************************************************************************************\n"
				+ "*******************************************************************************************************************************************\n");
		Random r = new Random();
		String[] poem = new String[r.nextInt(30)+5];
		for(int i = 0; i<poem.length; i++) {
			poem[i] = b.nextMessage(r.nextInt(400)+50);
			System.out.println(poem[i]);
		}
			
		
	}

}
