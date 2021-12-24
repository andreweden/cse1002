/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Treasure Hunt
 */

// Import libraries
import java.util.Scanner;

public final class Treasure {

   // Find max of array method
   public static int findMax (final int[] arr) {
      // Declare temporary max
      int max = 0;

      // Iterate through arr
      for (int i = 0; i < arr.length; i++) {
         if (arr[i] > max) {
            max = arr[i];
         }
      }

      // Return max
      return max;
   }

   // Main method
   public static void main (final String[] args) {

      // Declare scanner 
      final Scanner sc = new Scanner (System.in); 

      // Declare array list for previous values
      final int[] previous = new int[3];

      // While file has additional inputs
      while (sc.hasNextLine()) {

         // Scan the three inputs per line
         int first = sc.nextInt();
         int second = sc.nextInt();
         int third = sc.nextInt();

         // Declare array to store current and future values
         final int[] current = {first, second, third};

         // Find first max value
         if (previous[1] > previous[2]) {
            first += previous[1];
         }
         else {
            first += previous[2];
         }

         // Find second max value
         if (previous[0] > previous[2]) {
            second += previous[0];
         }
         else {
            second += previous[2];
         }

         // Find third max value
         if (previous[0] > previous[1]) {
            third += previous[0];
         }
         else {
            third += previous[1];
         }

         // Update previous array
         previous[0] = first;
         previous[1] = second;
         previous[2] = third;
      } 
      System.out.println(findMax(previous));
   } 
}
