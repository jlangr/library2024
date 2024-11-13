package com.loc.material.api;

import domain.core.ConstrainedAmountLateStrategy;
import domain.core.DaysLateStrategy;
import domain.core.LateStrategy;

import java.lang.reflect.InvocationTargetException;

public enum MaterialType {
   BOOK(21, 10, DaysLateStrategy.class),
   AUDIO_CASSETTE(14, 10, ConstrainedAmountLateStrategy.class),
   VINYL_RECORDING(14, 10, ConstrainedAmountLateStrategy.class),
   MICRO_FICHE(7, 200, ConstrainedAmountLateStrategy.class),
   AUDIO_CD(7, 100, ConstrainedAmountLateStrategy.class),
   SOFTWARE_CD(7, 500, ConstrainedAmountLateStrategy.class),
   DVD(3, 100, ConstrainedAmountLateStrategy.class),
   NEW_RELEASE_DVD(1, 200, ConstrainedAmountLateStrategy.class),
   BLU_RAY(3, 200, ConstrainedAmountLateStrategy.class),
   VIDEO_CASSETTE(7, 10, ConstrainedAmountLateStrategy.class);

   private final int checkoutPeriod;
   private final int dailyFine;
   private final Class<? extends LateStrategy> strategyClass;

   MaterialType(int checkoutPeriod, int dailyFine, Class<? extends LateStrategy> strategyClass) {
      this.checkoutPeriod = checkoutPeriod;
      this.dailyFine = dailyFine;
      this.strategyClass = strategyClass;
   }

   public int dailyFine() {
      return dailyFine;
   }

   public int checkoutPeriod() {
      return checkoutPeriod;
   }

   public LateStrategy strategy() {
      try {
         var constructor = strategyClass.getConstructor(int.class);
         return constructor.newInstance(dailyFine);
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
         throw new RuntimeException();
      }
   }
}
