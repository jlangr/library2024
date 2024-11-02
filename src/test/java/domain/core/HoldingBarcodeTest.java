package domain.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HoldingBarcodeTest {
   @Test
   void answersClassificationAndCopy() {
      var holdingBarcode = new HoldingBarcode("ABC:42");

      assertEquals("ABC", holdingBarcode.getClassification());
      assertEquals(42, holdingBarcode.getCopyNumber());
   }

   @Test
   void answersBarcodeWhenConstructedFromClassificationAndCopy() {
      var holdingBarcode = new HoldingBarcode("ABC", 42);

      assertEquals("ABC:42", holdingBarcode.barcode());
   }
}