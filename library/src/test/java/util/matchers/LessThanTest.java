package util.matchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static util.matchers.LessThan.lessThan;

public class LessThanTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void passesWhenLessThan() {
        assertThat(5, lessThan(6));
    }

    @Test
    public void notPassesWhenEqualTo() {
        assertThat(6, not(lessThan(6)));
    }

    @Test
    public void notPassesWhenGreaterThan() {
        assertThat(7, not(lessThan(6)));
    }

    @Test
    public void passesWithDoubles() {
        assertThat(5.0, lessThan(6.0));
    }

    @Test
    public void failureMessageIsUseful() {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("A number less than 4");

        assertThat(6, lessThan(4));
    }
}
