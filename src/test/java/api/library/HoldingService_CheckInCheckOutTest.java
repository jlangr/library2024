package api.library;

import com.loc.material.api.ClassificationApi;
import com.loc.material.api.Material;
import com.loc.material.api.MaterialType;
import domain.core.ClassificationApiFactory;
import domain.core.HoldingAlreadyCheckedOutException;
import domain.core.HoldingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DateUtil;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HoldingService_CheckInCheckOutTest {
   static final Date TODAY = new Date();
   final HoldingService service = new HoldingService();
   final PatronService patronService = new PatronService();
   final ClassificationApi classificationApi = mock(ClassificationApi.class);
   String patronId;
   String branchScanCode;
   String bookHoldingBarcode;

   @BeforeEach
   void initialize() {
      LibraryData.deleteAll();
      ClassificationApiFactory.setService(classificationApi);
      branchScanCode = new BranchService().add("a branch name");
      patronId = patronService.add("joe");
      bookHoldingBarcode = addBookHolding();
   }

   String addBookHolding() {
      var material = new Material("123", "", "", "", MaterialType.BOOK, "");
      when(classificationApi.retrieveMaterial("123")).thenReturn(material);
      return service.add("123", branchScanCode);
   }

   @Test
   void holdingMadeUnavailableOnCheckout() {
      service.checkOut(patronId, bookHoldingBarcode, new Date());

      assertFalse(service.isAvailable(bookHoldingBarcode));
   }

   @Test
   void checkoutThrowsWhenHoldingIdNotFound() {
      assertThrows(HoldingNotFoundException.class, () ->
         service.checkOut(patronId, "999:1", TODAY));
   }

   @Test
   void checkoutThrowsWhenUnavailable() {
      service.checkOut(patronId, bookHoldingBarcode, TODAY);

      assertThrows(HoldingAlreadyCheckedOutException.class, () ->
         service.checkOut(patronId, bookHoldingBarcode, TODAY));
   }

   @Test
   void updatesPatronWithHoldingOnCheckout() {
      service.checkOut(patronId, bookHoldingBarcode, new Date());

      var patronHoldings = patronService.find(patronId).holdingMap();

      assertEquals(List.of(service.find(bookHoldingBarcode)), patronHoldings.holdings());
   }

   @Test
   void returnsHoldingToBranchOnCheckIn() {
      service.checkOut(patronId, bookHoldingBarcode, new Date());

      service.checkIn(bookHoldingBarcode, DateUtil.tomorrow(), branchScanCode);

      var holding = service.find(bookHoldingBarcode);
      assertTrue(holding.isAvailable());
      assertEquals(branchScanCode, holding.getBranch().getScanCode());
   }

   @Test
   void removesHoldingFromPatronOnCheckIn() {
      service.checkOut(patronId, bookHoldingBarcode, new Date());

      service.checkIn(bookHoldingBarcode, DateUtil.tomorrow(), branchScanCode);

      assertTrue(patronService.find(patronId).holdingMap().isEmpty());
   }

   @Test
   void answersDueDate() {
      service.checkOut(patronId, bookHoldingBarcode, new Date());

      var due = service.dateDue(bookHoldingBarcode);

      var holding = service.find(bookHoldingBarcode);
      assertEquals(due, holding.dateDue());
   }

   @Test
   void checkinReturnsDaysLate() {
      service.checkOut(patronId, bookHoldingBarcode, new Date());
      var fiveDaysLate = DateUtil.addDays(service.dateDue(bookHoldingBarcode), 5);

      var daysLate = service.checkIn(bookHoldingBarcode, fiveDaysLate, branchScanCode);

      assertEquals(5, daysLate);
   }

   @Test
   void updatesFinesOnLateCheckIn() {
      service.checkOut(patronId, bookHoldingBarcode, new Date());
      var holding = service.find(bookHoldingBarcode);
      var oneDayLate = DateUtil.addDays(holding.dateDue(), 1);

      service.checkIn(bookHoldingBarcode, oneDayLate, branchScanCode);

      assertEquals(MaterialType.BOOK.getDailyFine(), patronService.find(patronId).fineBalance());
   }
}
