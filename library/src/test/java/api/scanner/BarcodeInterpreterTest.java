package api.scanner;

import org.junit.Test;

import static api.scanner.BarcodeType.*;
import static org.junit.Assert.assertEquals;

public class BarcodeInterpreterTest {
    @Test
    public void returnsHoldingTypeWhenBarcodeContainsColon() {
        assertEquals(HOLDING, BarcodeInterpreter.typeOf("123:1"));
    }

    @Test
    public void returnsBranchTypeWhenStartsWithB() {
        assertEquals(BRANCH, BarcodeInterpreter.typeOf("b123"));
        assertEquals(BRANCH, BarcodeInterpreter.typeOf("B123"));
    }

    @Test
    public void returnsInventoryTypeWhenStartsWithI() {
        assertEquals(INVENTORY, BarcodeInterpreter.typeOf("i111"));
        assertEquals(INVENTORY, BarcodeInterpreter.typeOf("I111"));
    }

    @Test
    public void returnsPatronTypeWhenStartsWithP() {
        assertEquals(PATRON, BarcodeInterpreter.typeOf("p123"));
    }

    @Test
    public void returnsUnrecognizedTypeWhenOther() {
        assertEquals(UNRECOGNIZED, BarcodeInterpreter.typeOf("123"));
    }
}
