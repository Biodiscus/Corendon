package nl.itopia.corendon.data;

/**
 *
 * @author Erik
 */
public class Picture {
    
    private int id;
    private String path;

    public Picture(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public int getID() {
        return id;
    }
}
