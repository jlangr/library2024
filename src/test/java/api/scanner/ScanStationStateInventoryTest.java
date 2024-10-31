package api.scanner;

import domain.core.Branch;
import org.junit.jupiter.api.Test;

import static api.scanner.ScanStationStateInventory.MSG_COMPLETE_INVENTORY_FIRST;
import static api.scanner.ScanStationStateInventory.MSG_SCANNED_HOLDING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScanStationStateInventoryTest extends ScanStationStateTestBase {
   @Override
   protected ScanStationStateInventory createStateObject() {
      return new ScanStationStateInventory(scanner);
   }

   @Test
   void displaysWarningWhenInventoryCardScanned() {
      state.scanInventoryCard();

      assertStateRetainedWithMessage(MSG_COMPLETE_INVENTORY_FIRST);
   }

   private void assertStateRetainedWithMessage(String message) {
      assertMessageDisplayed(message);
      assertStateUnchanged();
   }

   @Test
   void changesBranchWhenBranchIdScanned() {
      when(branchService.find("b222")).thenReturn(new Branch("b222", "West"));

      state.scanBranchId("b222");

      assertEquals("b222", scanner.getBranchId());
      assertMessageDisplayed(String.format(ScanStation.MSG_BRANCH_SET_TO, "West"));
      assertStateUnchanged();
   }

   @Test
   void addsNewHoldingToLibraryWhenSourceIdScanned() {
      String sourceId = "1234567890123";
      scanner.setBranch(new Branch("b123", ""));

      state.scanHolding(sourceId);

      verify(holdingService).add(sourceId, "b123");
      assertStateUnchanged();
      assertMessageDisplayed(String.format(MSG_SCANNED_HOLDING, sourceId));
   }

   @Test
   void displaysWarningWhenPatronCardScanned() {
      state.scanPatron("p123");

      assertStateRetainedWithMessage(MSG_COMPLETE_INVENTORY_FIRST);
   }

   @Test
   void changesStateToReturnsWhenCompletePressed() {
      state.pressComplete();

      assertTrue(scanner.getCurrentState() instanceof ScanStationStateReturns);
   }
}
