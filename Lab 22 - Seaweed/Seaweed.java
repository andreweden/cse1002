/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Seaweed
 */

// import libraries
import java.util.Scanner;
import java.util.Arrays;

public final class Seaweed {

   // declare record to store t and d values
   public record Values (Integer t, Integer d) implements Comparable<Values> {
      
      @java.lang.Override

      // nested class compareTo
      public int compareTo (final Values other) {

         // Total amount consumed by the manatee 1 while manatee 2 is moved
         final int consumed1 = (this.t() * other.d());
         
         // Total amount consumed by the manatee 2 while manatee 1 is moved
         final int consumed2 = (other.t() * this.d());

         // Difference between the amount consumed
         final int difference = consumed1 - consumed2;

         // Return difference
         return difference;
      }
   }

   public static void main (final String[] args) {

      // declare scanner
      final Scanner sc = new Scanner (System.in); 

      // scan for number of manatees
      final int n = sc.nextInt();

      // declare list to store value records
      final Values[] list = new Values[n];

      // store value records 
      for (int i = 0; i < n; i++) {

         // scan next record 
         final Values next = new Values(sc.nextInt(), sc.nextInt());

         // add next record to list
         list[i] = next;
      }

      // Sorts the list based on consumed difference
      Arrays.sort(list);

      // Find total amount eaten
      int totalEaten = 0;
      for (int j = 0; j < n; j++) {
         totalEaten += 2 * list[j].d;
      }

      // Iterate through the trips, updating the amount eaten 
      int solution = 0;
      for (int k = 0; k < n; k++) {

         // Reduce the total amount eaten every trip 
         totalEaten -= 2 * list[k].d;

         // Increase the amount eaten by other manatees 
         solution += list[k].t * totalEaten; 
      }

      // Print solution 
      System.out.printf("%d%n", solution);

   }
}
