package domain.core;

public class DaysLateStrategy implements LateStrategy {
   private final int dailyFine;

   public DaysLateStrategy(int dailyFine) {
      this.dailyFine = dailyFine;
   }

   public int calculateFine(int daysLate) {
      return dailyFine * daysLate;
   }
}
