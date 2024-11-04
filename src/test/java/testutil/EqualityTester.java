package testutil;

import static org.junit.jupiter.api.Assertions.*;

public class EqualityTester {
   private final Object object1;
   private final Object object1Copy1;
   private final Object object1Copy2;
   private final Object object2;
   private final Object object1Subtype;

   public EqualityTester(Object object1, Object object1Copy1,
                         Object object1Copy2, Object object2, Object object1Subtype) {
      this.object1 = object1;
      this.object1Copy1 = object1Copy1;
      this.object1Copy2 = object1Copy2;
      this.object2 = object2;
      this.object1Subtype = object1Subtype;
   }

   public void verify() {
      assertEquals(object1, object1Copy1);
      assertNotEquals(object1, object2);
      assertNotEquals(object1, object1Subtype);

      assertNullComparison();
      assertConsistency();
      assertTransitivity();
      assertReflexivity();
      assertSymmetry();
   }

   private void assertNullComparison() {
      assertNotNull(object1);
   }

   private void assertConsistency() {
      assertEquals(object1, object1Copy1);
      assertNotEquals(object1, object2);
   }

   private void assertTransitivity() {
      assertEquals(object1Copy1, object1Copy2);
      assertEquals(object1, object1Copy2);
   }

   private void assertSymmetry() {
      assertEquals(object1, object1Copy1);
      assertEquals(object1Copy1, object1);
   }

   private void assertReflexivity() {
      assertEquals(object1, object2);
   }
}
