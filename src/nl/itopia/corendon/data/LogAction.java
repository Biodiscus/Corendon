package nl.itopia.corendon.data;

/**
 *
 * @author Erik
 */
public class LogAction {
    
    private int id;
    public long date;
    public Action action;
    public Employee employee;
    public Luggage luggage;

    public LogAction(int id) {
        this.id = id;
    }
    
    public int getID() {
        return id;
    }
    
    public void setID(int id) {
        this.id = id;
    }
}
