package domain.core;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HoldingBarcodeTest {
   @Test
   void answersClassificationAndCopy() {
      var holdingBarcode = new HoldingBarcode("ABC:42");

      assertEquals("ABC", holdingBarcode.getClassification());
      assertEquals(42, holdingBarcode.getCopyNumber());
   }
}