/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Politics
 */

// import libraries
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public final class Politics {

   class SupporterImpl implements Comparable<SupporterImpl> {
      private String name;
      private int affiliation;

      // Constructor
      public SupporterImpl(final String k, final Integer i) {
         this.name = k;
         this.affiliation = i;
      }

      // String representation
      public String getStringRepresentation () {
         return name;
      }
      
      @Override
      public int compareTo (final SupporterImpl other) {
         if (this.affiliation == other.affiliation) {
            return 0;
         }
         else if (this.affiliation > other.affiliation) {
            return 1;
         }
         else {
            return -1;
         }
      }
   }

   // Produces array list of SupporterImpl objects which can be sorted 
   public ArrayList<SupporterImpl> determineAffiliation (final LinkedHashMap<String, String> 
         supporterMap, final ArrayList<String> candidateList) {
      
      // Final list for sorting and printing
      final ArrayList<SupporterImpl> supporterByKey = new ArrayList<SupporterImpl>();

      // Iterate through hash map and create a Supporter object for each key
      for (final Map.Entry<String, String> entry : supporterMap.entrySet()) {
         final String key = entry.getKey();
         final String value = entry.getValue();

         // Convert String value to Integer index of candidateList
         for (int i = 0; i < candidateList.size(); i++) {
            if (candidateList.get(i).equals(value)) {
               // Add SupporterImpl to array list
               supporterByKey.add(new SupporterImpl(key, i));
            }
         } 
      }
      return supporterByKey;
   }

   // Main function
   public static void main (final String[] args) {

      // Declare scanner
      final Scanner sc = new Scanner (System.in);
      
      // Scan through standard input 
      while (sc.hasNextLine()) {

         // Scan n (candidates) and m (supporters)
         final int n = sc.nextInt();
         final int m = sc.nextInt();

         // Check for 0,0 case
         if (n == 0 && m == 0) {
            break;
         }

         // Construct array list of declared candidates 
         final ArrayList<String> candidateList = new ArrayList<String>();
         for (int i = 0; i < n; i++) {
            candidateList.add(sc.next());
         }

         // Construct hash map of supporters and candidates
         final LinkedHashMap<String, String> supporterMap 
               = new LinkedHashMap<String, String>();
         for (int j = 0; j < m; j++) {
            final String supporter = sc.next();    // key = supporter  
            final String candidate = sc.next();    // value = candidates 
            
            // Add the Strings to the hashmap
            supporterMap.put(supporter, candidate);
         }

         // Add write-in candidates to candidate list
         for (final String i : supporterMap.values()) {
            if (!candidateList.contains(i)) {
               candidateList.add(i);
            }
         }

         // New politics instance 
         final Politics politics = new Politics();

         // Create final roster using non-static determineAffiliation method
         final ArrayList<SupporterImpl> roster 
               = politics.determineAffiliation(supporterMap, candidateList);

         // Sort final roster
         Collections.sort(roster);

         // Print string representation of final roster 
         for (int i = 0; i < roster.size(); i++) {
            System.out.println(roster.get(i).getStringRepresentation());
         }
      }
   }
}
