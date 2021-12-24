/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Guitar
 */

// Import libraries
import java.util.Random;
import java.util.ArrayDeque;

public final class GuitarString extends java.util.ArrayDeque<Double> {

   // Declare RNG
   private static final Random RNG = new Random (Long.getLong ("seed", System.nanoTime()));

   // Declare constants
   private final int rate = 44100;
   private final double decay = 0.996;

   // Declare private object values
   private double frequency;
   private double buffer;
   private ArrayDeque<Double> stack; 

   // Create a guitar string of the given frequency and initialize the ArrayDeque
   public GuitarString (final double f) {
      this.frequency = f;
      this.buffer = rate / this.frequency;
      this.stack = new ArrayDeque<>();
   }

   // Set the buffer to white noise
   public void pluck () {
      for (int i = 0; i < buffer; i++) {
         // Random double between 0 and 1, converted to -0.5 to 0.5 range
         final double rand = RNG.nextDouble() - 0.5;
         stack.add(rand);
      }
   }   

   // Advance the simulation one time step
   public void tic () {
      // Retrieve and remove front element 
      final double first = stack.pollFirst();

      // Retrieve second element, now first in deque
      final double second = stack.getFirst();

      // Add avg of first two, multiplied by energy decay factor
      final double last = (first + second) * 0.5 * decay;
      stack.addLast(last);
   }

   // Return the current sample                    
   public double sample () {
      return stack.getFirst();
   }      

   // Main method for testing
   public static void main (final String[] args) { } 
}
