/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Snake
 */

// Import libraries
import java.util.Random;
import java.awt.event.KeyEvent;

public final class Snake {

   // Declare RNG 
   private static final Random RNG = new Random (Long.getLong ("seed", System.nanoTime()));

   // Declare constants
   public static final int WIDTH = 800;
   public static final int HEIGHT = 600;
   public static final int TOP = 100;
   public static final int STANDARD = 20;
   public static int score = 0;
   public static int highScore = 0;
   public static int dirX = 0;      // 1 = right, -1 = left, 0 = none
   public static int dirY = 0;      // 1 = up, -1 = down, 0 = none
   public static int foodX = 0;     // Declare food x direction
   public static int foodY = 0;     // Declare food y direction
   public static int length = 0;    // Declare snake length

   // Texture settings
   public static boolean fancyWater = true;
   public static boolean fancyBeach = true;
   public static boolean fancyNet = true;
   public static boolean fancyBoat = true;
   public static boolean fancyTrash = true;


   // Initial set up
   public static void setUp () {
      StdDraw.setCanvasSize(WIDTH, HEIGHT);
      StdDraw.clear(StdDraw.BLACK); 
      StdDraw.setXscale(0, WIDTH);
      StdDraw.setYscale(0, HEIGHT);

      // Go to main menu
      mainMenu();
   }

   // Main menu
   public static void mainMenu () {
      StdDraw.clear(StdDraw.BOOK_BLUE);
      StdDraw.picture(WIDTH/2, HEIGHT/2, "menu.jpeg");
   
      // Reset score 
      score = 0;

      // Check for user selection
      boolean keyPressed = false;
      while (!keyPressed) {
         if (StdDraw.isKeyPressed(KeyEvent.VK_1)) {
            keyPressed = true;
            setWater();
            setTop();
            generateFood();
            moveBoat();
         }
         if (StdDraw.isKeyPressed(KeyEvent.VK_2)) {
            keyPressed = true;
            StdDraw.clear(StdDraw.BOOK_BLUE);
            settings();
         }
      }
   }

