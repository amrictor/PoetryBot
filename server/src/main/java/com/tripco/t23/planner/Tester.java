package com.tripco.t23.planner;
import java.util.Arrays;
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

		String poem = b.writePoem();
		String[] lines = poem.split("\n");
		System.out.println(Arrays.toString(lines));
	}

}
