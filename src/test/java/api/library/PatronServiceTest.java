package api.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.PatronStore;

import static org.junit.jupiter.api.Assertions.*;

class PatronServiceTest {
   PatronService service;

   @BeforeEach
   void initialize() {
      PatronStore.deleteAll();
      service = new PatronService();
   }

   @Test
   void answersGeneratedId() {
      var scanCode = service.add("name");

      assertTrue(scanCode.startsWith("p"));
   }

   @Test
   void allowsAddingPatronWithId() {
      service.add("p123", "xyz");

      var patron = service.find("p123");

      assertEquals("xyz", patron.getName());
   }

   @Test
   void rejectsPatronIdNotStartingWithP() {
      assertThrows(InvalidPatronIdException.class, () ->
         service.add("234", ""));
   }

   @Test
   void rejectsAddOfDuplicatePatron() {
      service.add("p556", "");
      assertThrows(DuplicatePatronException.class, () ->
         service.add("p556", ""));
   }

   @Test
   void answersNullWhenPatronNotFound() {
      assertNull(service.find("nonexistent id"));
   }
}
