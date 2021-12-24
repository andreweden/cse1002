/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Page Rank
 * Comment: This is a modified version of Transition.java by Princeton. 
 */

/******************************************************************************
 *  Compilation:  javac Transition.java
 *  Execution:    java Transition < input.txt
 *  Data files:   https://introcs.cs.princeton.edu/java/16pagerank/tinyG.txt
 *                https://introcs.cs.princeton.edu/java/16pagerank/mediumG.txt
 *
 *  This program is a filter that reads links from standard input and
 *  produces the corresponding transition matrix on standard output.
 *  First, it processes the input to count the outlinks from each page.
 *  Then it applies the 90-10 rule to compute the transition matrix.
 *  It assumes that there are no pages that have no outlinks in the
 *  input (see Exercise 1.6.3).
 *
 *  % more tinyG.txt
 *  5
 *  0 1
 *  1 2  1 2
 *  1 3  1 3  1 4
 *  2 3
 *  3 0
 *  4 0  4 2
 *
 *  % java Transition < tinyG.txt
 *  5 5
 *   0.02 0.92 0.02 0.02 0.02
 *   0.02 0.02 0.38 0.38 0.20
 *   0.02 0.02 0.02 0.92 0.02
 *   0.92 0.02 0.02 0.02 0.02
 *   0.47 0.02 0.47 0.02 0.02
 *
 ******************************************************************************/

// Import libraries
import java.util.Scanner;

public final class Transition {

   // JVM argument leap
   private static final String LEAP = System.getProperty("leap", "0.1");

   // JVM argument format 
   private static final String FORMAT = System.getProperty("format", " %.2f");

   public static void main (final String[] args) {

      // Declare scanner
      final Scanner scanner = new Scanner (System.in, "US-ASCII");

      // Declaring arrays
      final int n = scanner.nextInt();       // number of pages
      final int[][] counts = new int[n][n];  // counts[i][j] = # links from page i to page j
      final int[] outDegree = new int[n];    // outDegree[i] = # links from page i to anywhere

      // Convert leap string to double
      final double leapDouble = Double.parseDouble(LEAP);

      // Assert to check that leap is appropriate
      assert 0.0 <= leapDouble && leapDouble <= 1.0; 
      
      // Accumulate link counts
      while (scanner.hasNextLine()) {

         // Scan each line as a string and strip leading whitespace
         final String line = scanner.nextLine().stripLeading();

         // Split each string into a string array, removing all remaining whitespace
         final String[] splitLine = line.split("\\s+");
         
         // For loop iterates between each pair of integers to accumulate links
         for (int k = 0; k < splitLine.length; k = k + 2) {

            // If the line has no elements, i.e the first line after scanning n
            if (splitLine[k].equals("")) {
               break;
            }

            // If the line has only 1 element, i.e. no links from it
            if (splitLine.length == 1) {
               final int i = Integer.parseInt(splitLine[k]);
               outDegree[i] = 0;
            }

            // If the line has multiple elements, i.e. with links
            else {
               final int i = Integer.parseInt(splitLine[k]);
               final int j = Integer.parseInt(splitLine[k+1]);
               outDegree[i]++; 
               counts[i][j]++;
            }
         }
      }
      System.out.println(n + " " + n); 

      
      // Print probability distribution for row i. 
      for (int i = 0; i < n; i++) {

         // Print probability for column j. 
         for (int j = 0; j < n; j++) {

            double p = 0;

            // Probability for a page with no links
            if (outDegree[i] == 0) {
               p = leapDouble / n;
            }

            // Probability for a page with links
            else {
               p = (1 - leapDouble)*counts[i][j]/outDegree[i] + leapDouble/n; 
            }

            // Assert to check that probability is appropriate
            assert 0.0 <= p && p <= 1.0;

            // Print probability with formating 
            System.out.printf(FORMAT, p);
         } 
         System.out.println(); 
      } 
   } 
} 
