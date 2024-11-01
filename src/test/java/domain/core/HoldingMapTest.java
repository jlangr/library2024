package domain.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HoldingMapTest {
   private HoldingMap map;
   private Holding holding;

   @BeforeEach
   void initialize() {
      map = new HoldingMap();
      holding = new HoldingBuilder().create();
   }

   @Test
   void isEmptyWhenCreated() {
      assertTrue(map.isEmpty());
   }

   @Test
   void hasSizeZeroWhenCreated() {
      assertEquals(0, map.size());
   }

   @Test
   void containsFailsWhenHoldingNotFound() {
      assertFalse(map.contains(holding));
   }

   @Test
   void containsAddedHolding() {
      map.add(holding);

      assertTrue(map.contains(holding));
   }

   @Test
   void sizeIncrementedOnAddingHolding() {
      map.add(holding);

      assertEquals(1, map.size());
   }

   @Test
   void retrievesHoldingByBarcode() {
      map.add(holding);

      var retrieved = map.get(holding.getBarcode());

      assertSame(retrieved, holding);
   }

   @Test
   void returnsAllHoldings() {
      map.add(new HoldingBuilder().withBarcode("a:1").create());
      map.add(new HoldingBuilder().withBarcode("b:1").create());

      var holdings = map.holdings().stream().map(Holding::getBarcode).toList();

      assertEquals(List.of("a:1", "b:1"), holdings.stream().sorted().toList());
   }

   @Test
   void removeHolding() {
      map.add(holding);

      map.remove(holding);

      assertFalse(map.contains(holding));
   }

   @Test
   void removeHoldingDecrementsSize() {
      map.add(holding);

      map.remove(holding);

      assertEquals(0, map.size());
   }

   @Test
   void supportsIteration() {
      map.add(new HoldingBuilder().withBarcode("a:1").create());
      map.add(new HoldingBuilder().withBarcode("b:1").create());

      var barcodes = new ArrayList<String>();
      for (var eachHolding : map)
         barcodes.add(eachHolding.getBarcode());

      assertEquals(List.of("a:1", "b:1"), barcodes.stream().sorted().toList());
   }
}