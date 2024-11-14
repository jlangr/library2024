package domain.core;

public class ConstrainedFineStrategy implements LateStrategy{
   @Override
   public int calculateFine(int fineBasis, int daysLate) {
      return Math.min(1000, 100 + fineBasis * daysLate);
   }
}
