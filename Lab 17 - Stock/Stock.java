/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Stock
 */

// Import libraries
import java.util.Scanner;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public final class Stock {

   // Declare record 
   public record Trade(String date, Double adj) {}
   
   // Main method
   public static void main (final String[] args) {

      // Declare scanner
      final Scanner sc = new Scanner (System.in); 

      // Scan each line, strip leading whitespace, split into string array
      final String line = sc.nextLine().stripLeading();
      final String[] splitLine = line.split(",");

      // Find date collumn and adj close collum
      int dateCollumn = -1;
      int adjCollumn = -1;
      for (int i = 0; i < splitLine.length; i++) {
         if (splitLine[i].equalsIgnoreCase("Date")) {
            dateCollumn = i;
         }
         if (splitLine[i].equalsIgnoreCase("Adj Close")) {
            adjCollumn = i;
         }
      }

      // Declare array list to store records
      final ArrayList<Trade> arrList = new ArrayList<Trade>();

      // While file has additional inputs
      while (sc.hasNextLine()) {

         // Scan each line, strip leading whitespace, split into string array
         final String tempLine = sc.nextLine().stripLeading();
         final String[] splitTempLine = tempLine.split(",");

         // Extract date and adj from string array
         final String dateValue = splitTempLine[dateCollumn];
         final String adjValue = splitTempLine[adjCollumn];
         final Double adjValueDouble = Double.parseDouble(adjValue);

         // Declare new daily Trade record
         final Trade daily = new Trade(dateValue, adjValueDouble);

         // Add daily to array list
         arrList.add(daily);
      } 

      // Declare min and max gain
      Double min = 0.0;
      String minDate = "No Min";
      Double max = 0.0;
      String maxDate = "No Max";

      // Declare longest streak 
      int maxStreak = 0; 
      int currentStreak = 0;
      String start = "No Min Date";
      String tempStart = "Temp Min Date";
      String end = "No Max Date";

      // Precentage calculations for each date
      for (int i = 1; i < arrList.size(); i++) {

         // Access previous date and adj 
         final Trade previous = arrList.get(i-1);
         final String previousDate = previous.date();
         final Double previousAdj = previous.adj();

         // Access current date and adj from temp
         final Trade current = arrList.get(i);
         final String currentDate = current.date();
         final Double currentAdj = current.adj();

         // For each date calculate the percent change between the two days
         final Double percent = (currentAdj/previousAdj - 1) * 100;

         // Check for min gain
         if (percent < min) {
            min = percent;
            minDate = currentDate;
         }

         // Check for max gain
         if (percent > max) {
            max = percent;
            maxDate = currentDate;
         }

         // Check for streak decrease
         if (previousAdj > currentAdj) {
            currentStreak = 0;
         }
         // Otherwise apply streak increase
         else {
            currentStreak++;

            // Check for start of an entirely new streak
            if (currentStreak == 1) {
               tempStart = currentDate;
            }

            // Update longest streak to current streak
            if (currentStreak > maxStreak) {
               maxStreak = currentStreak;
               start = tempStart;
               end = currentDate;
            }
         }
      }

      // Formatting with DateTimeFormatter
      final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("EEE, dd MMM uuuu");

      // Declare local dates
      final LocalDate formatedMax = LocalDate.parse(maxDate, formatter);
      final LocalDate formatedMin = LocalDate.parse(minDate, formatter);
      final LocalDate formatedStart = LocalDate.parse(start, formatter);
      final LocalDate formatedEnd = LocalDate.parse(end, formatter);

      // Format local dates
      final String stringMax = outputFormat.format(formatedMax);
      final String stringMin = outputFormat.format(formatedMin);
      final String stringStart = outputFormat.format(formatedStart);
      final String stringEnd = outputFormat.format(formatedEnd);

      // Print Answers 
      final String percentSymbol = "%";
      System.out.printf("Max gain: %.2f%s on %s %n", max, percentSymbol, stringMax);
      System.out.printf("Min gain: %.2f%s on %s %n", min, percentSymbol, stringMin);
      System.out.printf("Longest growth streak: %d days ", maxStreak);
      System.out.printf("(%s to %s) %n", stringStart, stringEnd);
   } 
}
