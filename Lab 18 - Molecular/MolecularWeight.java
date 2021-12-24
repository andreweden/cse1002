/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Molecular Weight
 */

// Import libraries
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public final class MolecularWeight {

   // Declare record for elements
   public record Entry(String symbol, Double weight) {}
   
   // Main method
   public static void main (final String[] args) throws IOException {

      // Find fileName from command line argument 
      final String fileName = args[0];

      // Declare Buffer Reader
      final BufferedReader r = new BufferedReader (new FileReader(fileName));

      // Use buffer for first line
      final String firstLine = r.readLine();

      // Split line into string array
      final String[] splitFirstLine = firstLine.split("\\s*,\\s*");

      // Find columns for symbol and weight
      int symbolColumn = -1;
      int weightColumn = -1;
      for (int i = 0; i < splitFirstLine.length; i++) {
         if (splitFirstLine[i].equalsIgnoreCase("Symbol")) {
            symbolColumn = i;
         }
         if (splitFirstLine[i].equalsIgnoreCase("Weight")) {
            weightColumn = i;
         }
      }

      // Declare array list to store records
      final ArrayList<Entry> arrList = new ArrayList<Entry>();

      // Infinite loop to buffer all the inputs from element file 
      for (;;) {
         // Scan line using buffer
         final String line = r.readLine();

         // If statement to check for empty line
         if (line == null) break;

         // Split line into string array
         final String[] splitLine = line.split("\\s*,\\s*");

         // Find symbol and weight value to store
         final String symbolValue = splitLine[symbolColumn];
         final String weightValue = splitLine[weightColumn];
         final Double weightValueDouble = Double.parseDouble(weightValue);

         // Declare new Entry 
         final Entry nextElement = new Entry(symbolValue, weightValueDouble);

         // Add to array list
         arrList.add(nextElement);
      }
      r.close();

      // Declare scanner for equation reading
      final Scanner equationRead = new Scanner (System.in); 

      // While file has additional inputs
      while (equationRead.hasNextLine()) {

         // Scan each line, strip leading whitespace
         final String elementLine = equationRead.nextLine().stripLeading();

         // Split line into string array
         final String[] splitElementLine = elementLine.split("\\s+");

         // Declare default variables
         String result = "??";

         // Declare array list to store and adjust weights
         final ArrayList<Double> adjWeight = new ArrayList<Double>();

         // Declare boolean for illegal argument 
         boolean hasIllegal = false;

         // Iterate through string array
         for (int j = 0; j < splitElementLine.length; j++) {

            // Declare test string
            final String str = splitElementLine[j];
            
            // Iterate through test string to check for numbers
            boolean hasNum = false;
            for (int i = 0; i < str.length(); i++) {
               if (Character.isDigit(str.charAt(i))) {
                  hasNum = true;
               }
               else {
                  hasNum = false;
               }
            }

            // Check for illegal argument case 
            if (hasIllegal) {
               break;
            }

            // If the test string has a number, use as multiplication factor
            else if (hasNum) {

               // Parse string as a double
               final double mult = Double.parseDouble(str);

               // Multiply last element of weight array by mult 
               final int location = adjWeight.size() - 1;
               final double current = adjWeight.get(location);
               final double calc = current * mult;

               // Update weight array list
               adjWeight.set(location, calc);
            }

            // Else it has a symbol
            else {
               // Declare found boolean
               boolean found = false;

               // Iterate through array list of records
               for (int k = 0; k < arrList.size(); k++) { 

                  // Access current record elements
                  final Entry current = arrList.get(k);

                  // If current Symbol is the same as the split element, get the weight
                  if (current.symbol().equals(splitElementLine[j])) {
                     // Add weight of element to weight list
                     adjWeight.add(current.weight());

                     // If a weight is found update found boolean
                     found = true;
                  }
               }

               // If a value is not found, the string must be illegal
               if (!found) {
                  hasIllegal = true;
               }
            }
         }

         // Find result by summing adjWeight values if the adjWeight has values
         if (adjWeight.size() > 0) {

            // Declare a result double to replace the string double 
            double resultValue = 0;
            for (int i = 0; i < adjWeight.size(); i++) { 
               resultValue += adjWeight.get(i);
            }

            // Convert result double to string
            result = Double.toString(resultValue);
         }

         // Print Solution
         System.out.print("Molecular weight of ");
         for (int i = 0; i < splitElementLine.length; i++) {
            System.out.print(splitElementLine[i] + " ");
         }
         System.out.println(" = " + result);

      }
   } 
}
