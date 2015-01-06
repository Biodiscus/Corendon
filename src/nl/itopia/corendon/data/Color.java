package nl.itopia.corendon.data;

/**
 *
 * @author igor
 */
public class Color {
    
    private int id;
    private String hex;

    public Color(int id, String hex) {
        
        this.id = id;
        this.hex = hex;
    }
    
    public String getHex() {
        return hex;
    }
    
    public int getID() {
        return id;
    }
}