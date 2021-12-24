/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Geo-guesser
 */

// Import libraries
import java.util.Random;
import java.util.Scanner;

public final class Geoguesser {

   // Pass the seed as a Java property
   private static final Random RNG = new Random (Long.getLong ("seed", System.nanoTime()));
   
   // Declare record 
   public record GeoPoint(Double lat, Double lon) {

      // Method to create a random point
      public static GeoPoint generateRandom () {

         // Generate Random GeoPoint with u and v
         final double u = RNG.nextDouble();
         final double v = RNG.nextDouble();
         
         // Calculate phi (latitude) and theta (longitude)
         double phi = Math.toDegrees(Math.acos(2 * v - 1));
         double theta = Math.toDegrees(2 * Math.PI * u);
      
         // Declare default N and E
         String vertIndicator = "N";
         String horIndicator = "E";

         // Check for S and W
         final double maxLat = 90;
         final double maxLon = 180;
         if (phi > maxLat) {
            phi = phi - maxLat; 
            vertIndicator = "S";
         }
         if (theta > maxLon) {
            theta = theta - maxLon;
            horIndicator = "W";
         }

         // Print before negative modifications
         System.out.printf("%.2f %s %.2f %s %n", phi, vertIndicator, theta, horIndicator);

         // Apply S and W modifications
         if (vertIndicator.equalsIgnoreCase("S")) {
            phi = -phi;
         }
         if (horIndicator.equalsIgnoreCase("W")) {
            theta = -theta;
         }

         return new GeoPoint(phi, theta);
      }
   }

   // Method to calculate great circle distance
   public static void distance (final double lambda1, final double phi1, 
         final double lambda2, final double phi2) {

      final double R = 6371.009; // Mean radius of Earth
      final double diff = Math.abs(lambda2 - lambda1);
      final double centralAngle = Math.acos(Math.sin(Math.toRadians(phi1)) 
            * Math.sin(Math.toRadians(phi2)) + Math.cos(Math.toRadians(phi1)) 
            * Math.cos(Math.toRadians(phi2)) * Math.cos(Math.toRadians(diff)));
      final double d = R * centralAngle;

      System.out.printf("%.2f km %n", d);
   }
   
   // Main method
   public static void main (final String[] args) {

      // Declare scanner
      final Scanner sc = new Scanner (System.in); 

      // Declare Melbourne GeoPoint
      final GeoPoint melbourne = new GeoPoint(28.083000, -80.600000);

      // Call random generator method
      final GeoPoint random = GeoPoint.generateRandom();
      
      // While file has additional inputs
      while (sc.hasNextLine()) {

         // Scan each line, strip leading whitespace
         final String line = sc.nextLine().stripLeading();

         // Split string into string array, removing all whitespace
         final String[] splitLine = line.split("\\s+");

         // Find latitude
         double tempLat = Double.parseDouble(splitLine[0]);
         final String vertical = splitLine[1];
         if (vertical.equalsIgnoreCase("S")) {
            tempLat = -tempLat;
         }

         // Find longitude 
         double tempLon = Double.parseDouble(splitLine[2]);
         final String horizontal = splitLine[3];
         if (horizontal.equalsIgnoreCase("W")) {
            tempLon = -tempLon;
         }
      
         // Declare new GeoPoint
         final GeoPoint point = new GeoPoint(tempLat, tempLon);

         // Find distance between random and point
         distance(random.lon(), random.lat(), point.lon(), point.lat());

         // Find distance between melbourne and point
         distance(melbourne.lon(), melbourne.lat(), point.lon(), point.lat());
      } 
   } 
}
