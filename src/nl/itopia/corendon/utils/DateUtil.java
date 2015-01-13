package nl.itopia.corendon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * © 2014, Biodiscus.net robin
 */
public class DateUtil {
    
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
