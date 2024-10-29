package persistence;

import domain.core.Branch;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static util.matchers.HasExactlyItemsInAnyOrder.hasExactlyItemsInAnyOrder;

public class BranchStoreTest {
    private BranchStore store;
    private static final Branch EAST_BRANCH = new Branch("East");

    @Before
    public void initialize() {
        BranchStore.deleteAll();
        store = new BranchStore();
    }

    @Test
    public void assignsIdToBranch() {
        Branch branch = new Branch("name");

        store.save(branch);

        assertTrue(branch.getScanCode().startsWith("b"));
    }

    @Test
    public void assignedIdIsUnique() {
        var branchA = new Branch("a");
        store.save(branchA);
        var branchB = new Branch("b");

        store.save(branchB);

        assertNotEquals(branchB.getScanCode(), branchA.getScanCode());
    }

    @Test
    public void doesNotChangeIdIfAlreadyAssigned() {
        var branch = new Branch("b1964", "");

        store.save(branch);

        assertEquals("b1964", branch.getScanCode());
    }

    @Test
    public void returnsSavedBranches() {
        store.save(new Branch("name"));

        var retrieved = store.findByName("name");

        assertEquals("name", retrieved.getName());
    }

    @Test
    public void returnsNewInstanceOfPersistedBranch() {
        var branch = new Branch("name");
        store.save(branch);
        store = new BranchStore();

        var retrieved = store.findByName("name");

        assertNotSame(branch, sameInstance(retrieved));
    }

    @Test
    public void returnsListOfAllBranches() {
        var branch = new Branch("b123", "");
        store.save(branch);

        var branches = store.getAll();

        assertThat(branches, hasExactlyItemsInAnyOrder(branch));
    }

    @Test
    public void findsBranchByScanCode() {
        store.save(EAST_BRANCH);

        Branch retrieved = store.findByScanCode(EAST_BRANCH.getScanCode());

        assertThat(retrieved, is(EAST_BRANCH));
    }

    @Test
    public void findsCheckedOutBranch() {
        assertThat(store.findByScanCode(Branch.CHECKED_OUT.getScanCode()),
                is(sameInstance(Branch.CHECKED_OUT)));
    }

    @Test
    public void findsBranchByScanCodeReturnsNullWhenNotFound() {
        assertThat(store.findByScanCode(""), nullValue());
    }
}