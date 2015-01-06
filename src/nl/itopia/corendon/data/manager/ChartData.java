package nl.itopia.corendon.data.manager;

/**
 * © 2014, Biodiscus.net robin
 */
public class ChartData {
    
    public long timestamp;
    public int count;

    public ChartData(long timestamp, int count) {
        this.timestamp = timestamp;
        this.count = count;
    }

    @Override
    public String toString() {
        return "ChartData("+timestamp+", "+count+")";
    }
}
