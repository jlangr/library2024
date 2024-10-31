package domain.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testutil.EqualityTester;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class PatronTest {
   private Patron jane;

   @BeforeEach
   void initialize() {
      jane = new Patron("Jane");
   }

   @Test
   void defaultsIdToEmpty() {
      assertEquals("", jane.getId());
   }

   @Test
   void fineBalanceIsZeroOnCreation() {
      assertEquals(0, jane.fineBalance());
   }

   @Test
   void holdingsAreEmptyOnCreation() {
      assertTrue(jane.holdingMap().isEmpty());
   }

   @Test
   void returnsHoldingsAdded() {
      var holding = new HoldingBuilder().create();

      jane.add(holding);

      assertEquals(List.of(holding), jane.holdingMap().holdings());
   }

   @Test
   void removesHoldingFromPatron() {
      var holding = new HoldingBuilder().create();
      jane.add(holding);

      jane.remove(holding);

      assertTrue(jane.holdingMap().isEmpty());
   }

   @Test
   void storesFines() {
      jane.addFine(10);

      assertEquals(10, jane.fineBalance());
   }

   @Test
   void increasesBalanceOnAdditionalFines() {
      jane.addFine(10);

      jane.addFine(30);

      assertEquals(40, jane.fineBalance());
   }

   @Test
   void decreasesBalanceWhenPatronRemitsAmount() {
      jane.addFine(40);

      jane.remit(25);

      assertEquals(15, jane.fineBalance());
   }

   @Test
   void supportsEqualityComparison() {
      var patron1 = new Patron("p1", "Joe");
      var patron1Copy1 = new Patron("p1", "");
      var patron1Copy2 = new Patron("p1", "");
      var patron1Subtype = new Patron("p1", "") {
      };
      var patron2 = new Patron("p2", "");

      new EqualityTester(patron1, patron1Copy1, patron1Copy2, patron2, patron1Subtype).verify();
   }
}