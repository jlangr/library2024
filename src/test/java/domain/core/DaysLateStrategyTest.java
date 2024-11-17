package domain.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaysLateStrategyTest {
   @Test
   void calculatesFineUsingDaysLateAndBasis() {
      assertEquals(50, new DaysLateStrategy().calculateFine(10, 5));
   }
}
