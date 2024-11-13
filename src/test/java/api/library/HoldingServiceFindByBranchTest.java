package api.library;

import com.loc.material.api.ClassificationApi;
import com.loc.material.api.Material;
import domain.core.ClassificationApiFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HoldingServiceFindByBranchTest {
   final HoldingService service = new HoldingService();
   final ClassificationApi classificationApi = mock(ClassificationApi.class);
   final BranchService branchService = new BranchService();

   @BeforeEach
   void initialize() {
      LibraryData.deleteAll();
      ClassificationApiFactory.setService(classificationApi);
   }

   void addHolding(String sourceId, String branchScanCode) {
      when(classificationApi.retrieveMaterial(sourceId)).thenReturn(new Material(sourceId, "", "", "", ""));
      service.add(sourceId, branchScanCode);
   }

   @Test
   void returnsOnlyHoldingsAtBranch() {
      var branchAScanCode = branchService.add("branch A");
      var branchBScanCode = new BranchService().add("branch B");
      addHolding("123", branchAScanCode);
      addHolding("456", branchAScanCode);
      addHolding("999", branchBScanCode);

      var holdings = service.findByBranch(branchAScanCode);

      var holdingSourceIds = holdings.stream()
         .map(h -> h.getMaterial().sourceId())
         .collect(Collectors.toList());
      assertEquals(List.of("123", "456"), holdingSourceIds);
   }
}
