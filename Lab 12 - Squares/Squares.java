/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Squares
 */

public final class Squares {
   public static void main (final String[] args) {

      // Parse r (ratio), lev (level), and n (pattern) from command line   
      final double r = Double.parseDouble(args[0]);
      final double level = Double.parseDouble(args[1]);
      final double n = Double.parseDouble(args[2]);

      // Assert that n is between 1 and 4 
      final double nMin = 1;
      final double nMax = 4; 
      assert nMin <= n && n <= nMax; 

      // Declare starting position and size
      final double i = 0.5, j = 0.5;   
      final double size = 0.5;   

      // Call recursive method
      pattern(r, level, i, j, size, n);
   }

   // Method to place a grey square with black border
   public static void placeSquare (final double x, final double y, final double s) {
      // Plot the square 
      StdDraw.setPenColor (StdDraw.LIGHT_GRAY);
      StdDraw.filledSquare (x, y, s/2);
      StdDraw.setPenColor (StdDraw.BLACK);
      StdDraw.square (x, y, s/2);
   }

   // Recursive method to construct patterns
   public static void pattern (final double r, final double lev, final double x, 
         final double y, final double s, final double p) {

      // Base Case
      if (lev == 0) {
         return;
      }

      // Pattern 1 (All squares under)
      if (p == 1) {
         pattern(r, lev-1, x-s/2, y-s/2, s/r, p);    // Bottom left
         pattern(r, lev-1, x-s/2, y+s/2, s/r, p);    // Top left 
         pattern(r, lev-1, x+s/2, y-s/2, s/r, p);    // Bottom right
         pattern(r, lev-1, x+s/2, y+s/2, s/r, p);    // Top right 
         placeSquare(x, y, s); // Call placeSquare method
      } 

      // Pattern 2 (Bottom right square over)
      if (p == 2) {
         pattern(r, lev-1, x-s/2, y+s/2, s/r, p);    // Top left 
         pattern(r, lev-1, x+s/2, y+s/2, s/r, p);    // Top right 
         pattern(r, lev-1, x-s/2, y-s/2, s/r, p);    // Bottom left
         placeSquare(x, y, s); // Call placeSquare method
         pattern(r, lev-1, x+s/2, y-s/2, s/r, p);    // Bottom right

      }

      // Pattern 3 (All squares over)
      if (p == 3) {
         placeSquare(x, y, s); // Call placeSquare method
         pattern(r, lev-1, x-s/2, y-s/2, s/r, p);    // Bottom left
         pattern(r, lev-1, x-s/2, y+s/2, s/r, p);    // Top left 
         pattern(r, lev-1, x+s/2, y-s/2, s/r, p);    // Bottom right
         pattern(r, lev-1, x+s/2, y+s/2, s/r, p);    // Top right  
      }

      // Pattern 4 (Bottom left and right square over)
      if (p == 4) {
         pattern(r, lev-1, x-s/2, y+s/2, s/r, p);    // Top left 
         pattern(r, lev-1, x+s/2, y+s/2, s/r, p);    // Top right 
         placeSquare(x, y, s); // Call placeSquare method
         pattern(r, lev-1, x-s/2, y-s/2, s/r, p);    // Bottom left
         pattern(r, lev-1, x+s/2, y-s/2, s/r, p);    // Bottom right
      }
   }
}
