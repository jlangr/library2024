package persistence;

import domain.core.Holding;
import domain.core.HoldingBuilder;
import domain.core.Patron;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static testutil.CollectionsUtil.soleElement;
import static util.matchers.HasExactlyItemsInAnyOrder.hasExactlyItemsInAnyOrder;

public class PatronStoreTest {
    private PatronStore store;
    private static Patron patronSmith = new Patron("p1", "joe");

    @Before
    public void initialize() {
        PatronStore.deleteAll();
        store = new PatronStore();
    }

    @Test
    public void persistsAddedPatron() {
        store.add(patronSmith);

        Collection<Patron> patrons = store.getAll();

        assertThat(soleElement(patrons), equalTo(patronSmith));
    }

    @Test
    public void assignsId() {
        Patron patron = new Patron("name");

        store.add(patron);

        assertTrue(patron.getId().startsWith("p"));
    }

    @Test
    public void assignedIdIsUnique() {
        Patron patronA = new Patron("a");
        Patron patronB = new Patron("b");

        store.add(patronA);
        store.add(patronB);

        assertThat(patronA.getId(), not(equalTo(patronB.getId())));
    }

    @Test
    public void doesNotOverwriteExistingId() {
        Patron patron = new Patron("p12345", "");

        store.add(patron);

        assertThat(store.find("p12345").getId(), equalTo("p12345"));
    }

    @Test
    public void returnsPersistedPatronAsNewInstance() {
        store.add(patronSmith);

        Patron found = store.find(patronSmith.getId());

        assertThat(found, not(sameInstance(patronSmith)));
    }

    @Test
    public void storesHoldingsAddedToPatron() {
        Holding holding = new HoldingBuilder().create();
        store.add(patronSmith);
        store.addHoldingToPatron(patronSmith, holding);

        Patron patron = store.find(patronSmith.getId());

        assertThat(patron.holdingMap().holdings(), hasExactlyItemsInAnyOrder(holding));
    }

    @Test(expected = PatronNotFoundException.class)
    public void throwsOnAddingHoldingToNonexistentPatron() {
        store.addHoldingToPatron(patronSmith, new HoldingBuilder().create());
    }

    @Test
    public void findsPersistedPatronById() {
        store.add(patronSmith);

        Patron found = store.find(patronSmith.getId());

        assertThat(found.getName(), equalTo(patronSmith.getName()));
    }
}
