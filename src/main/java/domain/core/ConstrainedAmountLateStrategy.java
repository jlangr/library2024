package domain.core;

public class ConstrainedAmountLateStrategy implements LateStrategy {
   private final int dailyFine;

   public ConstrainedAmountLateStrategy(int dailyFine) {
      this.dailyFine = dailyFine;
   }

   public int calculateFine(int daysLate) {
      return Math.min(1000, 100 + dailyFine * daysLate);
   }
}
