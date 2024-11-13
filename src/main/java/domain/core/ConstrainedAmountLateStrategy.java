package domain.core;

public class ConstrainedAmountLateStrategy {
   public int calculateFine(int fineBasis, int daysLate) {
      return Math.min(1000, 100 + fineBasis * daysLate);
   }
}
