package testutil;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectionsUtilTest {
   private Collection<Object> collection;

   @Rule
   public final ExpectedException exceptionRule = ExpectedException.none();

   @BeforeEach
   void initialize() {
      collection = new ArrayList<Object>();
   }

   @Test
   void soleElementRetrievesFirstAndOnlyElement() {
      collection.add("a");

      var soleElement = CollectionsUtil.soleElement(collection);

      assertEquals("a", soleElement);
   }

   @Test
   void soleElementThrowsWhenNoElementsExist() {
      exceptionRule.expect(AssertionError.class);
      exceptionRule.expectMessage(CollectionsUtil.NO_ELEMENTS);

      CollectionsUtil.soleElement(collection);
   }

   @Test
   void soleElementThrowsWhenMoreThanOneElement() {
      exceptionRule.expect(AssertionError.class);
      exceptionRule.expectMessage(CollectionsUtil.MORE_THAN_ONE_ELEMENT);
      collection.add("a");
      collection.add("b");

      CollectionsUtil.soleElement(collection);
   }
}