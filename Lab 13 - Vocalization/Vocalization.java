/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Vocalization
 */

// Import libraries
import java.util.Scanner;

public final class Vocalization {

   public static void main (final String[] args) {

      // Declare scanner
      final Scanner sc = new Scanner (System.in, "US-ASCII");

      // Scan for requested value
      final long request = sc.nextLong();

      // Declare result variable
      final char result = sequence(request);

      // Print result variable 
      System.out.println(result);

   }
   // Recursive method to form sequence (r is requested value)
   public static char sequence (final long r) {

      // Base Case
      if (r == 1) {
         return 'k';
      }

      // Calculate iterations
      long counter = 0;
      for (int i = 0; length(i) < r; i++) {
         counter++;
      }

      // Declare length of r
      final long lengthR = 3 + counter;

      // Calculate shift in position
      final long shift = ((length(counter) - lengthR) / 2);

      // Update r value
      final long updateR = r - shift;

      // Check if new r is contained in the sequence
      if (updateR < lengthR) {
         // Check for k
         if (updateR == 1) {
            return 'k';
         }
         // Otherwise print i
         else {
            return 'i';
         }
      }

      // Else recur sequence
      else {
         return sequence(updateR - lengthR);
      }
   }  

   // Recursive method to calculate length
   public static long length (final long j) {
      
      // If j is 0 use the length of V(0)
      if (j == 0) {
         return 3;
      } 

      // Otherwise preform calculations
      else {
         return 3 + j + (length(j - 1) * 2);
      }
   } 
}
