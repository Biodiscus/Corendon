package nl.itopia.corendon.data;

/**
 *
 * @author wieskueter.com
 */
public class Employee {
    private int id;
    public String username;
    public String password;
    public String salt;
    public String contactDetails;
    public String notes;
    public Role role;
    public Airport airport;
    public int createDate;
    public int lastOnline;

    public Employee(int id) {
        this.id = id;
    }
    
    public int getID() {
        return id;
    }
}
