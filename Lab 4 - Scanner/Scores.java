/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Proj 4, scanner
 */

import java.util.Scanner;

public final class Scores {
   public static void main (final String[] args) {
      final Scanner sc = new Scanner (System.in, "US-ASCII");
        
      // Number of lines
      final int n = sc.nextInt(); 

      // Names and grades input
      final String[][] myArray = new String[n][3];

      for (int r = 0; r < myArray.length; r++) {
         for (int c = 0; c < myArray[r].length; c++) {
            myArray[r][c] = sc.next();
         }
         sc.nextLine();
      }

      // Find total and average
      double total = 0;
      for (int r = 0; r < myArray.length; r++) {
         final String tempScore = myArray[r][2];
         final int score = Integer.parseInt(tempScore);
         total += score;
      }
      final double avg = total/n;

      // Find standard dev
      double tempStnd = 0;
      for (int r = 0; r < myArray.length; r++) {
         final String tempScore = myArray[r][2];
         final int score = Integer.parseInt(tempScore);
         tempStnd += Math.pow(score - avg, 2);
      }
      final double stnd = Math.sqrt(tempStnd/n);
        
      // Table header
      final String s1 = "Name";
      final String s2 = "Score";
      final String s3 = "Z";
      final String s4 = "Grade";
      System.out.printf("%-31s %15s %5s %5s %n", s1, s2, s3, s4);

      // Table middle 
      for (int r = 0; r < myArray.length; r++) {
         final String first = myArray[r][0];
         final String last = myArray[r][1];
         final String tempScore = myArray[r][2];
         final int score = Integer.parseInt(tempScore);

         final double z = (score-avg)/stnd;
            
         // Z score to letter grade
         String grade = "Test";
         if (z >= 1.0) {
            grade = "A";
         } else if (z < 1.0 && z >= 0.0) {
            grade =  "B";
         } else if (z < 0.0 && z >= -1.0) {
            grade =  "C";
         } else if (z < -1.0 && z >= -2.0) {
            grade =  "D";
         } else {
            grade =  "F";
         } 

         System.out.printf("%15s %-15s %15s %5.2f %5s %n", first, last, score, z, grade);   
      }

      // Table footer
      final String s5 = "Average";
      System.out.printf("%-15s %15.2f %n", s5, avg);
   }
}
