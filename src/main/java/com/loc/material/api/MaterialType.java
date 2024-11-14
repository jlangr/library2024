package com.loc.material.api;

import domain.core.ConstrainedFineStrategy;
import domain.core.DaysLateStrategy;
import domain.core.LateStrategy;

public enum MaterialType {
   BOOK(21, 10, new DaysLateStrategy()),
   AUDIO_CASSETTE(14, 10, new ConstrainedFineStrategy()),
   VINYL_RECORDING(14, 10, new ConstrainedFineStrategy()),
   MICRO_FICHE(7, 200, new ConstrainedFineStrategy()),
   AUDIO_CD(7, 100, new ConstrainedFineStrategy()),
   SOFTWARE_CD(7, 500, new ConstrainedFineStrategy()),
   DVD(3, 100, new ConstrainedFineStrategy()),
   NEW_RELEASE_DVD(1, 200, new DaysLateStrategy()),
   BLU_RAY(3, 200, new ConstrainedFineStrategy()),
   VIDEO_CASSETTE(7, 10, new ConstrainedFineStrategy());

   private final int checkoutPeriod;
   private final int dailyFine;
   private final LateStrategy lateStrategy;

   MaterialType(int checkoutPeriod, int dailyFine, LateStrategy lateStrategy) {
      this.checkoutPeriod = checkoutPeriod;
      this.dailyFine = dailyFine;
      this.lateStrategy = lateStrategy;
   }

   public int dailyFine() {
      return dailyFine;
   }

   public int checkoutPeriod() {
      return checkoutPeriod;
   }

   public int calculateFine(int daysLate) {
      return lateStrategy.calculateFine(dailyFine, daysLate);
   }
}
