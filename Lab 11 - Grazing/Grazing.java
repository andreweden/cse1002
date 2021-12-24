/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Grazing
 */

// Import libraries
import java.util.Scanner;

public final class Grazing {

   // Declare global variable total so that it can always be accessed
   public static int total = 0;

   public static void main (final String[] args) {

      // Declare scanner
      final Scanner scanner = new Scanner (System.in, "US-ASCII");

      // Scan number of lines k
      final int k = scanner.nextInt();   

      // Assert that k is between 0 and 22, and is even   
      final int kMin = 0;
      final int kMax = 22; 
      assert kMin <= k && k <= kMax; 
      assert k % 2 == 0;

      // Store entire grass grid, where 0 is grass
      final int[][] grid = new int[5][5];
      for (int r = 0; r < grid.length; r++) {
         for (int c = 0; c < grid.length; c++) {
            grid[r][c] = 0;
         }
      }

      // Store grassless squares in an array
      final int[][] grassless = new int[k][2];
      for (int r = 0; r < grassless.length; r++) {
         for (int c = 0; c < grassless[r].length; c++) {
            grassless[r][c] = scanner.nextInt();
         }
      }

      // Add grassless spots to the grid, where 1 is grass
      for (int r = 0; r < grassless.length; r++) {
         final int x = grassless[r][0];
         final int y = grassless[r][1];
         grid[x-1][y-1] = 1;
      }

      // Construct an array copy of grid array 
      final int [][] copy = new int[5][5];
      for (int r = 0; r < copy.length; r++) {
         for (int c = 0; c < copy[r].length; c++) {
            copy[r][c] = 0;
         }
      }

      // Call the pathing method starting at origin
      path(0, 0, grid, copy);

      // Print total number of paths 
      System.out.println(total);
   }

   // Pathing method recursively constructs paths
   public static void path (final int y, final int x, final int[][] arr, final int[][] visit) {
   
      // Mark current block as visited 
      visit[y][x] = 1;   
      printArray(visit); 
      System.out.println();
      
      // If we are at the end block 
      if (y == 4 && x == 4) {
         /*
         // If a point has not been visited, ignore path
         for (int r = 0; r < visit.length; r++) {
            for (int c = 0; c < visit[r].length; c++) {
               if (visit[r][c] == 0) {
                  return;
               }
            }
         }*/

         // Reset visit array
         for (int r = 0; r < visit.length; r++) {
            for (int c = 0; c < visit[r].length; c++) {
               visit[r][c] = 0;
               System.out.println("Reset array: ");
               printArray(visit);
            }
         }

         // If all points were visited, increase path count
         total++;
         return;
      }

      // Move North if block is within bounds, univisited, and grassy
      if (y != 0) {
         if (visit[y - 1][x] == 0 && arr[y - 1][x] == 0) {
            //path(y - 1, x, arr, visit);
         }
      }

      // Move South if block is within bounds, univisited, and grassy
      if (y != 4) {
         if (visit[y + 1][x] == 0 && arr[y + 1][x] == 0) {
            path(y + 1, x, arr, visit);
         }
      }

      // Move East if block is within bounds, univisited, and grassy
      if (x != 4) {
         if (visit[y][x + 1] == 0 && arr[y][x + 1] == 0) {
            path(y, x + 1, arr, visit);
         } 
      }

      // Move West if block is within bounds, univisited, and grassy
      if (x != 0){
         if (visit[y][x - 1] == 0 && arr[y][x - 1] == 0) {
            //path(y, x - 1, arr, visit);
         }
      }

      // Unmark current block as visited 
      visit[y][x] = 0;
   }

   // Print test array 
   public static void printArray(int [][] arr) {
      for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
               System.out.print(arr[r][c] + " ");
         }
         System.out.println();
      }
   }
}
