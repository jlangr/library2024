package domain.core;

public class DaysLateStrategy implements LateStrategy {
   public int calculateFine(int fineBasis, int daysLate) {
      return fineBasis * daysLate;
   }
}
