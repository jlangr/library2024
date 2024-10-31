package api.scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

public abstract class ScanStationStateTestBase extends MockedScannerSubsystemFields {
   @BeforeEach
   void injectStateObject() {
      state = createStateObject();
      scanner.setCurrentState(state);
   }

   abstract protected ScanStationState createStateObject();

   protected void assertMessageDisplayed(String text) {
      verify(display).showMessage(text);
   }

   protected void assertStateUnchanged() {
      assertSame(scanner.getCurrentState(), state);
   }

   protected void assertCurrentState(Class<?> expectedState) {
      assertEquals(expectedState, scanner.getCurrentState().getClass());
   }

   @Test
   void toStringSpecifiesStateName() {
      var className = state.getClass().getSimpleName();
      assertEquals("state: " + className, state.toString());
   }
}
