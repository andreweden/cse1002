/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Search a List
 */

// Import libraries
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.text.Collator;

public final class Search {

   public static void main (final String[] args) {

      // Declare scanner
      final Scanner sc = new Scanner (System.in);  

      // Declare array list
      final ArrayList<String> arrList = new ArrayList<String>();

      // While file has additional inputs
      while (sc.hasNextLine()) {

         // Scan temporary token
         final String temp = sc.next();

         // If temporary token is question mark 
         if (temp.equals("?")) {

            // Scan first and last requested string
            final String first = sc.next();
            final String last = sc.next();

            // Check if arrList contains queried names
            if (arrList.contains(first) && arrList.contains(last)) {

               // Find start location in array list
               int start = -1;
               for (int i = 0; i < arrList.size(); i++) {
                  if (arrList.get(i).equals(first)) {
                     start = i;
                     break;
                  }
               }

               // Find end location in array list
               int end = -1;
               for (int i = 0; i < arrList.size(); i++) {
                  if (arrList.get(i).equals(last)) {
                     end = i;
                     break;
                  }
               }

               // Check for nonesuch case
               if (start > end) {
                  System.out.println("Nonesuch");
               }

               // Otherwise continue to sort
               else {
                  // Split arrList1 into a smaller arrList2 from start to end 
                  final ArrayList<String> list 
                        = new ArrayList<String>(arrList.subList(start, end+1));

                  // Update Collator
                  final Collator myCollator = Collator.getInstance();

                  // Sort lexigraphically 
                  Collections.sort(list, myCollator);

                  // Print loop
                  for (int j = 0; j < list.size(); j++) {
                     System.out.printf("%3d: %s %n", j+1, list.get(j));
                  }
               }
            }

            // Otherwise print absent 
            else {
               System.out.println("Absent");
            }
         }

         // Otherwise add the temporary variable to the array list
         else {
            arrList.add(temp);
         }
      }
   }
}
