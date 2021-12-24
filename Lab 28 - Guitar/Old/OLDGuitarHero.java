/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Guitar
 */

// Import libraries
import java.util.Scanner;
import java.util.Random;

public final class GuitarHero extends GuitarString {

   // Declare RNG
   private static final Random RNG = new Random (Long.getLong ("seed", System.nanoTime()));

   // Declare constants
   private final int RATE = 44100;
   private final double DECAY = 0.996;

   // Declare private object values
   private int frequency = 44100;

   // ******************************************************************************
   
   // create a guitar string of the given frequency and initialize the ArrayDeque
   /*
   public GuitarString (double f) {
      this.frequency = f;

      // TESTING
      System.out.println("frequency" + this.frequency);
   }
   */

   /*
   The constructor creates an ArrayDeque and stores the frequency to be used in pluck method. 
   Recall that the buffer size will be the sampling rate(44,100) divided by the frequency 
   (rounding the quotient up to the nearest integer).
   */

   //******************************************************************************

   // set the buffer to white noise
   @Override
   public void pluck() {

      // TESTING
      System.out.println("pluck");

      // Declare energy decay 
      final double n = RATE / this.frequency;

      for (int i = 0; i < n; i++) {
         // Random double between 0 and 1, converted to -0.5 to 0.5 range
         final double rand = RNG.nextDouble() - 0.5;
         System.out.println(rand);
      }
   }   

   /*
   Add the N elements in the buffer with N random values between -0.5 and +0.5 
   ( N is sample rate(44,100) divided by frequency).
   */

   //******************************************************************************

   // advance the simulation one time step
   @Override
   public void tic() {

      // Retrieve and remove front element 
      // final double first = GuitarHero.pollFirst();

      // Retrieve second element, now first
      // final double second = GuitarHero.getFirst();

      // Add avg of first two, multiplied by energy decay factor
      // final double last = (first + second) * 0.5 * DECAY;
      // GuitarHero.addLast(double last);

      // TESTING
      System.out.println("tic");
   }

   /*
   Apply the Karplus-Strong update: delete the sample at the front of the buffer 
   and add to the end of the buffer the average of the first two samples, 
   multiplied by the energy decay factor.
   */

   //******************************************************************************
    
   // return the current sample  
   @Override                    
   public double sample() {
      //return GuitarHero.getFirst();
      
      // TESTING
      final double sample = 0;
      return sample;
   }      

   //******************************************************************************
   // Main method
   public static void main (final String[] args) {

      // TESTING
      final GuitarHero sim = new GuitarHero();
      System.out.println("Program has run");
      sim.pluck();
      sim.tic();
      System.out.println(sim.sample());

      // Declare scanner 
      final Scanner sc = new Scanner (System.in); 

      // While file has additional inputs
      while (sc.hasNextLine()) {
         final int index = sc.nextInt();
         final double duration = sc.nextDouble();
      }

      // find average of first 2 values
      // remove value in first position
      // place average in last position
   
   } 
}
