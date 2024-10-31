package testutil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CollectionsUtilTest {
   private Collection<Object> collection;

   @BeforeEach
   void initialize() {
      collection = new ArrayList<>();
   }

   @Test
   void soleElementRetrievesFirstAndOnlyElement() {
      collection.add("a");

      var soleElement = CollectionsUtil.soleElement(collection);

      assertEquals("a", soleElement);
   }

   @Test
   void soleElementThrowsWhenNoElementsExist() {
      var thrown = assertThrows(AssertionError.class, () ->
         CollectionsUtil.soleElement(collection));
      assertEquals(CollectionsUtil.NO_ELEMENTS, thrown.getMessage());
   }

   @Test
   void soleElementThrowsWhenMoreThanOneElement() {
      collection.add("a");
      collection.add("b");
      var thrown = assertThrows(AssertionError.class, () ->
         CollectionsUtil.soleElement(collection));
      assertEquals(CollectionsUtil.MORE_THAN_ONE_ELEMENT, thrown.getMessage());
   }
}