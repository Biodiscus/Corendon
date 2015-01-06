package nl.itopia.corendon.data;

/**
 *
 * @author Erik
 */
public class Role {
    
    private int id;
    private String name;

    public Role(int id, String name) {
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
