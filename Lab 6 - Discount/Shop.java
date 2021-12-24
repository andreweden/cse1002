/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: shopaholic
 */

// Import libraries
import java.util.Scanner;
import java.util.Arrays;

public final class Shop {
   public static void main (final String[] args) {

      // Declare scanner
      final Scanner sc = new Scanner (System.in, "US-ASCII");
      
      // Price limit
      final long pMax = 200000;
      final long pMin = 1;

      // Item limit
      final long nMax = 200000;
      final long nMin = 1;

      // Total saved
      long total = 0;

      // Number of items
      int n = sc.nextInt(); 

      // Check for item limit
      if (n > nMax || n < nMin) {
         System.out.println("Incorrect size inputed.");
         n = 0;
      }

      // Initialize array of size n
      final long[] myArray = new long[n];

      // Build array
      for (int i = 0; i < myArray.length; i++) {

         // Scanner
         myArray[i] = sc.nextLong();

         // Check for price limit
         if (myArray[i] > pMax || myArray[i] < pMin) {
            System.out.println("Incorrect price inputed.");
            break;
         }
      }

      // Sort array
      Arrays.sort(myArray);

      // Number of remaining items
      final int remain = n % 3;
      
      // Add discounted items to find total saved
      for (int i = remain; i < myArray.length; i += 3) {
         total += myArray[i];
      }
      System.out.println(total);
   }
}
