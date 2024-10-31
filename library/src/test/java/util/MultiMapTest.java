package util;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MultiMapTest {
   private MultiMap<String, String> map;

   @Before
   public void initialize() {
      map = new MultiMap<>();
   }

   @Test
   public void isEmptyOnCreation() {
      assertEquals(0, map.size());
      assertEquals(0, map.valuesSize());
   }

   @Test
   public void returnsValuesAssociatedWithKeyAsList() {
      map.put("a", "alpha");

      var values = map.get("a");

      assertEquals(List.of("alpha"), values);
   }

   @Test
   public void incrementsSizeForMultipleKeys() {
      map.put("a", "");
      map.put("b", "");

      assertEquals(2, map.size());
   }

   @Test
   public void allowsMultipleElementsSameKey() {
      map.put("a", "alpha1");
      map.put("a", "alpha2");

      var values = map.get("a");

      assertEquals(List.of("alpha1", "alpha2"), sorted(values));
   }

   @Test
   public void valuesSizeRepresentsTotalCountOfValues() {
      map.put("a", "alpha");
      map.put("b", "beta1");
      map.put("b", "beta2");

      var size = map.valuesSize();

      assertEquals(3, size);
   }

   @Test
   public void returnsOnlyValuesAssociatedWithKey() {
      map.put("a", "alpha");
      map.put("b", "beta");

      var values = map.get("b");

      assertEquals(List.of("beta"), values);
   }

   @Test(expected = NullPointerException.class)
   public void throwsOnPutOfNullKey() {
      map.put(null, "");
   }

   @Test
   public void clearEliminatesAllData() {
      map.put("a", "");
      map.put("b", "");

      map.clear();

      assertEquals(0, map.size());
      assertEquals(0, map.valuesSize());
   }

   @Test
   public void returnsCombinedCollectionOfAllValues() {
      map.put("a", "alpha");
      map.put("b", "beta");

      var values = map.values();

      assertEquals(List.of("alpha", "beta"), sorted(values));
   }

   private List<String> sorted(Collection<String> items) {
      return items.stream().sorted().toList();
   }
}