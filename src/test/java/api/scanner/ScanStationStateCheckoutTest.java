package api.scanner;

import domain.core.Holding;
import domain.core.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DateUtil;
import util.TimestampSource;

import static api.scanner.ScanStationStateCheckout.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ScanStationStateCheckoutTest extends ScanStationStateTestBase {
   public static final String PATRON_JOE_ID = "p111";
   public static final Patron PATRON_JOE = new Patron(PATRON_JOE_ID, "Joe");

   private Holding holdingWithAvailability;
   private Holding holdingWithUnavailability;

   @Override
   protected ScanStationState createStateObject() {
      return new ScanStationStateCheckout(scanner);
   }

   @BeforeEach
   void initialize() {
      holdingWithAvailability = createHoldingWithAvailability(true);
      holdingWithUnavailability = createHoldingWithAvailability(false);
   }

   private Holding createHoldingWithAvailability(boolean isAvailable) {
      var holding = mock(Holding.class);
      when(holding.isAvailable()).thenReturn(isAvailable);
      return holding;
   }

   @BeforeEach
   void addPatronJoe() {
      when(patronService.find(PATRON_JOE_ID)).thenReturn(PATRON_JOE);
   }

   @Test
   void displaysWarningWhenPatronCardScanned() {
      state.scanPatron(PATRON_JOE_ID);

      assertMessageDisplayed(MSG_COMPLETE_CHECKOUT_FIRST);
      assertStateUnchanged();
   }

   @Test
   void displaysWarningWhenInventoryCardScanned() {
      state.scanInventoryCard();

      assertMessageDisplayed(MSG_COMPLETE_CHECKOUT_FIRST);
      assertStateUnchanged();
   }

   @Test
   void displaysMessageIfNoHoldingExists() {
      scanner.scanPatronId(PATRON_JOE_ID);
      when(holdingService.find("123:1")).thenReturn(null);

      state.scanHolding("123:1");

      assertMessageDisplayed(String.format(MSG_INVALID_HOLDING_ID, "123:1"));
      assertStateUnchanged();
   }

   @Test
   void checksOutHoldingWhenHoldingIdScanned() {
      var someDate = DateUtil.create(2025, 12, 10);
      scanner.scanPatronId(PATRON_JOE_ID);
      when(holdingService.find("123:1")).thenReturn(holdingWithAvailability);
      TimestampSource.queueNextTime(someDate);

      state.scanHolding("123:1");

      verify(holdingService).checkOut(PATRON_JOE_ID, "123:1", someDate);
      assertMessageDisplayed(String.format(MSG_CHECKED_OUT, "123:1"));
      assertStateUnchanged();
   }

   @Test
   void displaysMessageWhenBookCheckedOutTwice() {
      scanner.scanPatronId(PATRON_JOE_ID);
      when(holdingService.find("123:1")).thenReturn(holdingWithAvailability);
      state.scanHolding("123:1");
      when(holdingService.find("123:1")).thenReturn(holdingWithUnavailability);

      state.scanHolding("123:1");

      assertMessageDisplayed(String.format(ScanStationStateCheckout.MSG_ALREADY_CHECKED_OUT, "123:1"));
      assertStateUnchanged();
      assertTrue(TimestampSource.isExhausted());
   }

   @Test
   void changesStateToReturnsWhenCompletePressed() {
      scanner.scanPatronId(PATRON_JOE_ID);

      state.pressComplete();

      assertCurrentState(ScanStationStateReturns.class);
      assertMessageDisplayed(String.format(MSG_COMPLETED_CHECKOUT, PATRON_JOE_ID));
   }

   @Test
   void displaysWarningWhenBranchIdScanned() {
      state.scanBranchId("b123");

      assertStateUnchanged();
      assertMessageDisplayed(MSG_COMPLETE_CHECKOUT_FIRST);
   }
}