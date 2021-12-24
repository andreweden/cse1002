/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Mortgage
 */

// Import libraries
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Mortgage {

   public static void main (final String[] args) {

      // Declare scanner
      final Scanner sc = new Scanner (System.in, "US-ASCII");   

      // Scan for loan amount
      BigDecimal balance = new BigDecimal(sc.nextLine());

      // Scan for interest rate
      final BigDecimal rate = new BigDecimal(sc.nextLine());

      // While file has additional inputs 
      while (sc.hasNextLine()) {

         // Scan for payment 
         final String payment = sc.nextLine();

         // If balance string is present, print current balance
         if (payment.toLowerCase().equals("balance")) {

            // For a zero balance, print 0.00
            if (balance.compareTo(BigDecimal.ZERO) == 0) {
               System.out.printf("%s %.2f %n", "balance:", balance); 
            }

            // For a positive balance, print with "left"
            else if (balance.compareTo(BigDecimal.ZERO) > 0) {
               System.out.printf("%s %.2f %s %n", "balance:", balance, "left"); 
            }

            // For a negative balance, print with "over" and take absolute value
            else {
               System.out.printf("%s %.2f %s %n", "balance:", balance.abs(), "over");
            }
         }

         // Otherwise update balance
         else {

            // Convert payment string into BigDecimal
            final BigDecimal bigPayment = new BigDecimal(payment);

            // Create a rounded balance 
            final BigDecimal balanceRound = balance.setScale(2, RoundingMode.HALF_UP);

            // Apply formula: loan = loan + loan * interest - payment
            balance = balance.add(balanceRound.multiply(rate)); 
            balance = balance.subtract(bigPayment);
         }
      }        
   }
}
