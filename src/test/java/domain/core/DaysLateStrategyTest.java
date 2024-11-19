package domain.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaysLateStrategyTest {
   @Test
   void calculatesFineUsingDaysLateAndBasis() {
      var fineBasis = 10;
      assertEquals(50, new DaysLateStrategy(fineBasis).calculateFine(5));
   }
}

