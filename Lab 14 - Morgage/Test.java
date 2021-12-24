// Import libraries
import java.math.BigDecimal;

public final class Test {

   public static void main (final String[] args) {

      final BigDecimal test = new BigDecimal("-10");
      final BigDecimal negative = new BigDecimal("-1");
      final BigDecimal result = test.multiply(negative);

      System.out.println(result);

            
   }
}
