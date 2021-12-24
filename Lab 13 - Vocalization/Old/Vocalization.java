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

      // Scan requested number
      final long request = sc.nextLong(); 

      // Initial string of length
      final String string = "3";

      // Initial counter
      final long counter = 3;

      // Initial length
      final long length = 3;

      // Call sequence method
      sequence(counter, string, request, length);
      
   }

   // Method to form sequence (k iterations, s strings, r requested value, l length)
   public static void sequence (final long k, final String s, final long r, final long l) {

      // Find end string
      final String m = String.valueOf(k+1);

      // Test
      System.out.println("Length: " + l);

      // Split string into an array
      final String[] splitLine = s.split("");

      // Test 
      for (int i = 0; i < splitLine.length; i++) {
         System.out.print(splitLine[i] + " ");
      }

      // Base case 
      if (r <= l) {
      
         // Declare starting values for spitLine method
         final int counter = 0;           
         final long start = 1;
         final long iterations = splitLine.length;

         // Check for k, calling splitLine method
         if (check(splitLine, r, counter, start, iterations) == true) {
            System.out.println("k");
            return;
         }
         // Otherwise print i
         else {
            System.out.println("i");
            return;
         }
      }

      // Recursive call
      sequence(k+1, s+s+m, r, (k+1)+(l*2));

   }

   // Method to check for k (arr, r requested value, i iterations, s sum)
   public static boolean check (final String [] arr, final long r, int i, long s, final long n) { 

      // Base case
      if (i == n) {
         System.out.println("Base Case");
         return false;
      }

      // If our requested value is equal to a k value
      if (r == s) {
         System.out.println("K Case");
         return true;
      } 

      // Create a string from the string array
      final String tempString = arr[i];

      // Convert temporary string to long
      final long temp = Long.parseLong(tempString);

      System.out.println("i: " + i + " s: " + s);
      System.out.print(" temp: " + temp);

      return check(arr, r, i=i+1, s=s+temp, n);
   }
}
