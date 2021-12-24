// CopyInt.java -- read space separated integers from the standard input stream

import java.util.Scanner;

/*
 *  System.in  (java.io.InputStream) is the standard input
 *  System.out (java.io.PrintStream) is the standard output
 */

public final class CopyInt {

   public static void main (final String[] args) {

      final Scanner scanner = new Scanner (System.in, "US-ASCII");

      // Read the standard input stream
      while (scanner.hasNextLine()) {
         String test = scanner.nextLine();
         if(test.equals("")){
            break;
         }

         final int n = Integer.parseInt(test); // get the next int from input
         System.out.println(n);         // write the int to output

      }
       //scanner.close();
   }
}