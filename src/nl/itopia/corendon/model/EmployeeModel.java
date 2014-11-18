package nl.itopia.corendon.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.utils.Log;

/**
 *
 * @author wieskueter.com
 */

public class EmployeeModel {
    private final DBManager dbmanager = DBManager.getDefault();
    private static final EmployeeModel _default = new EmployeeModel();
    
    private EmployeeModel() { 
    }
    
    public Employee getEmployee(int id) {
        Employee employee = new Employee(id);
        
        ResultSet result = dbmanager.doQuery("SELECT * FROM employee WHERE id = "+ id);
        
        try {
            if(result.next()) {
                employee.username = (String)result.getObject("username");
                employee.password = (String)result.getObject("password");
                employee.salt = (String)result.getObject("salt");
//                int role_id = result.getInt("role_id");
//                employee.role = RoleModel.getDefault().getRole(role_id);
//                String a = employee.role.getName();

                Log.display("Employee", employee.username, employee.password, employee.salt);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
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
