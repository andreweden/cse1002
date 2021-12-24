/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Guitar
 */

// Import libraries
import java.util.Scanner;

public final class GuitarHero {

   // Main method 
   public static void main (final String[] args) {

      // Declare constants
      final int rate = 44100;

      // Declare scanner 
      final Scanner sc = new Scanner (System.in); 

      // While file has additional inputs
      while (sc.hasNextLine()) {

         // Scan index i and duration d
         final double i = sc.nextDouble();
         final double d = sc.nextDouble();

         // Declare new GuitarString with frequency f
         final double f = 440 * Math.pow(2, i/12);
         
         // Simulate new GuitarString
         final GuitarString sim = new GuitarString(f);

         // Call pluck for each new GuitarString
         sim.pluck();

         // Iterate through each note time interval
         for (int j = 0; j < rate*d; j++) {

            // Call tic for each time interval
            sim.tic();

            // Play sample note
            MyAudio.play(sim.sample());
         }
         
      }
   } 
}
