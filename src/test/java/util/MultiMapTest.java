package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MultiMapTest {
   private MultiMap<String, String> map;

   @BeforeEach
   void initialize() {
      map = new MultiMap<>();
   }

   @Test
   void isEmptyOnCreation() {
      assertEquals(0, map.size());
      assertEquals(0, map.valuesSize());
   }

   @Test
   void returnsValuesAssociatedWithKeyAsList() {
      map.put("a", "alpha");

      var values = map.get("a");

      assertEquals(List.of("alpha"), values);
   }

   @Test
   void incrementsSizeForMultipleKeys() {
      map.put("a", "");
      map.put("b", "");

      assertEquals(2, map.size());
   }

   @Test
   void allowsMultipleElementsSameKey() {
      map.put("a", "alpha1");
      map.put("a", "alpha2");

      var values = map.get("a");

      assertEquals(List.of("alpha1", "alpha2"), sorted(values));
   }

   @Test
   void valuesSizeRepresentsTotalCountOfValues() {
      map.put("a", "alpha");
      map.put("b", "beta1");
      map.put("b", "beta2");

      var size = map.valuesSize();

      assertEquals(3, size);
   }

   @Test
   void returnsOnlyValuesAssociatedWithKey() {
      map.put("a", "alpha");
      map.put("b", "beta");

      var values = map.get("b");

      assertEquals(List.of("beta"), values);
   }

   @Test
   void throwsOnPutOfNullKey() {
      assertThrows(NullPointerException.class, () -> map.put(null, ""));
   }

   @Test
   void clearEliminatesAllData() {
      map.put("a", "");
      map.put("b", "");

      map.clear();

      assertEquals(0, map.size());
      assertEquals(0, map.valuesSize());
   }

   @Test
   void returnsCombinedCollectionOfAllValues() {
      map.put("a", "alpha");
      map.put("b", "beta");

      var values = map.values();

      assertEquals(List.of("alpha", "beta"), sorted(values));
   }

   private List<String> sorted(Collection<String> items) {
      return items.stream().sorted().toList();
   }
}