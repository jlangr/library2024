package api.scanner;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class BarcodeInterpreterTest {
    @Test
    public void returnsHoldingTypeWhenBarcodeContainsColon() {
        assertThat(BarcodeInterpreter.typeOf("123:1"), equalTo(BarcodeType.HOLDING));
    }

    @Test
    public void returnsBranchTypeWhenStartsWithB() {
        assertThat(BarcodeInterpreter.typeOf("b123"), equalTo(BarcodeType.BRANCH));
        assertThat(BarcodeInterpreter.typeOf("B123"), equalTo(BarcodeType.BRANCH));
    }

    @Test
    public void returnsInventoryTypeWhenStartsWithI() {
        assertThat(BarcodeInterpreter.typeOf("i111"), equalTo(BarcodeType.INVENTORY));
        assertThat(BarcodeInterpreter.typeOf("I111"), equalTo(BarcodeType.INVENTORY));
    }

    @Test
    public void returnsPatronTypeWhenStartsWithP() {
        assertThat(BarcodeInterpreter.typeOf("p123"), equalTo(BarcodeType.PATRON));
    }

    @Test
    public void returnsUnrecognizedTypeWhenOther() {
        assertThat(BarcodeInterpreter.typeOf("123"), equalTo(BarcodeType.UNRECOGNIZED));
    }
}
