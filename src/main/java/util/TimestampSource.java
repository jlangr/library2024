package util;

import java.util.Date;
import java.util.LinkedList;

public class TimestampSource {
    private static final LinkedList<Date> storedTimes = new LinkedList<>();

    private TimestampSource() {}

    public static void queueNextTime(Date date) {
        storedTimes.add(date);
    }

    public static Date now() {
        if (storedTimes.isEmpty())
            return new Date();
        return storedTimes.removeFirst();
    }

    public static Boolean isExhausted() {
        return storedTimes.isEmpty();
    }

    public static void emptyQueue() {
        storedTimes.clear();
    }
}
