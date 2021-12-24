/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Guitar
 */

abstract class GuitarString extends java.util.ArrayDeque<Integer> {
   // create a guitar string of the given frequency and initialize the ArrayDeque
   // GuitarString(double frequency);

   // set the buffer to white noise
   abstract public void pluck();    

   // advance the simulation one time step
   abstract public void tic();

   // return the current sample                        
   abstract public double sample();                   
}
