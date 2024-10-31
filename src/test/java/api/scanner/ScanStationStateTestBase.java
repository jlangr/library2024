package api.scanner;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public abstract class ScanStationStateTestBase extends MockedScannerSubsystemFields {

    @Before
    public void injectStateObject() {
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
        assertThat(scanner.getCurrentState(), is(instanceOf(expectedState)));
    }

    @Test
    public void toStringSpecifiesStateName() {
        String className = state.getClass().getSimpleName();
        assertThat(state.toString(), is("state: " + className));
    }
}
