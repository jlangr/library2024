package domain.core;

import org.junit.Before;
import org.junit.Test;
import testutil.EqualityTester;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PatronTest {
   private Patron jane;

   @Before
   public void initialize() {
      jane = new Patron("Jane");
   }

   @Test
   public void defaultsIdToEmpty() {
      assertEquals("", jane.getId());
   }

   @Test
   public void fineBalanceIsZeroOnCreation() {
      assertEquals(0, jane.fineBalance());
   }

   @Test
   public void holdingsAreEmptyOnCreation() {
      assertTrue(jane.holdingMap().isEmpty());
   }

   @Test
   public void returnsHoldingsAdded() {
      var holding = new HoldingBuilder().create();

      jane.add(holding);

      assertEquals(List.of(holding), jane.holdingMap().holdings());
   }

   @Test
   public void removesHoldingFromPatron() {
      var holding = new HoldingBuilder().create();
      jane.add(holding);

      jane.remove(holding);

      assertTrue(jane.holdingMap().isEmpty());
   }

   @Test
   public void storesFines() {
      jane.addFine(10);

      assertEquals(10, jane.fineBalance());
   }

   @Test
   public void increasesBalanceOnAdditionalFines() {
      jane.addFine(10);

      jane.addFine(30);

      assertEquals(40, jane.fineBalance());
   }

   @Test
   public void decreasesBalanceWhenPatronRemitsAmount() {
      jane.addFine(40);

      jane.remit(25);

      assertEquals(15, jane.fineBalance());
   }

   @Test
   public void supportsEqualityComparison() {
      Patron patron1 = new Patron("p1", "Joe");
      Patron patron1Copy1 = new Patron("p1", "");
      Patron patron1Copy2 = new Patron("p1", "");
      Patron patron1Subtype = new Patron("p1", "") {
      };
      Patron patron2 = new Patron("p2", "");

      new EqualityTester(patron1, patron1Copy1, patron1Copy2, patron2, patron1Subtype).verify();
   }
}