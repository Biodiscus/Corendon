package nl.itopia.corendon.data;

/**
 *
 * @author Erik
 */
public class Action {
    
    private int id;
    private String name;
    
    public Action(int id, String name) {
        
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
