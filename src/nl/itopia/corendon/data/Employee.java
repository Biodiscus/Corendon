package nl.itopia.corendon.data;

import nl.itopia.corendon.model.Airport;
import nl.itopia.corendon.model.Role;

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
    
    public int getID() {
        return id;
    }
}
