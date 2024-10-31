package api.library;

import domain.core.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// TODO JUnit 5

class BranchServiceTest {
   private BranchService service;

   @BeforeEach
   void initialize() {
      service = new BranchService();
      LibraryData.deleteAll();
   }

   @Test
   void findsByScanCode() {
      service.add("name", "b2");

      var branch = service.find("b2");

      assertEquals("name", branch.getName());
   }

//    @Test(expected = DuplicateBranchCodeException.class)
//    void rejectsDuplicateScanCode() {
//        service.add("", "b559");
//        service.add("", "b559");
//    }
//
//    @Test(expected = InvalidBranchCodeException.class)
//    void rejectsScanCodeNotStartingWithB() {
//        service.add("", "c2234");
//    }
   // TODO

   @Test
   void answersGeneratedId() {
      assertTrue(service.add("").startsWith("b"));
   }

   @Test
   void findsBranchMatchingScanCode() {
      var scanCode = service.add("a branch");

      var branch = service.find(scanCode);

      assertEquals("a branch", branch.getName());
      assertEquals(scanCode, branch.getScanCode());
   }

   @Test
   void returnsListOfAllPersistedBranches() {
      var eastScanCode = service.add("e");
      var westScanCode = service.add("w");

      var all = service.allBranches();

      var scanCodes = all.stream().map(Branch::getScanCode).toList();
      assertEquals(List.of(eastScanCode, westScanCode), scanCodes);
   }
}
