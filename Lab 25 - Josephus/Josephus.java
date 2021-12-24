/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 1, Fall 2021
 * Project: Josephus Problem
 */

// import libraries
import java.util.Scanner;
import java.util.List;
import java.util.ListIterator;
import java.lang.reflect.Constructor;

public final class Josephus {

   // Method to find last 
   public static String findLast (final int n, final List<String> list) {

      // If n = 1, the last name will be remaining 
      if (n == 1) {
         return list.get(list.size() - 1);
      }

      // Loop through lists of decreasing size, removing every nth name
      int counter = 0;
      while (list.size() > 1) {
         
         // Declare list iterator object from list
         final ListIterator litr = list.listIterator();
         
         // Loop through iterator
         while (litr.hasNext()) {
            final Object test = litr.next();
            counter++;
            // Only apply to every nth number
            if (counter % n == 0) {
               litr.remove();
               counter = 0;
            }
         }
      }
      // Return first and only element of list
      return list.get(0);
   }

   // Main method
   @SuppressWarnings("unchecked")
   public static void main (final String[] args) {
      
      // Step size from command line
      final int k = Integer.parseInt(args[0]);

      try {
         // Construct list with reflection
         final Class c = Class.forName(args[1]);
         final Constructor<List<String>> constructor = c.getDeclaredConstructor();
         final List<String> list = constructor.newInstance();

         // Scan in list elements
         final Scanner sc = new Scanner (System.in);
         while (sc.hasNextLine()) {
            list.add(sc.next());
         }

         // Method runs through 10 trials, keeping track of time elapsed
         final int trials = 10;
         String name = null;
         final double startTime = System.nanoTime();
         for (int i = 0; i < trials; i++) {
            name = findLast(k, list);
         } 
         final double endTime = System.nanoTime();

         // Calculate average time in seconds
         final double conversion = 1000000000;
         final double time = ((endTime - startTime) / trials) / conversion;

         // Print out soldier name and run time
         System.out.printf("Last soldier: %s. Average running time: PT%.10fS %n", name, time); 
      }

      catch (Throwable e) {
         System.err.println(e);
      }
   }
}
