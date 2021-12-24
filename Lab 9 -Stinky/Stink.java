/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Stink
 */

// Import libraries
import java.util.Random;

public final class Stink {

   // Pass the seed as a Java property
   private static final Random RNG = new Random (Long.getLong ("seed", System.nanoTime()));
   
   public static void main (final String[] args) {

      // Parse number of trials from command line   
      final long t = Long.parseLong(args[0]);

      // Declare min and max trials
      final long min = 1;
      final long max = 1000000;

      // Check for impossible sample size
      if (t < min || t > max) {
         throw new IllegalArgumentException("Too many trials");
      }

      // Declare variables
      long freeze = 0;
      long move = 0;

      // Run for each trial
      for (int i = 0; i < t; i++) {

         // Randomly select hycanith pot (0 = pot 1, 1 = pot 2, 2, = pot 3)
         final int hyacinth = RNG.nextInt(3);

         // Professor selects pot 2 or 3 to leave closed 
         final int closed;
         if (hyacinth == 1) {
            closed = 1;
         }
         else if (hyacinth == 2) {
            closed = 2;
         }
         // If hycanith is in neither, randomly chose a pot to leave closed
         else {
            if (RNG.nextInt(2) == 0) {
               closed = 1;
            }
            else {
               closed = 2;
            }
         }

         // If hyacinth is in the first pot
         if (hyacinth == 0) {
            freeze++;
         }
         // If hyacinth is in the remaining closed pot
         else {
            move++;
         }
      }

      // Print results 
      System.out.printf("%d %d %n", freeze, move);
   }
}
