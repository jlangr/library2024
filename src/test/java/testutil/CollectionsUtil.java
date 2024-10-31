package testutil;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectionsUtil {
    static final String NO_ELEMENTS = "no elements";
    static final String MORE_THAN_ONE_ELEMENT = "more than one element";

    public static <T> T soleElement(Collection<T> collection) {
        Iterator<T> it = collection.iterator();
        assertTrue(it.hasNext(), NO_ELEMENTS);
        T sole = it.next();
        assertFalse(it.hasNext(), MORE_THAN_ONE_ELEMENT);
        return sole;
    }
}