package persistence;

import domain.core.HoldingBuilder;
import domain.core.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.*;

class PatronStoreTest {
   private PatronStore store;
   private static final Patron patronSmith = new Patron("p1", "joe");

   @BeforeEach
   void initialize() {
      PatronStore.deleteAll();
      store = new PatronStore();
   }

   @Test
   void persistsAddedPatron() {
      store.add(patronSmith);

      var patrons = store.getAll();

      assertEquals(List.of(patronSmith), patrons);
   }

   @Test
   void assignsId() {
      var patron = new Patron("name");

      store.add(patron);

      assertTrue(patron.getId().startsWith("p"));
   }

   @Test
   void assignedIdIsUnique() {
      var patronA = new Patron("a");
      var patronB = new Patron("b");

      store.add(patronA);
      store.add(patronB);

      assertNotEquals(patronB.getId(), patronA.getId());
   }

   @Test
   void doesNotOverwriteExistingId() {
      var patron = new Patron("p12345", "");

      store.add(patron);

      assertEquals("p12345", store.find("p12345").getId());
   }

   @Test
   void returnsPersistedPatronAsNewInstance() {
      store.add(patronSmith);

      var found = store.find(patronSmith.getId());

      assertNotSame(patronSmith, found);
   }

   @Test
   void storesHoldingsAddedToPatron() {
      var holding = new HoldingBuilder().create();
      store.add(patronSmith);
      store.addHoldingToPatron(patronSmith, holding);

      var patron = store.find(patronSmith.getId());

      assertEquals(List.of(holding), patron.holdingMap().holdings());
   }

   // TODO
//   @Test(expected = PatronNotFoundException.class)
//   void throwsOnAddingHoldingToNonexistentPatron() {
//      store.addHoldingToPatron(patronSmith, new HoldingBuilder().create());
//   }

   @Test
   void findsPersistedPatronById() {
      store.add(patronSmith);

      var found = store.find(patronSmith.getId());

      assertEquals(patronSmith.getName(), found.getName());
   }
}
