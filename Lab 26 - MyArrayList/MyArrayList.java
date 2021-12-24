/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: MyArrayList
 */

// import libraries
import java.util.Arrays;

public final class MyArrayList extends MyListInterface {

   // Declare default size of array
   private static int defaultCapacity = 10;
   // Declare current number of elements stored
   private static int stored = 0;
   // Declare empty array
   private static int[] myArray = {};

   // Constructs an empty list with an initial capacity of 10
   public MyArrayList () {
      myArray = new int[defaultCapacity];
   }

   // Constructs an empty list with the specified initial capacity
   public MyArrayList (final int initialCapacity) {
      myArray = new int[initialCapacity];
   }

   // Increases capacity array to ensure it can hold number of elements in argument
   private void ensureCapacity (final int minCapacity) {
      // Copy myArray into larger array
      if (myArray.length <= minCapacity) {
         myArray = Arrays.copyOf(myArray, myArray.length * 2);
      }
   }

   // Main method for testing
   public static void main (final String[] args) { }

   // Inserts the specified element at the end of the list, return true if it succeeded
   @Override
   public boolean add (final Integer element) {
      ensureCapacity(stored + 1);
      // Set last element of list as element 
      myArray[stored] = element;
      stored++;
      return true;
   }

   // Returns the element at the specified position in this list
   @Override
   public Integer get (final int index) {
      // Check for index out of bounds
      if (index >= myArray.length || index < 0) {
         throw new IndexOutOfBoundsException();
      }
      return myArray[index];
   }

   // Replaces element at specified position in list, return removed element value
   @Override
   public Integer set (final int index, final Integer element) {
      // Check for index out of bounds
      if (index >= stored || index < 0) {
         throw new IndexOutOfBoundsException();
      }
      // Find removed element and replace it
      final int removedElement = myArray[index];
      myArray[index] = element;
      return removedElement;
   }

   // Removes element at index specified by argument, return removed element value.
   @Override
   public Integer remove (final int index) {
      // Check for index out of bounds
      if (index >= stored || index < 0) {
         throw new IndexOutOfBoundsException();
      }
      // Find removed element
      final int removedElement = myArray[index];
      // Shift remaining values 
      for (int i = index; i < myArray.length - 1; i++) {
         myArray[i] = myArray[i + 1];
      }
      // Update stored
      stored--;
      return removedElement;
   }

   // Find and Remove first occurrence of specified element from list, if present.
   @Override
   public boolean remove (final Integer element) {
      // Iterate to check for element
      for (int i = 0; i < myArray.length; i++) {
         if (myArray[i] == element) {
            remove(i);
            return true;
         }
      }
      return false;
   }

   // Returns number of elements in list
   @Override
   public int size () {
      return stored;
   }

   // Removes all of the elements from this list
   @Override
   public void clear () {
      // Declare seperate removable variable since stored will change 
      final int removable = stored;
      // Iterate through list, applying remove to each removable element
      for (int i = 0; i < removable; i++) {
         remove(0);
      }
      // Update stored value
      stored = 0;
   }

   // Convert array to string
   @Override
   public String toString () {
      String asString = "";
      for (int i = 0; i < stored; i++) {
         asString = asString + String.valueOf(myArray[i]);
      }
      return asString;
   }
}
