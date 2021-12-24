import java.util.Scanner;

public final class Sum {

   public static void main (final String[] args) {

      final Scanner scanner = new Scanner (System.in, "US-ASCII");

      int sum = 0;

      while (scanner.hasNextInt()){

         int test = scanner.nextInt();

         sum += test;
      }

      System.out.println("Sum: " + sum);    
   }
}
