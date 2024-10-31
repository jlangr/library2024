package persistence;

import domain.core.Holding;
import domain.core.HoldingBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static testutil.CollectionsUtil.soleElement;

class HoldingStoreTest {
   private HoldingStore store;
   private Holding savedHolding;

   @BeforeEach
   void setUp() {
      HoldingStore.deleteAll();
      store = new HoldingStore();
      savedHolding = new HoldingBuilder().create();
      store.save(savedHolding);
   }

   @Test
   void returnsAddedHoldings() {
      var retrieved = store.findByClassification(classification(savedHolding));

      assertEquals(savedHolding.getMaterial(), soleElement(retrieved).getMaterial());
   }

   private String classification(Holding holding) {
      return holding.getMaterial().getClassification();
   }

   @Test
   void returnsNewInstanceOnRetrieval() {
      store = new HoldingStore();

      var retrieved = store.findByClassification(classification(savedHolding));

      assertNotSame(savedHolding, soleElement(retrieved));
   }

   @Test
   void findByBarCodeReturnsMatchingHolding() {
      var holding = store.findByBarcode(savedHolding.getBarcode());

      assertEquals(savedHolding.getBarcode(), holding.getBarcode());
   }

   @Test
   void findBarCodeNotFound() {
      assertNull(store.findByBarcode("nonexistent barcode:1"));
   }
}