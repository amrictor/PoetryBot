package com.tripco.t23.planner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/*
  This class contains tests for the Trip class.
 */
@RunWith(JUnit4.class)
public class TestTrip {

  // Setup to be done before every test in TestPlan
  @Before
  public void initialize() {

  }

  @Test
  public void testTrue() {
    // assertTrue checks if a statement is true
    assertTrue( true);
  }



  //@Test
  //public void testShorter(){
  //  trip.options.optimization = "shorter";
  //  ArrayList<Integer> expectedDistances = new ArrayList<>();
  //  Collections.addAll(expectedDistances, 192, 5429, 2340, 2346, 1286, 5458, 1995, 132);
    // Call the equals() method of the first object on the second object.
  //  assertEquals(expectedDistances, trip.distances);
  //}

  //reads a file and returns String with file contents
  private String readFile(String filename) {
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


