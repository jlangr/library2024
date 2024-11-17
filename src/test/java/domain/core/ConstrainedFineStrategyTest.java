package domain.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstrainedFineStrategyTest {
   ConstrainedFineStrategy strategy = new ConstrainedFineStrategy();

   @Test
   void addsDailyFineAmountToMinimumFine() {
      assertEquals(ConstrainedFineStrategy.BASE_FEE + 5 * 2, strategy.calculateFine(5, 2));
   }

   @Test
   void maxesOutFine() {
      assertEquals(ConstrainedFineStrategy.MAX_FINE, strategy.calculateFine(10, 1000));
   }
}
