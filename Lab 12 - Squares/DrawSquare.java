public class DrawSquare {
   public static void main (final String[] args) {
      double x = 0.5, y = 0.5;   // center of square
      double size = 0.5;   // side length of square
      
      // plot a square, centered on (x,y) of the given side length
      // with a light gray background and black border
      StdDraw.setPenColor (StdDraw.LIGHT_GRAY);
      StdDraw.filledSquare (x, y, size/2);
      StdDraw.setPenColor (StdDraw.BLACK);
      StdDraw.square (x, y, size/2);
   }
}