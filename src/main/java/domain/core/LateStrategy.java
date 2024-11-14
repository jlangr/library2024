package domain.core;

public interface LateStrategy {
   int calculateFine(int fineBasis, int daysLate);
}
