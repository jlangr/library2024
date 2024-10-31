package util;

import org.junit.After;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class TimestampSourceTest {
    static final Date NEW_YEARS_DAY = DateUtil.create(2011, Calendar.JANUARY, 1);

    @After
    public void clearTimestampSource() {
        TimestampSource.emptyQueue();
    }

    @Test
    public void retrievesSinglePushedTime() {
        TimestampSource.queueNextTime(NEW_YEARS_DAY);

        assertEquals(NEW_YEARS_DAY, TimestampSource.now());
    }

    @Test
    public void retrievesMultiplePushedTimes() {
        var groundhogDay = DateUtil.create(2011, Calendar.FEBRUARY, 2);
        TimestampSource.queueNextTime(NEW_YEARS_DAY);
        TimestampSource.queueNextTime(groundhogDay);

        assertEquals(NEW_YEARS_DAY, TimestampSource.now());
        assertEquals(groundhogDay, TimestampSource.now());
    }

    @Test
    public void isNotExhaustedWhenTimeQueued() {
        TimestampSource.queueNextTime(NEW_YEARS_DAY);
        assertFalse(TimestampSource.isExhausted());
    }

    @Test
    public void isExhaustedWhenNoTimeQueued() {
        assertTrue(TimestampSource.isExhausted());
        TimestampSource.queueNextTime(NEW_YEARS_DAY);
        TimestampSource.now();
        assertTrue(TimestampSource.isExhausted());
    }

    @Test
    public void returnsCurrentTimeWhenQueueExhausted() {
        TimestampSource.queueNextTime(NEW_YEARS_DAY);

        var now = new Date();
        TimestampSource.now();
        var retrievedNow = TimestampSource.now();
        assertTrue(retrievedNow.getTime() - now.getTime() < 100);
    }
}
