package domain.core;

import org.junit.jupiter.api.Test;
import testutil.EqualityTester;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BranchTest {
   @Test
   void defaultsIdToEmpty() {
      assertEquals("", new Branch("alpha").getScanCode());
   }

   @Test
   void supportsEquality() {
      var branch1 = new Branch("b111", "east");
      var branch1Copy1 = new Branch("b111", "east");
      var branch1Copy2 = new Branch("b111", "east");
      var branch1Subtype = new Branch("b111", "east") {
      };
      var branch2 = new Branch("b222", "west");

      new EqualityTester(branch1, branch1Copy1, branch1Copy2, branch2, branch1Subtype).verify();
   }
}