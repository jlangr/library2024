package api.scanner;

import domain.core.Branch;
import org.junit.jupiter.api.Test;

import static api.scanner.ScanStationStateWaiting.MSG_SCAN_BRANCH_ID_FIRST;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ScanStationStateWaitingTest extends ScanStationStateTestBase {
   @Override
   protected ScanStationState createStateObject() {
      return new ScanStationStateWaiting(scanner);
   }

   @Test
   void storesBranchIdWhenBranchCardScanned() {
      Branch westBranch = new Branch("b123", "west");
      when(branchService.find("b123")).thenReturn(westBranch);
      scanner.setBranch(new Branch("b999", "other"));

      state.scanBranchId("b123");

      assertCurrentState(ScanStationStateReturns.class);
      assertMessageDisplayed(String.format(ScanStation.MSG_BRANCH_SET_TO, westBranch.getName()));
      assertEquals("b123", scanner.getBranchId());
   }

   @Test
   void displaysWarningMessageOnWhenHoldingScanned() {
      state.scanHolding("");

      assertStateUnchanged();
      assertMessageDisplayed(MSG_SCAN_BRANCH_ID_FIRST);
   }

   @Test
   void displaysWarningMessageOnWhenInventoryCardScanned() {
      state.scanInventoryCard();

      assertStateUnchanged();
      assertMessageDisplayed(MSG_SCAN_BRANCH_ID_FIRST);
   }

   @Test
   void displaysWarningMessageOnWhenPatronScanned() {
      state.scanPatron("");

      assertStateUnchanged();
      assertMessageDisplayed(MSG_SCAN_BRANCH_ID_FIRST);
   }

   @Test
   void displaysWarningMessageOnWhenCompletePressed() {
      state.pressComplete();

      assertStateUnchanged();
      assertMessageDisplayed(MSG_SCAN_BRANCH_ID_FIRST);
   }
}