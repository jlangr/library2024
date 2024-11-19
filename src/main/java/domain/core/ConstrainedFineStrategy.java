package domain.core;

public class ConstrainedFineStrategy implements LateStrategy {
   public static final short BASE_FEE = 100;
   public static final short MAX_FINE = 1000;

   private final int fineBasis;

   public ConstrainedFineStrategy(int fineBasis) {
      this.fineBasis = fineBasis;
   }

   @Override
   public int calculateFine(int daysLate) {
      return Math.min(1000, 100 + fineBasis * daysLate);
   }
}
