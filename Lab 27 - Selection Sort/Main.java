/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Selection Sort
 */

// Import packages
import java.util.Random;
import java.util.Collections;
import java.util.List;

public final class Main {

   // Pass the seed as a Java property
   private static final Random RNG = new Random (Long.getLong ("seed", System.nanoTime()));

   // Sort method
   public static void sort (final List<Integer> data) {
      for (int i = 0; i < data.size(); i++) {
         /* find the min element in the unsorted data[i, i+1, .., n-1] */
               
         /* assume initially that min is the first element in the range */
         int min = i;
               
         for (int j = i + 1; j < data.size(); j++) {
            /* if the 'j'th element is less, then it is the new minimum */
            if (data.get(min) > data.get(j)) {
               /* found new minimum; remember its index */
               min = j;
            }
         }
           
         /* swapping */
         final int temp = data.get(min);
         data.set(min, data.get(i));
         data.set(i, temp);
      }
   }

   // Main method
   @SuppressWarnings("deprecation")
   public static void main (final String[] args) throws ClassNotFoundException, 
         InstantiationException, IllegalAccessException {

      // Reflection
      final Class<?> clazz = Class.forName(args[1]);
      @SuppressWarnings("unchecked")
      final java.util.List<Integer> list = (java.util.List<Integer>) clazz.newInstance();

      // Initialize
      final Random rnd = new Random (Long.getLong ("seed", System.nanoTime()));
      long startTime, endTime, total = 0;

      // Create list {1, 2, 3, ..., N}
      final int n = Integer.parseInt(args[0]);
      for (int j = 1; j <= n; j++) {
         list.add(j);
      }

      for (int i = 0; i < 10; i++) {
         // Shuffle
         Collections.shuffle(list, rnd);
         startTime = System.nanoTime();

         // Sort
         sort(list);

         endTime = System.nanoTime();
         total += endTime - startTime;
      }
      final double averageTime = total / 10;
      final double conversion = 1000000000;

      // Print time
      System.out.printf("PT%.10fS %n", averageTime / conversion);
   }
}
