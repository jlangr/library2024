package api.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.PatronStore;

import static org.hamcrest.Matchers.nullValue;
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

//   @Test(expected = InvalidPatronIdException.class)
//   void rejectsPatronIdNotStartingWithP() {
//      service.add("234", "");
//   }
//
//   @Test(expected = DuplicatePatronException.class)
//   void rejectsAddOfDuplicatePatron() {
//      service.add("p556", "");
//      service.add("p556", "");
//   }
   // TODO

   @Test
   void answersNullWhenPatronNotFound() {
      assertNull(service.find("nonexistent id"));
   }
}
