package nl.itopia.corendon.data;

/**
 *
 * @author igor
 */
public class Airport {
    
    private int id;
    private int code;
    private String name;
    
    public Airport(int id, int code, String name) {
        
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public int getCode() {
        return code;
    }
    
    public int getID() {
        return id;
    }
}
