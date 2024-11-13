package domain.core;

import static com.loc.material.api.MaterialType.*;

public class LateStrategyFactory {
   LateStrategy create(int format) {
      return switch (format) {
         case BOOK, NEW_RELEASE_DVD -> new DaysLateStrategy();
         case AUDIO_CASSETTE, VINYL_RECORDING, MICRO_FICHE, AUDIO_CD, SOFTWARE_CD, DVD, BLU_RAY, VIDEO_CASSETTE -> new ConstrainedAmountLateStrategy();
         default -> (LateStrategy)(fine, daysLate) -> 0;
      };
   }
}
