/*
 *  Data files:   http://introcs.cs.princeton.edu/16pagerank/tiny.txt
 *                http://introcs.cs.princeton.edu/16pagerank/medium.txt
 *
 *  This program reads a transition matrix from standard input and
 *  computes the probabilities that a random surfer lands on each page
 *  (page ranks) after T matrix-vector multiplies.
 *
 *  % curl --silent https://introcs.cs.princeton.edu/java/16pagerank/tiny.txt | \
 *       java Transition | java Markov 4
 *  % java Transition < tiny.txt | java Markov 40
 *   0.27303 0.26573 0.14618 0.24723 0.06783
 *
 */
import java.util.Scanner;
import java.io.BufferedInputStream;

public final class Markov {

   private static final String FORMAT =
         System.getProperty ("format", "%2d: %6.3f%n");

   private static final int DEFAULT_MOVES = 50;

   private static final Scanner STDIN =
         new Scanner (new BufferedInputStream (System.in));

   // Iteratively multiply a Markov matrix with a vector
   // a fixed number of times to compute page ranks. 

   public static void main (String[] args) { 
      // The number of iterations
      final int pow  = args.length>0 ? Integer.parseInt(args[0]) : DEFAULT_MOVES;
      final int x    = STDIN.nextInt(); // number of pages  - ignored
      final int size = STDIN.nextInt(); // number of pages

      // The input is a (right) stochastic matrix -- rows sum to 1.0
      // p[i][j] = prob. surfer moves from page i to page j
      final double[][] p = new double[size][size];
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            p[i][j] = STDIN.nextDouble();
         }
      }

      double[] rank = new double[size]; 
      
      // Initilize rank to the unit vector
      rank[0] = 1.0; 
      for (int t = 0; t < pow; t++) {

         // Create a new array (really shouldn't) and
         // compute effect of next move on page ranks. 
         final double[] newRank = new double[size]; 
         for (int j = 0; j < size; j++) {
            //  New rank of page j is dot product of
            //  old ranks and column j of p[][]. 
            for (int k = 0; k < size; k++) {
               newRank[j] += rank[k]*p[k][j]; 
            } 
         }
         // Update page ranks.
         rank = newRank;
      } 

      // print page ranks
      for (int i = 0; i < size; i++) {
         System.out.printf (FORMAT, i, rank[i]);  
      }
   } 
} 

/*
 * ------------For GNU Emacs ------------
 * Local Variables:
 * compile-command: "javac Markov.java"
 * End:
 */
