package api.library;

import com.loc.material.api.ClassificationApi;
import com.loc.material.api.Material;
import domain.core.ClassificationApiFactory;
import domain.core.Holding;
import domain.core.HoldingMap;
import domain.core.HoldingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HoldingService_WithBranchCreatedTest {
   final HoldingService service = new HoldingService();
   final ClassificationApi classificationApi = mock(ClassificationApi.class);
   String branchScanCode;

   @BeforeEach
   void initialize() {
      LibraryData.deleteAll();
      ClassificationApiFactory.setService(classificationApi);
      branchScanCode = new BranchService().add("a branch name");
   }

   String addHolding() {
      Material material = new Material("123", "", "", "", "");
      when(classificationApi.retrieveMaterial("123")).thenReturn(material);
      return service.add("123", branchScanCode);
   }

   @Test
   void returnsEntireInventoryOfHoldings() {
      for (int i = 0; i < 3; i++)
         addHolding();

      var holdings = service.allHoldings();

      assertEquals(3, holdings.size());
   }

   @Test
   void storesNewHoldingAtBranch() {
      var barcode = addHolding();

      assertEquals(branchScanCode, service.find(barcode).getBranch().getScanCode());
   }

   @Test
   void findByBarCodeReturnsNullWhenNotFound() {
      assertNull(service.find("999:1"));
   }

   @Test
   void updatesBranchOnHoldingTransfer() {
      var barcode = addHolding();

      service.transfer(barcode, branchScanCode);

      var holding = service.find(barcode);
      assertEquals(branchScanCode, holding.getBranch().getScanCode());
   }

   // TODO
//   @Test(expected = HoldingNotFoundException.class)
//   void throwsOnTransferOfNonexistentHolding() {
//      service.transfer("XXX:1", branchScanCode);
//   }

   @Test
   void holdingIsAvailableWhenNotCheckedOut() {
      var barcode = addHolding();

      assertTrue(service.isAvailable(barcode));
   }

   // TODO
//   @Test(expected = HoldingNotFoundException.class)
//   void availabilityCheckThrowsWhenHoldingNotFound() {
//      service.isAvailable("345:1");
//   }
//
//   @Test(expected = HoldingNotFoundException.class)
//   void checkinThrowsWhenHoldingIdNotFound() {
//      service.checkIn("999:1", new Date(), branchScanCode);
//   }
}