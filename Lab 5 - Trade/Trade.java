/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Volitility 
 */

import java.util.Scanner;

public final class Trade {
   public static void main (final String[] args) {

      // max stock price
      final double buyMax = 500;

      // max number of stock
      final double nMax = 100000;

      // initial balance
      double bal = 100;

      // initial number of shares
      double n = 0;

      // last recorded buy price
      double buyLast = 0;

      try  {    
         Scanner sc = new Scanner(new File("input.txt"));    
         while (sc.hasNextLine()){
            // daily buy price
            final String tempBuy = sc.nextLine();
            final double buy = Double.parseDouble(tempBuy);

            // daily sell price
            final String tempSell = sc.nextLine();
            final double sell = Double.parseDouble(tempSell);

            // if we can buy
            if (bal >= buy && buy <= buyMax) {
               // number of shares to buy
               n = Math.round(bal / buy); 

               // max amount of shares is 100000
               if (n >= nMax) {
                  n = nMax;
               }

               // update balance
               bal = bal - (n * buy);

               // update last recorded buy price
               buyLast = buy;
            }

            // if we can sell
            if (sell > buyLast && n > 0) {
               bal += n*sell;
               // update number of shares to 0 
               n = 0;
            }
         }        

      } catch(Exception e) {  
            e.printStackTrace();  
      }  

      /*
      // while loop to execute every line
      while (sc.hasNextLine()) {

         // daily buy price
         final String tempBuy = sc.nextLine();
         // check for empty line
         if (tempBuy.equals("")) {
            break;
         }
         final double buy = Double.parseDouble(tempBuy); 

         // daily sell price
         final String tempSell = sc.nextLine();
         // check for empty line
         if (tempSell.equals("")) {
            break;
         }
         final double sell = Double.parseDouble(tempSell);

         // if we can buy
         if (bal >= buy && buy <= buyMax) {
            // number of shares to buy
            n = Math.round(bal / buy); 

            // max amount of shares is 100000
            if (n >= nMax) {
               n = nMax;
            }

            // update balance
            bal = bal - (n * buy);

            // update last recorded buy price
            buyLast = buy;
         }

         // if we can sell
         if (sell > buyLast && n > 0) {
            bal += n*sell;
            // update number of shares to 0 
            n = 0;
         }
      }
      */
      // print final balance
      System.out.printf("%.0f %n", bal); 
   }
}
