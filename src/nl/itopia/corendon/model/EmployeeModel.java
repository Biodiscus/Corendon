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
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private static final EmployeeModel _default = new EmployeeModel();
    
    private EmployeeModel() { 
    }
    
    public Employee getEmployee(int id) {
        Employee employee = new Employee(id);

        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM employee WHERE id = "+ id);

            if(result.next()) {
                employee = resultToEmployee(result);
            }

            return employee;
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
            return null;
        }
    }

    private Employee resultToEmployee(ResultSet result) throws SQLException{
        Employee employee = new Employee(result.getInt("id"));
        // TODO: role, airport
        employee.username = result.getString("username");
        employee.password = result.getString("password");
        employee.salt = result.getString("salt");
        employee.contactDetails = result.getString("contact_details");
        employee.notes = result.getString("notes");
        employee.createDate = result.getInt("create_date");
        employee.createDate = result.getInt("last_online");


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
