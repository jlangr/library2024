package domain.core;

public class DaysLateStrategy {
   private final int fineBasis;

   public DaysLateStrategy(int fineBasis) {
      this.fineBasis = fineBasis;
   }

   public int calculateFine(int daysLate) {
      return fineBasis * daysLate;
   }
}
