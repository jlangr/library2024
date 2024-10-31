package api.library;

import com.loc.material.api.ClassificationApi;
import com.loc.material.api.Material;
import domain.core.BranchNotFoundException;
import domain.core.ClassificationApiFactory;
import domain.core.Holding;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HoldingServiceTest {
   @Rule
   public final ExpectedException expectedEx = ExpectedException.none();

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

      assertEquals(isbn, holding.getMaterial().getSourceId());
   }

   // TODO update
   @Test
   void throwsExceptionWhenBranchNotFound() {
      expectedEx.expect(BranchNotFoundException.class);
      expectedEx.expectMessage("Branch not found: badBranchId");

      service.add("", "badBranchId");
   }
}