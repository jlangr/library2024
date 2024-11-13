package domain.core;

public class DaysLateStrategy {
   public int calculateFine(int fineBasis, int daysLate) {
      return fineBasis * daysLate;
   }
}
