package util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TimestampSourceTest {
   static final Date NEW_YEARS_DAY = DateUtil.create(2011, Calendar.JANUARY, 1);

   @AfterEach
   void clearTimestampSource() {
      TimestampSource.emptyQueue();
   }

   @Test
   void retrievesSinglePushedTime() {
      TimestampSource.queueNextTime(NEW_YEARS_DAY);

      assertEquals(NEW_YEARS_DAY, TimestampSource.now());
   }

   @Test
   void retrievesMultiplePushedTimes() {
      var groundhogDay = DateUtil.create(2011, Calendar.FEBRUARY, 2);
      TimestampSource.queueNextTime(NEW_YEARS_DAY);
      TimestampSource.queueNextTime(groundhogDay);

      assertEquals(NEW_YEARS_DAY, TimestampSource.now());
      assertEquals(groundhogDay, TimestampSource.now());
   }

   @Test
   void isNotExhaustedWhenTimeQueued() {
      TimestampSource.queueNextTime(NEW_YEARS_DAY);
      assertFalse(TimestampSource.isExhausted());
   }

   @Test
   void isExhaustedWhenNoTimeQueued() {
      assertTrue(TimestampSource.isExhausted());
      TimestampSource.queueNextTime(NEW_YEARS_DAY);

      TimestampSource.now();

      assertTrue(TimestampSource.isExhausted());
   }

   @Test
   void returnsCurrentTimeWhenQueueExhausted() {
      TimestampSource.queueNextTime(NEW_YEARS_DAY);

      var now = new Date();
      TimestampSource.now();
      var retrievedNow = TimestampSource.now();

      assertTrue(retrievedNow.getTime() - now.getTime() < 100);
   }
}
