/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Predator-Prey Simulation
 */

// import libraries
import java.util.Random;

// Simulation 
public final class Simulation {

   // Neighborhood implementation
   class NeighborhoodImpl implements Neighborhood {

      // Declare internal variables for counts
      private int foxCount;
      private int rabbitCount;
      private int grassCount;
      private int memberCount;

      // Neighborhood constructor
      public NeighborhoodImpl(final Living[][] board, final int row, final int col) {

         // Update instance variables
         this.foxCount = 0;
         this.rabbitCount = 0;
         this.grassCount = 0;
         this.memberCount = 0;

         // Loop through surroundings
         for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
               
               final Class currentClass = board[r][c].getClass();
               if (currentClass.equals(Fox.class)) {
                  foxCount++;
               }
               else if (currentClass.equals(Rabbit.class)) {
                  rabbitCount++;
               }
               else if (currentClass.equals(Grass.class)) {
                  grassCount++;
               }
               else {
                  memberCount++;
               }  
            }
         }

         // Remove center piece from neighborhood count
         final Class centerClass = board[row][col].getClass();
         if (centerClass.equals(Fox.class)) {
            foxCount--;
         }
         else if (centerClass.equals(Rabbit.class)) {
            rabbitCount--;
         }
         else if (centerClass.equals(Grass.class)) {
            grassCount--;
         }
         else {
            memberCount--;
         }  
      }

      // Get count method
      public int getCount (final Class c) {

         // Fox count
         if (c.equals(Fox.class)) { 
            return foxCount;
         }
         // Rabbit count
         else if (c.equals(Rabbit.class)) { 
            return rabbitCount;
         }
         // Grass count
         else if (c.equals(Grass.class)) { 
            return grassCount;
         }
         // Member count
         else {
            return memberCount;
         }
      }
   }

   // Declare Fox
   class Fox implements Living {
      private int age = 0;
      public Living next (final Neighborhood n) {
         // Fox dies if #foxes > 5
         if (n.getCount(Fox.class) > 5) {
            return new Member();
         }
         // Fox dies if #rabbits = 0
         else if (n.getCount(Rabbit.class) <= 0) {
            return new Member();
         }
         // Fox dies if age > 5
         else if (age > 5) {
            return new Member();
         }
         // Otherwise remains a fox and increase age
         else {
            this.age++;
            return this;
         }
      }
      public char getCharRepresentation () {
         return 'X';
      }
   }

   // Declare Rabbit
   class Rabbit implements Living {
      private int age = 0;
      public Living next (final Neighborhood n) {
         // Rabbit dies if #grass = 0
         if (n.getCount(Grass.class) == 0) {
            return new Member();
         }
         // Rabbit dies if #foxes >= #rabbits
         else if (n.getCount(Fox.class) >= n.getCount(Rabbit.class)) {
            return new Member();
         }
         // Rabbit dies if age > 3
         else if (age > 3) {
            return new Member();
         }
         // Otherwise remains a rabbit and increase age
         else {
            this.age++;
            return this;
         }
      }
      public char getCharRepresentation () {
         return 'R';
      }
   }

   // Declare Grass
   class Grass implements Living {
      public Living next (final Neighborhood n) {
         // Grass becomes rabbit if #grass > 2#rabbits
         if (n.getCount(Grass.class) > (n.getCount(Rabbit.class) * 2)) {
            return new Rabbit();
         }
         // Grass remains if #grass > #rabbits 
         else if (n.getCount(Grass.class) > n.getCount(Rabbit.class)) {
            return this;
         }
         // Otherwise dies
         else {
            return new Member();
         }
      }
      public char getCharRepresentation () {
         return 'G';
      }
   }

   // Declare Member (neither Fox, Rabbit, or Grass)
   class Member implements Living {
      public Living next (final Neighborhood n) {
         // Becomes fox if #rabbits > 2
         if (n.getCount(Rabbit.class) > 2) {
            return new Fox();
         }
         // Becomes rabbit if #grass > 4
         else if (n.getCount(Grass.class) > 4) {
            return new Rabbit();
         }
         // Becomes grass if #grass > 0
         else if (n.getCount(Grass.class) > 0) {
            return new Grass();
         }
         // Otherwise remains member
         else {
            return this;
         }
      }
      public char getCharRepresentation () {
         return '.';
      }
   }

   // Declare RNG
   private static final Random RNG = new Random (Long.getLong ("seed", System.nanoTime()));

   // Initialize board
   public void init (final Living[][] board, final int size) {

      // Iterate and set each element to a period
      for (int row = 0; row < size; row++) {
         for (int col = 0; col < size; col++) {
            board[row][col] = new Member();
         }
      }

      // Add other elements (fox, rabbit, grass)
      for (int row = 1; row < size - 1; row++) {
         for (int col = 1; col < size - 1; col++) {
            board[row][col] = gen();
         }
      }

      // Call printBoard method
      printBoard(board, size);
   }

   // Randomly generate placements 
   public Living gen () {

      // Declare constants
      final double foxPercent = 0.1;
      final double rabbitPercent = 0.3;
      final double grassPercent = 0.6;

      // Declare new random double
      final double x = RNG.nextDouble();

      // 10% Fox 
      if (x < foxPercent) {
         return new Fox();   
      }
      // 20% Rabbit
      else if (x < rabbitPercent) {
         return new Rabbit();
      }
      // 30% Grass
      else if (x < grassPercent) {
         return new Grass();
      }
      // 40% Empty
      else { 
         return new Member();
      }
   }

   // Print board method
   public void printBoard (final Living[][] board, final int size) {
      for (int row = 0; row < size; row++) {
         for (int col = 0; col < size; col++) {
            System.out.print(board[row][col].getCharRepresentation());
         }
         System.out.println();
      }
   }

   // Cycle through generations greater than 0
   public Living[][] cycle (final Living[][] board, final int size) {

      // Declare new board
      final Living[][] newBoard = new Living[size][size];

      // Initialize new board with each element as a member
      for (int row = 0; row < size; row++) {
         for (int col = 0; col < size; col++) {
            newBoard[row][col] = new Member();
         }
      }

      // Iterate through to update based on cycle rules
      for (int row = 1; row < size - 1; row++) {
         for (int col = 1; col < size - 1; col++) {
            
            final Living temp = board[row][col].next(new NeighborhoodImpl(board, row, col));
            newBoard[row][col] = temp;
         }
      }

      // Call printBoard method
      printBoard(newBoard, size);

      // Return new board to update the previous board
      return newBoard;
   }

   // Main function
   public static void main (final String[] args) {

      // Parse size and cycles from command line
      final int size = Integer.parseInt(args[0]);
      final int cycle = Integer.parseInt(args[1]);

      // Declare board array
      Living[][] board = new Living[size][size];

      // New simulation instance
      final Simulation simulation = new Simulation();

      // Cycle through generations
      for (int i = 0; i <= cycle; i++) {
         System.out.printf("Cycle = %d %n", i);
         // Initialize cycle 0 board
         if (i == 0) {
            simulation.init(board, size);
         }
         // Cycle through future generations
         else {
            board = simulation.cycle(board, size);
         }
         System.out.println();
      }
   }
}
