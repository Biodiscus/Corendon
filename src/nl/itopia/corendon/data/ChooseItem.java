package nl.itopia.corendon.data;

/**
 * © 2014, Biodiscus.net Robin
 */
public class ChooseItem {
    private final int id;
    private final String name;

    public ChooseItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getKey() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
