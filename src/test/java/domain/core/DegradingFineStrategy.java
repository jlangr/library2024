package domain.core;

public class DegradingFineStrategy implements LateStrategy {
   private final int fineBasis;
   private final double degradationRate;

   public DegradingFineStrategy(int fineBasis, double degradationRate) {
      this.fineBasis = fineBasis;
      this.degradationRate = degradationRate;
   }

   @Override
   public int calculateFine(int fineBasis, int daysLate) {
      double total = fineBasis * (1 - Math.pow(1 - degradationRate, daysLate)) / degradationRate;
      return (int) Math.round(total);
   }
}
