package nl.itopia.corendon.model;

import nl.itopia.corendon.utils.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * © 2014, Biodiscus.net Robin
 */
public class DateModel {
    private static final DateModel _default = new DateModel();

    private DateModel() {}

    public long getCurrentTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public String formatDate(String format, long timestamp) {
        Date date = timestampToDate(timestamp);
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    public Date timestampToDate(long stamp) {
        return new Date(stamp * 1000);
    }

    public String getTimestampDate(long timestamp) {
        String day = formatDate("E yyyy.MM.dd 'at' hh:mm:ss a zzz", timestamp);
        return formatDate("yyyy.MM.dd", timestamp);
    }

    public int getTimestampDay(long timestamp) {
        String day = formatDate("E yyyy.MM.dd 'at' hh:mm:ss a zzz", timestamp);
//        Log.display(day, timestamp);

        return 0;
    }

    public static DateModel getDefault() {
        return _default;
    }
}
