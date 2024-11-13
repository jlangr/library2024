package domain.core;

public class ConstrainedAmountLateStrategy {
   private final int fineBasis;

   public ConstrainedAmountLateStrategy(int fineBasis) {
      this.fineBasis = fineBasis;
   }

   public int calculateFine(int daysLate) {
      return Math.min(1000, 100 + fineBasis * daysLate);
   }
}
