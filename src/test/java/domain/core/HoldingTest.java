package domain.core;

import com.loc.material.api.Material;
import com.loc.material.api.MaterialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testutil.EqualityTester;
import util.DateUtil;

import java.util.Calendar;
import java.util.Date;

import static com.loc.material.api.MaterialType.*;
import static domain.core.Branch.CHECKED_OUT;
import static org.junit.jupiter.api.Assertions.*;

/*
This test class is a mess. Some of the opportunities for cleanup:

 - AAA used but no visual separation
 - seeming use of AAA, but it's not really
 - unnecessary code (null checks? try/catch?)
 - constant names that obscure relevant information
 - can data be created in the test?
 - poor / inconsistent test names
 - comments in tests (are they even true)?
 - multiple behaviors/asserts per test
 - code in the wrong place / opportunities for reuse of existing code
 - dead code
 */

class HoldingTest {
   private static final Material THE_TRIAL = new Material("10", "", "10", "", "");
   private static final Material DR_STRANGELOVE = new Material("12", "", "11", "", DVD, "");
   private static final Material IT = new Material("12", "", "11", "", MaterialType.NEW_RELEASE_DVD, "");
   private Holding holding;
   private static final Date TODAY = new Date();
   private static final int COPY_NUMBER_1 = 1;
   private final Branch eastBranch = new Branch("East");
   private final Branch westBranch = new Branch("West");

   @Nested
   class HoldingAtEastBranch {
      @BeforeEach
      void setUp() {
         holding = new Holding(THE_TRIAL, eastBranch, COPY_NUMBER_1);
      }

      @Test
      void changesBranchOnTransfer() {
         holding.transfer(westBranch);
         assertEquals(westBranch, holding.getBranch());
      }

      @Test
      void ck() {
         holding.checkOut(TODAY);
         assertEquals(TODAY, holding.dateCheckedOut());
         assertTrue(holding.dateDue().after(TODAY));
         assertEquals(CHECKED_OUT, holding.getBranch());
         assertFalse(holding.isAvailable());

         holding.checkOut(TODAY);
         Date tomorrow = new Date(TODAY.getTime() + 60L + 60 * 1000 * 24);
         holding.checkIn(tomorrow, eastBranch);
         assertEquals(tomorrow, holding.dateLastCheckedIn());
         assertTrue(holding.isAvailable());
         assertEquals(eastBranch, holding.getBranch());
      }

      @Test
      void returnDateForStandardBook() {
         holding.checkOut(TODAY);
         var dateDue = holding.dateDue();
         assertDateEquals(addDays(TODAY, BOOK.checkoutPeriod()), dateDue);
      }

      @Test
      void dateDueNullWhenCheckedOutIsNull() {
         assertNull(holding.dateDue());
      }

      @Test
      void daysLateIsZeroWhenDateDueIsNull() {
         assertEquals(0, holding.daysLate());
      }

      @Test
      void testSomething() {
         // movie
         checkOutToday(DR_STRANGELOVE, eastBranch);
         addDays(TODAY, DVD.checkoutPeriod());
         assertDateEquals(addDays(TODAY, DVD.checkoutPeriod()), holding.dateDue());

         // childrens movie
         checkOutToday(IT, eastBranch);
         var expected = addDays(TODAY, NEW_RELEASE_DVD.checkoutPeriod());
         assertDateEquals(expected, holding.dateDue());
      }

      @Test
      void answersDaysLateOfZeroWhenReturnedSameDay() {
         checkOutToday(THE_TRIAL, eastBranch);
         var daysLate = holding.checkIn(TODAY, eastBranch);
         assertEquals(0, daysLate);
      }

      @Test
      void answersDaysLateOfZeroWhenReturnedOnDateDue() {
         checkOutToday(THE_TRIAL, eastBranch);
         var daysLate = holding.checkIn(holding.dateDue(), eastBranch);
         assertEquals(0, daysLate);
      }

      @Test
      void answersDaysLateWhenReturnedAfterDueDate() {
         try {
            checkOutToday(THE_TRIAL, eastBranch);
            var date = DateUtil.addDays(holding.dateDue(), 3);
            var days = holding.checkIn(date, eastBranch);
            assertEquals(3, days);
         } catch (RuntimeException notReallyExpected) {
            fail();
         }
      }

      private void checkOutToday(Material material, Branch branch) {
         holding = new Holding(material, branch);
         holding.checkOut(TODAY);
      }

      public static Date addDays(Date date, int days) {
         return new Date(date.getTime() + days * 60L * 1000 * 60 * 24);
      }

      public static void assertDateEquals(Date expectedDate, Date actualDate) {
         var calendar = Calendar.getInstance();
         calendar.setTime(expectedDate);
         var expectedYear = calendar.get(Calendar.YEAR);
         var expectedDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
         calendar.setTime(actualDate);
         assertEquals(expectedYear, calendar.get(Calendar.YEAR));
         assertEquals(expectedDayOfYear, calendar.get(Calendar.DAY_OF_YEAR));
      }

      @Test
      void equality() {
         var holding1 = new Holding(THE_TRIAL, eastBranch, 1);
         var holding1Copy1 = new Holding(THE_TRIAL, westBranch, 1); // diff loc but same copy
         var holding1Copy2 = new Holding(THE_TRIAL, CHECKED_OUT, 1);
         var holding2 = new Holding(THE_TRIAL, eastBranch, 2); // 2nd copy
         var holding1Subtype = new Holding(THE_TRIAL, eastBranch,
            1) {
         };

         new EqualityTester(holding1, holding1Copy1, holding1Copy2, holding2,
            holding1Subtype).verify();
      }
   }

   @Nested
   class NewlyCreatedHolding {
      final Holding holding = new Holding(THE_TRIAL);

      @Test
      void branchDefaultsToCheckedOutWhenCreated() {
         assertEquals(CHECKED_OUT, holding.getBranch());
      }

      @Test
      void copyNumberDefaultsTo1() {
         assertEquals(1, holding.getCopyNumber());
      }
   }
}