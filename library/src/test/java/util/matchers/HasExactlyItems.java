package util.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.List;

public class HasExactlyItems<T> extends TypeSafeMatcher<List<T>> {
    private List<T> rhsItems;

    public HasExactlyItems(List<T> rhsItems) {
        this.rhsItems = rhsItems;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("exactly items: " + rhsItems);
    }

    @Override
    protected boolean matchesSafely(List<T> lhsItems) {
        return lhsItems.equals(rhsItems);
    }

    @SafeVarargs
    @Factory
    public static <T> Matcher<List<T>> hasExactlyItems(T... items) {
        return new HasExactlyItems<T>(Arrays.asList(items));
    }
}