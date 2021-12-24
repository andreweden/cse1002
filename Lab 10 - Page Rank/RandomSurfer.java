/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Page Rank
 * Comment: This is a modified version of RandomSurfer.java by Princeton. 
 */

/******************************************************************************
 *  Compilation:  javac RandomSurfer.java
 *  Execution:    java RandomSurfer trials
 *  Data files:   https://introcs.cs.princeton.edu/java/16pagerank/tinyG.txt
 *                https://introcs.cs.princeton.edu/java/16pagerank/mediumG.txt
 *
 *  % java Transition < tinyG.txt | java RandomSurfer 1000000
 *   0.27297 0.26583 0.14598 0.24729 0.06793
 *
 ******************************************************************************/

// Import libraries
import java.util.Scanner;
import java.util.Random;

public class RandomSurfer {

   // Pass the seed as a Java property
   private static final Random RNG = new Random (Long.getLong ("seed", System.nanoTime()));

   // JVM argument trials
   private static final String TRIALS = System.getProperty("trials", "1000");

   public static void main (final String[] args) {

      // Declare scanner
      final Scanner scanner = new Scanner (System.in, "US-ASCII");

      final long trialsLong = Long.parseLong(TRIALS); // number of moves
      final int m = scanner.nextInt();          // number of pages  - ignore since m = n
      final int n = scanner.nextInt();          // number of pages
      if (m != n) {
         System.out.println("m does not equal n");
         return;
      }

      // Read transition matrix.
      final double[][] p = new double[n][n];    // p[i][j] = prob. that surfer moves i to j
      for (int i = 0; i < n; i++)
         for (int j = 0; j < n; j++) 
            p[i][j] = scanner.nextDouble(); 

      final int[] freq = new int[n];            // freq[i] = # times surfer hits page i
 
      // Start at page 0. 
      int page = 0;

      // Find the frequency for each trial
      for (int t = 0; t < trialsLong; t++) {

         // Make one random move. 
         final double r = RNG.nextDouble();

         // Assert to check that probability is between 0 and 1
         assert 0.0 <= r && r <= 1.0; 

         double sum = 0.0; 
         for (int j = 0; j < n; j++) {
            // Find interval containing r. 
            sum += p[page][j]; 
            if (r < sum) {
               page = j;
               break;
            } 
         } 
         freq[page]++; 
      } 
      // Print page ranks. 
      for (int i = 0; i < n; i++) {

         // Convert freq from int to double
         final double freqDouble = freq[i];
         System.out.printf("%2d: %6.3f%n", i, freqDouble / trialsLong);
      }
      System.out.println(); 
   }  
} 
