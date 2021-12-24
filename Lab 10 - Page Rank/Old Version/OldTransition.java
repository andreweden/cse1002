/*
 *  % java -Dformat=" %a" Transition [<damping fraction>] < tiny.txt
 *
 *  This program is a filter that reads a list of edges from the standard input
 *  and produces the corresponding transition matrix on the standard output.
 *  First, it processes the input to count the outlinks from each page.
 *  Then it applies the 90-10 rule to compute the transition matrix.
 *
 *  % more tiny.txt
 *  5 
 *  0 1 
 *  1 2  1 2
 *  1 3  1 3  1 4
 *  2 3
 *  3 0 
 *  4 0  4 2
 *
 *  % java Transition < tiny.txt
 *  5 5
 *   0.02 0.92 0.02 0.02 0.02
 *   0.02 0.02 0.38 0.38 0.20
 *   0.02 0.02 0.02 0.92 0.02
 *   0.92 0.02 0.02 0.02 0.02
 *   0.47 0.02 0.47 0.02 0.02
 *
 */

import java.util.Scanner;

public final class OldTransition {

   // Greater accuracy may be required for the next step.
   // -Dformat=" %a" hexadecimal floating-point avoid loss during decimal conversion
   private static final String FORMAT = System.getProperty("format"," %7.5f");

   private static final double DAMPING_DEFAULT = 0.90;
   private static final Scanner STDIN = new Scanner (System.in, "US-ASCII");

   public static void main (final String[] args) {
      // number of pages
      final int size = STDIN.nextInt();
      // counts[i][j] = # links from page i to page j
      final int[][] counts = new int[size][size];
      // outDegree[j] = # links from page i to anywhere
      final int[] outDegree = new int[size];
      // Probability of taking a random link on the page
      final double damping = args.length>0?Double.parseDouble(args[0]):DAMPING_DEFAULT;
      assert 0.0 <= damping && damping <= 1.0; 
      final double d = (1.0-damping)/size;

      // Accumulate link counts.
      while (STDIN.hasNextInt()) {
         final int i = STDIN.nextInt();  assert 0<=i && i<size;
         final int j = STDIN.nextInt();  assert 0<=j && j<size;
         outDegree[i]++;
         counts[i][j]++;
      }
      System.out.printf("%d %d%n", size, size);

      // Print probability distribution for row i. 
      for (int i = 0; i < size; i++) {
         assert outDegree[i] >= 0;
         // Print probability for column j.
         for (int j = 0; j < size; j++) {
            final double p = .90*counts[i][j]/outDegree[i] + .10/size;
            System.out.printf(FORMAT, p);
         }
         System.out.println();
      }
   }
}