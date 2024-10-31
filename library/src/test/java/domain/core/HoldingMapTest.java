package domain.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HoldingMapTest {
   private HoldingMap map;
   private Holding holding;

   @Before
   public void initialize() {
      map = new HoldingMap();
      holding = new HoldingBuilder().create();
   }

   @Test
   public void isEmptyWhenCreated() {
      assertTrue(map.isEmpty());
   }

   @Test
   public void hasSizeZeroWhenCreated() {
      assertEquals(0, map.size());
   }

   @Test
   public void containsFailsWhenHoldingNotFound() {
      assertFalse(map.contains(holding));
   }

   @Test
   public void containsAddedHolding() {
      map.add(holding);

      assertTrue(map.contains(holding));
   }

   @Test
   public void sizeIncrementedOnAddingHolding() {
      map.add(holding);

      assertEquals(1, map.size());
   }

   @Test
   public void retrievesHoldingByBarcode() {
      map.add(holding);

      var retrieved = map.get(holding.getBarcode());

      assertSame(retrieved, holding);
   }

   @Test
   public void returnsAllHoldings() {
      map.add(new HoldingBuilder().withBarcode("a:1").create());
      map.add(new HoldingBuilder().withBarcode("b:1").create());

      var holdings = map.holdings().stream().map(Holding::getBarcode).toList();

      assertEquals(List.of("a:1", "b:1"), holdings.stream().sorted().toList());
   }

   @Test
   public void removeHolding() {
      map.add(holding);

      map.remove(holding);

      assertFalse(map.contains(holding));
   }

   @Test
   public void removeHoldingDecrementsSize() {
      map.add(holding);

      map.remove(holding);

      assertEquals(0, map.size());
   }

   @Test
   public void supportsIteration() {
      map.add(new HoldingBuilder().withBarcode("a:1").create());
      map.add(new HoldingBuilder().withBarcode("b:1").create());

      var barcodes = new ArrayList<String>();
      for (var holding : map)
         barcodes.add(holding.getBarcode());

      assertEquals(List.of("a:1", "b:1"), barcodes.stream().sorted().toList());
   }
}