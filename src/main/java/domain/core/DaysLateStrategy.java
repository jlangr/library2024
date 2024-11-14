package domain.core;

public class DaysLateStrategy implements LateStrategy {
   @Override
   public int calculateFine(int fineBasis, int daysLate) {
      return fineBasis * daysLate;
   }
}
