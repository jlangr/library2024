package domain.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DegradingFineStrategyTest {
   int initialFine = 1000;
   double degradationRate = 0.1d;
   DegradingFineStrategy strategy = new DegradingFineStrategy(initialFine, degradationRate);

   @Test
   void firstDayIsFineBasis() {
      assertEquals(initialFine, strategy.calculateFine(1));
   }

   @Test
   void nextDayReducedUsingDegradationRate() {
      assertEquals(1000 + 900, strategy.calculateFine(2));
   }

   @Test
   void multipleDays() {
      assertEquals(1000 + 900 + 810 + 729 + 656, strategy.calculateFine(5));
   }
}