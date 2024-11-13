package api.library;

import com.loc.material.api.ClassificationApi;
import com.loc.material.api.Material;
import domain.core.BranchNotFoundException;
import domain.core.ClassificationApiFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HoldingServiceTest {
   final HoldingService service = new HoldingService();
   final ClassificationApi classificationApi = mock(ClassificationApi.class);
   String branchScanCode;

   @BeforeEach
   void initialize() {
      LibraryData.deleteAll();
      ClassificationApiFactory.setService(classificationApi);
      branchScanCode = new BranchService().add("");
   }

   @Test
   void usesClassificationServiceToRetrieveBookDetails() {
      var isbn = "9780141439594";
      var material = new Material(isbn, "", "", "", "");
      when(classificationApi.retrieveMaterial(isbn)).thenReturn(material);
      var barcode = service.add(isbn, branchScanCode);

      var holding = service.find(barcode);

      assertEquals(isbn, holding.getMaterial().sourceId());
   }

   @Test
   void throwsExceptionWhenBranchNotFound() {
      var thrown = assertThrows(BranchNotFoundException.class,
         () -> service.add("", "badBranchId"));
      assertEquals("Branch not found: badBranchId", thrown.getMessage());
   }
}