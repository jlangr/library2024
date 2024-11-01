package domain.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.HoldingStore;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatalogTest {
   private final Catalog catalog = new Catalog();
   private final HoldingBuilder holdingBuilder = new HoldingBuilder();

   @BeforeEach
   void initialize() {
      HoldingStore.deleteAll();
   }

   @Test
   void isEmptyOnCreation() {
      assertEquals(0, catalog.size());
   }

   @Test
   void incrementsSizeWhenMaterialAdded() {
      catalog.add(holdingBuilder.create());

      assertEquals(1, catalog.size());
   }

   @Test
   void answersEmptyForNonexistentMaterial() {
      assertTrue(catalog.findAll("nonexistentid").isEmpty());
   }

   @Test
   void findAllReturnsListOfHoldings() {
      var classification = "123";
      var barcode = addHoldingWithClassification(classification);
      var barcode2 = addHoldingWithClassification(classification);

      var holdings = catalog.findAll(classification);

      var retrieved1 = catalog.find(barcode);
      var retrieved2 = catalog.find(barcode2);
      assertEquals(List.of(retrieved1, retrieved2), holdings);
   }

   private String addHoldingWithClassification(String classification) {
      var holding = holdingBuilder.withClassification(classification).create();
      return catalog.add(holding);
   }

   @Test
   void findAllReturnsOnlyHoldingsWithMatchingClassification() {
      var barcode1 = addHoldingWithClassification("123");
      addHoldingWithClassification("456");

      var retrieved = catalog.findAll("123");

      assertEquals(1, retrieved.size());
      assertEquals(barcode1, retrieved.get(0).getBarcode());
   }

   @Test
   void retrievesHoldingUsingBarcode() {
      var holding = holdingBuilder.create();
      var barcode = catalog.add(holding);

      var retrieved = catalog.find(barcode);

      assertEquals(holding, retrieved);
   }

   @Test
   void incrementsCopyNumberWhenSameClassificationExists() {
      var holding = holdingBuilder.create();
      catalog.add(holding);
      var barcode = catalog.add(holding);

      var retrieved = catalog.find(barcode);

      assertEquals(2, retrieved.getCopyNumber());
   }

   @Test
   void supportsIteration() {
      var barcode1 = addHoldingWithClassification("1");
      var barcode2 = addHoldingWithClassification("2");

      var results = new ArrayList<>();
      for (var holding : catalog)
         results.add(holding.getBarcode());

      assertEquals(List.of(barcode1, barcode2), results);
   }
}