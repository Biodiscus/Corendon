package nl.itopia.corendon.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import nl.itopia.corendon.data.Dbmanager;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.utils.Log;

/**
 *
 * @author wieskueter.com
 */

public class EmployeeModel {
    private final Dbmanager dbmanager = new Dbmanager();
    private static final EmployeeModel _default = new EmployeeModel();
    
    private EmployeeModel() { 
    }
    
    public Employee getEmployee(int id) {
        nl.itopia.corendon.data.Employee employee = new nl.itopia.corendon.data.Employee();
        
        ResultSet result = dbmanager.doQuery("SELECT * FROM employee WHERE id = "+ id);
        
        try {
        employee.username = (String)result.getObject("username");
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getMessage());
        }
        
        return employee;
    }
    
    public nl.itopia.corendon.data.Employee getEmployees()
    {
        return null;
    }
    
    public static EmployeeModel getDefault() {
        return _default;
    }
}
