package nl.itopia.corendon.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.itopia.corendon.data.DBManager;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.utils.Log;

/**
 *
 * @author wieskueter.com
 */

public class EmployeeModel {
    private final DBManager dbmanager = new DBManager();
    private static final EmployeeModel _default = new EmployeeModel();
    
    private EmployeeModel() { 
    }
    
    public Employee getEmployee(int id) {
        Employee employee = new Employee();
        
        ResultSet result = dbmanager.doQuery("SELECT * FROM employee WHERE id = "+ id);
        
        try {
        employee.username = (String)result.getObject("username");
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getMessage());
        }
        
        return employee;
    }
    
    public Employee getEmployees()
    {
        return null;
    }
    
    public static EmployeeModel getDefault() {
        return _default;
    }
}
