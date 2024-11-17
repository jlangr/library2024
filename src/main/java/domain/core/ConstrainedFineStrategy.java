package domain.core;

public class ConstrainedFineStrategy implements LateStrategy {
   public static final short BASE_FEE = 100;
   public static final short MAX_FINE = 1000;

   @Override
   public int calculateFine(int fineBasis, int daysLate) {
      return Math.min(MAX_FINE, BASE_FEE + fineBasis * daysLate);
   }
}
