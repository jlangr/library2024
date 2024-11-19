package domain.core;

public class ConstrainedFineStrategy implements LateStrategy{
   private final int fineBasis;

   public ConstrainedFineStrategy(int fineBasis) {
      this.fineBasis = fineBasis;
   }

   @Override
   public int calculateFine(int daysLate) {
      return Math.min(1000, 100 + fineBasis * daysLate);
   }
}
