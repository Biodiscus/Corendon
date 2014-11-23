package nl.itopia.corendon.model;

/**
 * © 2014, Biodiscus.net Robin
 */
public class DateModel {
    private static final DateModel _default = new DateModel();

    private DateModel() {}

    public long getCurrentTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static DateModel getDefault() {
        return _default;
    }
}
