package domain.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstrainedFineStrategyTest {
   private ConstrainedFineStrategy strategy;

   @Test
   void addsDailyFineAmountToMinimumFine() {
      strategy = new ConstrainedFineStrategy(5);
      assertEquals(ConstrainedFineStrategy.BASE_FEE + 5 * 2, strategy.calculateFine(2));
   }

   @Test
   void maxesOutFine() {
      strategy = new ConstrainedFineStrategy(10);
      assertEquals(ConstrainedFineStrategy.MAX_FINE, strategy.calculateFine(1000));
   }
}
