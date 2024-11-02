package util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.DateUtil.*;

public class DateUtilTest {
   public static final Date NEW_YEARS_DAY = create(2011, JANUARY, 1);

   @Test
   void createGeneratedProperDateElements() {
      var calendar = Calendar.getInstance();

      calendar.setTime(NEW_YEARS_DAY);

      assertEquals(2011, calendar.get(YEAR));
      assertEquals(JANUARY, calendar.get(MONTH));
      assertEquals(1, calendar.get(DAY_OF_MONTH));
      assertEquals(0, calendar.get(HOUR_OF_DAY));
      assertEquals(0, calendar.get(MINUTE));
      assertEquals(0, calendar.get(SECOND));
      assertEquals(0, calendar.get(MILLISECOND));
   }

   @Test
   void addDaysAnswersLaterDate() {
      assertEquals(create(2017, MARCH, 22), addDays(create(2017, MARCH, 1), 21));
      assertEquals(create(2012, JANUARY, 3), addDays(NEW_YEARS_DAY, 367));
      assertEquals(create(2018, FEBRUARY, 1), addDays(create(2017, DECEMBER, 31), 32));
   }

   @Test
   void answersDaysFromWithinSameYear() {
      var laterBy15 = addDays(NEW_YEARS_DAY, 15);

      assertEquals(15, daysFrom(NEW_YEARS_DAY, laterBy15));
   }

   @Test
   void answersDaysFromToNextYear() {
      var laterBy375 = addDays(NEW_YEARS_DAY, 375);

      assertEquals(375, daysFrom(NEW_YEARS_DAY, laterBy375));
   }

   @Test
   void answersDaysFromManyYearsOut() {
      var later = addDays(NEW_YEARS_DAY, 2100);

      assertEquals(2100, daysFrom(NEW_YEARS_DAY, later));
   }

   @Test
   void convertsJavaUtilDateToLocalDate() {
      LocalDate converted = toLocalDate(create(2016, MAY, 15));

      assertEquals(15, converted.getDayOfMonth());
      assertEquals(2016, converted.getYear());
      assertEquals(Month.MAY, converted.getMonth());
   }

   @Test
   void getCurrentDateReturnsInjectedValue() {
      fixClockAt(NEW_YEARS_DAY);

      var date = getCurrentDate();

      assertEquals(NEW_YEARS_DAY, date);
   }

   @Test
   void getCurrentLocalDateReturnsInjectedValue() {
      fixClockAt(NEW_YEARS_DAY);

      var date = getCurrentLocalDate();

      assertEquals(NEW_YEARS_DAY, toDate(date));
   }

   @Test
   void ageInYearsDeterminesYearsBetweenTwoLocalDates() {
      int age = ageInYears(LocalDate.of(2010, Month.MAY, 1), LocalDate.of(2015, Month.MAY, 2));

      assertEquals(5, age);
   }
}