package nl.itopia.corendon.data;

/**
 *
 * @author igor
 */
public class Brand {
    
    private int id;
    private String name;
    
    public Brand(int id, String name) {
        
        this.id = id;
        this.name = name;
    }
    public Brand(){
        
    }
    
    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }
}
