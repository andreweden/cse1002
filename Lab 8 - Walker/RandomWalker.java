/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: walk
 */

// Import libraries
import java.util.Random;

public final class RandomWalker {

   // Pass the seed as a Java property
   private static final Random RNG = new Random (Long.getLong ("seed", System.nanoTime()));
   
   public static void main (final String[] args) {

      // Parse n (steps) and m (trials) from command line   
      final long n = Long.parseLong(args[0]);
      final long t = Long.parseLong(args[1]);

      // Declare variables
      int direction = 0;
      long x = 0;
      long y = 0;
      final long min = 1;
      final long max = 1000000000;
      long origin = 0;
      double euclidian = 0;

      // Check for impossible variables
      if (n < min || n > max) {
         throw new IllegalArgumentException("n is too large");
      }
      if (t < min || t > max) {
         throw new IllegalArgumentException("m is too large");
      }

      // Run for each trial
      for (int i = 0; i < t; i++) {

         // Reset variables between trials
         x = 0;
         y = 0;
         direction = 0;

         // Run for each step
         for (int j = 0; j < n; j++) {
            // Generate random direction (East=0, North=1, West=2, South=3)
            direction = RNG.nextInt(4);

            // Check for East 
            if (direction == 0) {
               x++;
            }
            // Check for North 
            else if (direction == 1) {
               y++;
            }
            // Check for West 
            else if (direction == 2) {
               x--;
            }
            // Check for South 
            else {
               y--;
            }
         }
         // Check for origin
         if (x == 0 && y == 0) {
            origin++;
         }

         // Find the euclidian distance for this trial 
         euclidian += Math.hypot(x, y);
      }

      // Print origin and average euclidian
      System.out.printf("%d %.2f %n", origin, euclidian/t);
   }
}
