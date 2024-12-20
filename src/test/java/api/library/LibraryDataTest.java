package api.library;

import com.loc.material.api.ClassificationApi;
import com.loc.material.api.Material;
import domain.core.Branch;
import domain.core.ClassificationApiFactory;
import domain.core.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.HoldingStore;
import persistence.PatronStore;
import persistence.BranchStore;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LibraryDataTest {
   final PatronService patronService = new PatronService();
   final HoldingService holdingService = new HoldingService();
   final BranchService branchService = new BranchService();
   ClassificationApi classificationApi;

   @BeforeEach
   void setUpClassificationService() {
      classificationApi = mock(ClassificationApi.class);
      ClassificationApiFactory.setService(classificationApi);
   }

   @Test
   void deleteAllRemovesAllData() {
      patronService.patronAccess.add(new Patron("", "1"));
      branchService.add("2");
      var material = new Material("3", "", "", "", "");
      when(classificationApi.retrieveMaterial("3")).thenReturn(material);
      holdingService.add(material.getSourceId(), Branch.CHECKED_OUT.getScanCode());

      LibraryData.deleteAll();

      assertTrue(PatronStore.isEmpty());
      assertTrue(HoldingStore.isEmpty());
      assertTrue(BranchStore.isEmpty());
   }
}
