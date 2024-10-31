package persistence;

import domain.core.Branch;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

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

      assertEquals(List.of(branch), branches);
   }

   @Test
   public void findsBranchByScanCode() {
      store.save(EAST_BRANCH);

      var retrieved = store.findByScanCode(EAST_BRANCH.getScanCode());

      assertEquals(EAST_BRANCH, retrieved);
   }

   @Test
   public void findsCheckedOutBranch() {
      assertSame(Branch.CHECKED_OUT, store.findByScanCode(Branch.CHECKED_OUT.getScanCode()));
   }

   @Test
   public void findsBranchByScanCodeReturnsNullWhenNotFound() {
      assertNull(store.findByScanCode(""));
   }
}