   // Settings
   public static void settings () {
      StdDraw.picture(WIDTH/2, HEIGHT/2, "settings.jpeg");
      
      StdDraw.setPenColor(StdDraw.WHITE);
      StdDraw.text(WIDTH/2, HEIGHT/2 + STANDARD * 3, "1. Water texture: " + fancyWater);
      StdDraw.text(WIDTH/2, HEIGHT/2 + STANDARD * 1, "2. Beach texture: " + fancyBeach);
      StdDraw.text(WIDTH/2, HEIGHT/2 - STANDARD * 1, "3. Net texture: " + fancyNet);
      StdDraw.text(WIDTH/2, HEIGHT/2 - STANDARD * 3, "4. Boat texture: " + fancyBoat);
      StdDraw.text(WIDTH/2, HEIGHT/2 - STANDARD * 5, "5. Trash texture: " + fancyTrash);

      // Check for user selection
      boolean keyPressed = false;
      while (!keyPressed) {
         if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            keyPressed = true;
            mainMenu();
         }
         if (StdDraw.isKeyPressed(KeyEvent.VK_1)) {
            keyPressed = true;
            if (fancyWater) {
               fancyWater = false;
            }
            else {
               fancyWater = true;
            }
            settings();
         }
         if (StdDraw.isKeyPressed(KeyEvent.VK_2)) {
            keyPressed = true;
            if (fancyBeach) {
               fancyBeach = false;
            }
            else {
               fancyBeach = true;
            }
            settings();
         }
         if (StdDraw.isKeyPressed(KeyEvent.VK_3)) {
            keyPressed = true;
            if (fancyNet) {
               fancyNet = false;
            }
            else {
               fancyNet = true;
            }
            settings();
         }
         if (StdDraw.isKeyPressed(KeyEvent.VK_4)) {
            keyPressed = true;
            if (fancyBoat) {
               fancyBoat = false;
            }
            else {
               fancyBoat = true;
            }
            settings();
         }
         if (StdDraw.isKeyPressed(KeyEvent.VK_5)) {
            keyPressed = true;
            if (fancyTrash) {
               fancyTrash = false;
            }
            else {
               fancyTrash = true;
            }
            settings();
         }
      }
   }

   // Set water texture
   public static void setWater () {
      StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
      if (fancyWater) {
         StdDraw.picture(WIDTH/2, HEIGHT/2, "waterTexture.png");
      }
   }

   // Set top border
   public static void setTop () {
      if (fancyBeach) {
         StdDraw.picture(WIDTH/2, HEIGHT-100, "beach.png");
      }
      else {
         StdDraw.setPenColor(StdDraw.YELLOW);
         StdDraw.filledRectangle(WIDTH/2, HEIGHT, WIDTH, 100);
      }
   }

   // Generate food location
   public static void generateFood () {
      foodX = RNG.nextInt(WIDTH);
      foodY = RNG.nextInt(HEIGHT - TOP);
      placeFood();
   }

   // Place food
   public static void placeFood () {
      if (fancyTrash) {
         StdDraw.picture(foodX, foodY, "barrel.png", STANDARD*2, STANDARD*2);
      }
      else {
         StdDraw.setPenColor(StdDraw.GREEN);
         StdDraw.filledSquare(foodX, foodY, STANDARD);
      }
   }

   // Place boat facing appropriate direction
   public static void placeBoat (final int x, final int y) {
      final int rightAngle = 90;
      if (fancyBoat) {
         // RIGHT
         if (dirX == -1) {
            StdDraw.picture(x, y, "boat.png", STANDARD*2, STANDARD*2, rightAngle);
         }
         // LEFT
         else if (dirX == 1) {
            StdDraw.picture(x, y, "boat.png", STANDARD*2, STANDARD*2, rightAngle*3);
         }
         // UP
         else if (dirY == 1) {
            StdDraw.picture(x, y, "boat.png", STANDARD*2, STANDARD*2);
         }
         // DOWN
         else if (dirY == -1) {
            StdDraw.picture(x, y, "boat.png", STANDARD*2, STANDARD*2, rightAngle*2);
         }
         // START
         else {
            StdDraw.picture(x, y, "boat.png", STANDARD*2, STANDARD*2, rightAngle);
         }
      }
      else {
         StdDraw.setPenColor(StdDraw.RED);
         StdDraw.filledSquare(x, y, STANDARD);
      }
   }

   // Check for key inputs and update direction
   public static void checkKey () {
      // LEFT
      if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) || StdDraw.isKeyPressed(KeyEvent.VK_A)) {
         // Previous not right
         if (dirX != 1) {
            dirX = -1;
            dirY = 0;
         }
      }

      // RIGHT
      if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) || StdDraw.isKeyPressed(KeyEvent.VK_D)) {
         // Previous not left
         if (dirX != -1) {
            dirX = 1;
            dirY = 0;
         }
      }

      // UP
      if (StdDraw.isKeyPressed(KeyEvent.VK_UP) || StdDraw.isKeyPressed(KeyEvent.VK_W)) {
         // Previous not down
         if (dirY != -1) {
            dirX = 0;
            dirY = 1;
         }
      }

      // DOWN
      if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN) || StdDraw.isKeyPressed(KeyEvent.VK_S)) {
         // Previous not up
         if (dirY != 1) {
            dirX = 0;
            dirY = -1;
         }
      }
   }

   // Place Nets
   public static void placeNets (final int x, final int y) {

      for (int i = 0; i < length; i++) {
         StdDraw.setPenColor(StdDraw.RED);
         StdDraw.filledSquare(x + dirX * length, y + dirY * length, STANDARD);
      }
   }

   // Dispay score
   public static void displayScore () {
      StdDraw.text(WIDTH/2, HEIGHT - STANDARD, "Score: " + score + " Highscore: " + highScore);
   }

   // Boat movement
   public static void moveBoat () {

      // Declare alive state 
      boolean alive = true;

      // Boat starting position 
      int x = WIDTH/2;
      int y = HEIGHT/2;

      // Place starting boat
      placeBoat(x, y);

      // While character is alive
      while (alive) {

         // Animate with buffering
         StdDraw.enableDoubleBuffering();

         // Update textures
         setWater();
         setTop();
         
         // Place food 
         placeFood();

         // Display score 
         StdDraw.setPenColor(StdDraw.BLACK);
         displayScore();

         // Check for key and update position
         checkKey();
         // LEFT
         if (dirX == -1) {
            x = x - STANDARD;
         }
         // RIGHT
         if (dirX == 1) {
            x = x + STANDARD;
         }
         // UP
         if (dirY == 1) {
            y = y + STANDARD;
         }
         // DOWN
         if (dirY == -1) {
            y = y - STANDARD;
         }

         // Place Boat
         placeBoat(x, y);

         // Place Nets
         // placeNets(x,y);
         // This was intended to be the tail, but I never got it to work

         // Check for non-food collision 
         alive = checkCollision(x, y);

         // Check for food collision
         checkFoodCollision(x, y);

         // Animate with buffering
         StdDraw.pause(100);
         StdDraw.show();

         // Check for escape key press
         if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            alive = false;
         }

         // Check for death condition
         if (!alive) {

            // Reset direction and buffering
            dirX = 0;
            dirY = 0;
            StdDraw.disableDoubleBuffering();

            // Go to death screen
            deathScreen();
         }
      }
   }

   // Check for non-food collision
   public static boolean checkCollision (final int x, final int y) {

      // Wall collision
      if (0 > x || x > WIDTH || 0 > y || y > HEIGHT - TOP) {
         return false;
      }

      // Tail collision
      // Would be implemented if the tail mechanism worked

      else {
         return true;
      }
   }

   // Check for food collision
   public static void checkFoodCollision (final int x, final int y) {

      // detect collision with margin of error
      if (foodX - STANDARD * 2 <= x && x <= foodX + STANDARD * 2) {
         if (foodY - STANDARD * 2 <= y && y <= foodY + STANDARD * 2) {
            // Increase score
            score++;

            // Generate new food
            generateFood();

            // Increase snake length 
            length++;
         }
      }
   } 

   // Death screen 
   public static void deathScreen () {
      StdDraw.clear(StdDraw.BLACK);
      StdDraw.picture(WIDTH/2, HEIGHT/2, "gameover.jpeg");

      // Update high score
      StdDraw.setPenColor(StdDraw.WHITE);
      if (score >= highScore) {
         highScore = score;
      }
      displayScore();

      // Check for user selection
      boolean keyPressed = false;
      while (!keyPressed) {
         if (StdDraw.isKeyPressed(KeyEvent.VK_M)) {
            keyPressed = true;
            StdDraw.clear(StdDraw.GREEN);

            // Return to main menu to replay
            mainMenu();
         }
      }
   }

   // Main method
   public static void main (final String[] args) {
      // Call setup function to run the game
      setUp();
   } 
}
