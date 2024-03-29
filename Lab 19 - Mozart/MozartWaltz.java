/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: MozartWaltz
 */

// Import libraries
import java.util.Random;
import java.net.URL;
import java.net.MalformedURLException;

public final class MozartWaltz {

   // Pass the seed as a Java property
   private static final Random RNG = new Random (Long.getLong ("seed", System.nanoTime()));

   // Main method
   public static void main (final String[] args) {

      // Minuet Generator Table
      final int [][] minTable = {{96, 22, 141, 41, 105, 122, 11, 30, 70,
                                  121, 26, 9, 112, 49, 109, 14},
                                 {32, 6, 128, 63, 146, 46, 134, 81, 117,
                                  39, 126, 56, 174, 18, 116, 83},
                                 {69, 95, 158, 13, 153, 55, 110, 24, 66,
                                  139, 15, 132, 73, 58, 145, 7},
                                 {40, 17, 113, 85, 161, 2, 159, 100, 90,
                                  176, 7, 34, 67, 160, 52, 170},
                                 {148, 74, 163, 45, 80, 97, 36, 107, 25,
                                  143, 64, 125, 76, 136, 1, 93},
                                 {104, 157, 27, 167, 154, 68, 118, 91, 138,
                                  71, 150, 29, 101, 162, 23, 151},
                                 {152, 60, 171, 53, 99, 133, 21, 127, 16,
                                  155, 57, 175, 43, 168, 89, 172},
                                 {119, 84, 114, 50, 140, 86, 169, 94, 120,
                                  88, 48, 166, 51, 115, 72, 111},
                                 {98, 142, 42, 156, 75, 129, 62, 123, 65,
                                  77, 19, 82, 137, 38, 149, 8},
                                 {3, 87, 165, 61, 135, 47, 147, 33, 102, 4,
                                  31, 164, 144, 59, 173, 78},
                                 {54, 130, 10, 103, 28, 37, 106, 5, 35, 20,
                                  108, 92, 12, 124, 44, 131},
      };

      // Trio Generator Table
      final int [][] triTable = {{72, 6, 59, 25, 81, 41, 89, 13, 36, 5, 46,
                                  79, 30, 95, 19, 66},
                                 {56, 82, 42, 74, 14, 7, 26, 71, 76, 20, 64,
                                  84, 8, 35, 47, 8},
                                 {75, 39, 54, 1, 65, 43, 15, 80, 9, 34, 93,
                                  48, 69, 58, 90, 21},
                                 {40, 73, 16, 68, 29, 55, 2, 61, 22, 67, 49,
                                  77, 57, 87, 33, 10},
                                 {83, 3, 28, 53, 37, 17, 44, 70, 63, 85, 32,
                                  96, 12, 23, 50, 91},
                                 {18, 45, 62, 38, 4, 27, 52, 94, 11, 92, 24,
                                  86, 51, 60, 78, 31},
      };

      // Declare size of file 
      int size = 0;
      
      // Generate and print Minuet
      System.out.print("Minuets: "); 
      for (int i = 0; i < 16; i++) {
         final int dice1 = RNG.nextInt(11);
         final int value1 = minTable[dice1][i];
         System.out.print(value1 + " ");

         // Declare new URL 
         try {
            final String a = "http://andrew.cs.fit.edu/~cse1002-stansifer/mozart/wav/M" + value1 + ".wav";
            final URL url = new URL(a);

            // Declare array from wav file
            final double[] fileData = MyAudio.read(url);

            // Increase size
            size += fileData.length;

         } 
         catch (MalformedURLException e) {
            e.printStackTrace();
         }
      }
      System.out.println();

      // Generate and print Trio
      System.out.print("Trios:: ");
      for (int j = 0; j < 16; j++) {
         final int dice2 = RNG.nextInt(6);
         final int value2 = triTable[dice2][j];
         System.out.print(value2 + " ");

         // Declare new URL 
         try {
            final String a = "http://andrew.cs.fit.edu/~cse1002-stansifer/mozart/wav/T" + value2 + ".wav";
            final URL url = new URL(a);
            // Declare array from wav file
            final double[] fileData = MyAudio.read(url);

            // Increase size
            size += fileData.length;

         } 
         catch (MalformedURLException e) {
            e.printStackTrace();
         }
      }
      System.out.println();

      // Print size
      System.out.println("Music Size: " + size);
      
   } 
}
