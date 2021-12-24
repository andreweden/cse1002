/*
 * Recursively calculate how many paths from (0,0) to (i,j) in a grid
 * Source: https://stackoverflow.com/questions/14215198/count-the-number-of-lattice-paths-from-0-0-to-x-y-in-recursion
 */

// Import libraries
import java.util.Scanner;

public final class Grid {
   public static void main (final String[] args) {
      // Declare scanner
      final Scanner scanner = new Scanner (System.in, "US-ASCII");

      System.out.print("Enter i: ");   
      final int i= scanner.nextInt();
      System.out.print("Enter j: ");
      final int j= scanner.nextInt();
      final int n = path(i,j);
      System.out.println("n: " + n);   

   }
   
   public static int path(int x, int y) {
      if (x == 0 || y == 0) {
         return 1;
      }
      return path(x - 1, y) + path(x, y - 1);
   }
   
}