package nl.itopia.corendon.data;

/**
 *
 * @author igor
 */
public class Country {
    
    private int id;
    private String name;
    
    public Country(int id, String name) {
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
