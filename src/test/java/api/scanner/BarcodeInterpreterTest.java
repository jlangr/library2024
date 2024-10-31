package api.scanner;

import org.junit.jupiter.api.Test;

import static api.scanner.BarcodeType.*;
import static org.junit.Assert.assertEquals;

class BarcodeInterpreterTest {
   @Test
   void returnsHoldingTypeWhenBarcodeContainsColon() {
      assertEquals(HOLDING, BarcodeInterpreter.typeOf("123:1"));
   }

   @Test
   void returnsBranchTypeWhenStartsWithB() {
      assertEquals(BRANCH, BarcodeInterpreter.typeOf("b123"));
      assertEquals(BRANCH, BarcodeInterpreter.typeOf("B123"));
   }

   @Test
   void returnsInventoryTypeWhenStartsWithI() {
      assertEquals(INVENTORY, BarcodeInterpreter.typeOf("i111"));
      assertEquals(INVENTORY, BarcodeInterpreter.typeOf("I111"));
   }

   @Test
   void returnsPatronTypeWhenStartsWithP() {
      assertEquals(PATRON, BarcodeInterpreter.typeOf("p123"));
   }

   @Test
   void returnsUnrecognizedTypeWhenOther() {
      assertEquals(UNRECOGNIZED, BarcodeInterpreter.typeOf("123"));
   }
}
