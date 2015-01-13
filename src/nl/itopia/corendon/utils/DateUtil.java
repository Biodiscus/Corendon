package nl.itopia.corendon.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * © 2014, Biodiscus.net robin
 */
public class DateUtil {
    public static final long HOUR_UNIX_TIMESTAMP = 3600;
    public static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();


    public static Date getEndOfDTheDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());

        // Make the time: 23:59:59
        calendar.add(Calendar.HOUR, 23);
        calendar.add(Calendar.MINUTE, 59);
        calendar.add(Calendar.SECOND, 59);

        Date newDate = new Date(calendar.getTimeInMillis());
        return newDate;
    }


    public static long dateToTimestamp(Date date) {
        return date.toInstant().getEpochSecond();
    }

    public static Date localDateToDate(LocalDate date) {
        Date value;
        Instant instant = Instant.from(date.atStartOfDay(DEFAULT_ZONE));
        value = Date.from(instant);

        return value;
    }

    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static String formatDate(String format, long timestamp) {
        Date date = timestampToDate(timestamp);
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    /**
     * Convert a unix timestamp to a Date object. The date object works with milliseconds while unix timestamps are
     * in seconds.
     * @param stamp long
     * @return Date
     */
    public static Date timestampToDate(long stamp) {
        return new Date(stamp * 1000);
    }

    public static String getTimestampDate(long timestamp) {
        return formatDate("dd.MM.yyyy", timestamp);
    }
}
