package persistence;

import domain.core.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BranchStoreTest {
   private BranchStore store;
   private static final Branch EAST_BRANCH = new Branch("East");

   @BeforeEach
   void initialize() {
      BranchStore.deleteAll();
      store = new BranchStore();
   }

   @Test
   void assignsIdToBranch() {
      Branch branch = new Branch("name");

      store.save(branch);

      assertTrue(branch.getScanCode().startsWith("b"));
   }

   @Test
   void assignedIdIsUnique() {
      var branchA = new Branch("a");
      store.save(branchA);
      var branchB = new Branch("b");

      store.save(branchB);

      assertNotEquals(branchB.getScanCode(), branchA.getScanCode());
   }

   @Test
   void doesNotChangeIdIfAlreadyAssigned() {
      var branch = new Branch("b1964", "");

      store.save(branch);

      assertEquals("b1964", branch.getScanCode());
   }

   @Test
   void returnsSavedBranches() {
      store.save(new Branch("name"));

      var retrieved = store.findByName("name");

      assertEquals("name", retrieved.getName());
   }

   @Test
   void returnsNewInstanceOfPersistedBranch() {
      var branch = new Branch("name");
      store.save(branch);
      store = new BranchStore();

      var retrieved = store.findByName("name");

      assertNotSame(retrieved, branch);
   }

   @Test
   void returnsListOfAllBranches() {
      var branch = new Branch("b123", "");
      store.save(branch);

      var branches = store.getAll();

      assertEquals(List.of(branch), branches);
   }

   @Test
   void findsBranchByScanCode() {
      store.save(EAST_BRANCH);

      var retrieved = store.findByScanCode(EAST_BRANCH.getScanCode());

      assertEquals(EAST_BRANCH, retrieved);
   }

   @Test
   void findsCheckedOutBranch() {
      assertSame(Branch.CHECKED_OUT, store.findByScanCode(Branch.CHECKED_OUT.getScanCode()));
   }

   @Test
   void findsBranchByScanCodeReturnsNullWhenNotFound() {
      assertNull(store.findByScanCode(""));
   }
}