package domain.core;

public class DaysLateStrategy implements LateStrategy {
   private final int fineBasis;

   public DaysLateStrategy(int fineBasis) {
      this.fineBasis = fineBasis;
   }

   @Override
   public int calculateFine(int daysLate) {
      return fineBasis * daysLate;
   }
}
