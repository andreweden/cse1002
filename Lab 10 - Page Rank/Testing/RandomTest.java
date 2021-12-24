
// Import libraries
import java.util.Random;

public final class RandomTest {

   // Pass the seed as a Java property
   private static final Random RNG = new Random (Long.getLong ("seed", System.nanoTime()));
   
   public static void main (final String[] args) {

      double test = RNG.nextDouble();
      System.out.println("Test: " + test);
   }
}
