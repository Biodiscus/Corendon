package nl.itopia.corendon.data;

/**
 *
 * @author igor
 */
public class Status {
    
    private int id;
    private String name;

    public Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public int getID() {
        return id;
    }
}
