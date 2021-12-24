/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: bijection
 */
 
// Import libraries
import java.util.Scanner;

public final class Bijection {
   public static void main (final String[] args) {

      // Declare scanner
      final Scanner sc = new Scanner (System.in, "US-ASCII");

      // Declare key of 64 characters
      final String[] keyArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                                 "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                                 "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                                 "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e",
                                 "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
                                 "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
                                 "z", "<", "#", ">"};

      // Declare initial bases
      long baseA = 0;
      long baseB = 0;

      // Read while standard input is open
      while (sc.hasNextLine()) { 

         // Declare and reset temporary values
         int tempInt = 0;
         int tempExp = 0;
         long decimal = 0;
         String temp1 = "0";
         String temp2 = "0";
         long remain = 0;
         String remainString = "";
         String endString = "";

         // Find inputed values for a, b, and n 
         final String a = sc.next();
         final String n = sc.next();
         final String b = sc.next();

         // Break loop if there are no more values to read
         if (a.equals("")) {
            break;
         }

         // Find base A
         for (int i = 0; i < keyArray.length; i++) {
            if (a.equals(keyArray[i])) {
               baseA = i + 1;
            }
         }

         // Find base B
         for (int i = 0; i < keyArray.length; i++) {
            if (b.equals(keyArray[i])) {
               baseB = i + 1;
            }
         }

         // Break string n into a string array
         final String[] brokenArray = n.split("");

         // Convert base A to decimal 
         for (int i = 0; i < brokenArray.length; i++) {
            temp1 = brokenArray[i]; 

            // Find the corresponding key value
            for (int j = 0; j < keyArray.length; j++) {
               temp2 = keyArray[j];
               if (temp1.equals(temp2)) {
                  tempInt = j + 1;
               }
            }

            // Find exponenent in bijection
            tempExp = brokenArray.length - 1 - i; 
               
            // Sum each bijection value
            decimal += tempInt * Math.pow(baseA, tempExp);
         }

         // Convert decimal to base B
         long result = decimal;
         while (result > 0) {
            remain = (result % baseB);
            if (remain != 0) {
               remainString = Long.toString(remain);
               endString = remainString + endString;
            }
            result = result / baseB;
         } 
         System.out.println(endString);
      }
   }
}
