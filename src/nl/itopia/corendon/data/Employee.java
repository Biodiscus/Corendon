package nl.itopia.corendon.data;

/**
 *
 * @author wieskueter.com
 */
public class Employee {
    public int id;
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String salt;
    public String contactDetails;
    public String notes;
    public Role role;
    public String account_status;
    public Airport airport;
    public long createDate;
    public long lastOnline;

    public Employee(int id) {
        this.id = id;
    }
    
    public int getID() {
        return id;
    }
}
