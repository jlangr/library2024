package com.loc.material.api;

import java.util.Map;

public class MaterialType {
   public static final int BOOK = 0; //21, 10
   public static final int AUDIO_CASSETTE = 1; //14, 10
   public static final int VINYL_RECORDING = 2; //14, 10
   public static final int MICRO_FICHE = 3; //7, 200
   public static final int AUDIO_CD = 4; //7, 100
   public static final int SOFTWARE_CD = 5; //7, 500
   public static final int DVD = 6; //3, 100
   public static final int NEW_RELEASE_DVD = 7; //1, 200
   public static final int BLU_RAY = 8; //3, 200
   public static final int VIDEO_CASSETTE = 9; //7, 10

   private static final Map<Integer, Integer> DAILY_FINE_AMOUNT = Map.of(
      BOOK, 10,
      AUDIO_CASSETTE, 10,
      VINYL_RECORDING, 10,
      MICRO_FICHE, 200,
      AUDIO_CD, 100,
      SOFTWARE_CD, 500,
      DVD, 100,
      NEW_RELEASE_DVD, 200,
      BLU_RAY, 200,
      VIDEO_CASSETTE, 10);

   public static int dailyFine(int format) {
      return DAILY_FINE_AMOUNT.get(format);
   }

   private static final Map<Integer, Integer> CHECKOUT_PERIODS = Map.of(
      BOOK, 21,
      AUDIO_CASSETTE, 14,
      VINYL_RECORDING, 14,
      MICRO_FICHE, 7,
      AUDIO_CD, 7,
      SOFTWARE_CD, 7,
      DVD, 3,
      NEW_RELEASE_DVD, 1,
      BLU_RAY, 3,
      VIDEO_CASSETTE, 7);

   public static int checkoutPeriod(int format) {
      return MaterialType.CHECKOUT_PERIODS.get(format);
   }
}
