package nl.itopia.corendon.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.itopia.corendon.controller.administrator.AdministratorController;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.data.table.TableUser;
import nl.itopia.corendon.utils.Hashing;
import nl.itopia.corendon.utils.Log;

/**
 * @author wieskueter.com & Jeroentje
 */
public class EmployeeModel {

    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private static final EmployeeModel _default = new EmployeeModel();

    // The current employee that is logged in
    public static Employee currentEmployee;

    private EmployeeModel() {
    }

    /**
     * Get the employee based on Id
     *
     * @param id a {@code int} Id
     * @return Get the full object of employee
     */
    public Employee getEmployee(int id) {
        Employee employee = new Employee(id);

        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM employee WHERE id = " + id);

            if (result.next()) {
                employee = resultToEmployee(result);
            }

            return employee;
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
            return null;
        }
    }

    /**
     * parse a resultset to an Employee Object
     *
     * @param result a {@code ResultSet} ResultSet
     * @return Get the full object of employee
     */
    private Employee resultToEmployee(ResultSet result) throws SQLException {

        RoleModel rolemodel = RoleModel.getDefault();
        AirportModel airportmodel = AirportModel.getDefault();

        Employee employee = new Employee(result.getInt("id"));
        employee.username = result.getString("username");
        employee.password = result.getString("password");
        employee.firstName = result.getString("first_name");
        employee.lastName = result.getString("last_name");
        employee.salt = result.getString("salt");
        employee.contactDetails = result.getString("contact_details");
        employee.notes = result.getString("notes");
        employee.createDate = result.getInt("create_date");
        employee.createDate = result.getInt("last_online");
        employee.role = rolemodel.getRoleByEmployeeId(employee.getID());
        employee.account_status = result.getString("account_status");
        employee.airport = airportmodel.getAirportByEmployeeId(employee.getID());

        return employee;
    }

    /**
     * get all employees
     *
     * @return Arraylist of all employees
     */
    public List<Employee> getEmployees() {
        List<Employee> employeeList = new ArrayList<Employee>();
        try {
            String sql = "SELECT * FROM employee";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                Employee employee = resultToEmployee(result);
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        return employeeList;
    }

    public Employee login(Employee employee) {
        if (checkPassword(employee)) {
            Log.display("Password correct");
            /* user exists and password is corect. Return the full employee */
            String employeeIdQuery = "SELECT id FROM employee WHERE username = '" + employee.username + "' AND  password = '" + employee.password + "'";

            int employeeId = 0;
            try {
                ResultSet result = dbmanager.doQuery(employeeIdQuery);
                if (result.next()) {
                    employeeId = Integer.parseInt(result.getString("id"));
                }

            } catch (SQLException e) {
                Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
            }

            return getEmployee(employeeId);
        } else {
            /* password is incorect or the user doesn't exists return null */ 
            /* @TODO Clean the employee object up */
            return null;
        }
    }

    private boolean checkPassword(Employee employee) {
        if (userExists(employee)) {
            /* user exists, convert plain password to sha256 and attach it to the model */
            String salt = getSalt(employee);
            String finalPass = Hashing.sha256(employee.password + salt);
            employee.password = finalPass;

            Log.display(employee.password);

            String passwordQuery = "SELECT COUNT(*) as usercounter FROM employee WHERE username = '" + employee.username + "' AND password = '" + finalPass + "'";

            int numRecords = 0;

            try {
                ResultSet result = dbmanager.doQuery(passwordQuery);
                if (result.next()) {
                    String userCount = result.getString("usercounter");
                    numRecords = Integer.parseInt(userCount);
                }

            } catch (SQLException e) {
                Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
            }

            return numRecords == 1;
        } else {
            /* user doesn't exists */
            return false;
        }
    }

    private boolean userExists(Employee employee) {

        String checkUser = "SELECT COUNT(*) AS usercounter FROM employee WHERE username = '" + employee.username + "'";

        int numRecords = 0;

        try {
            ResultSet result = dbmanager.doQuery(checkUser);
            if (result.next()) {
                String userCount = result.getString(1);
                numRecords = Integer.parseInt(userCount);
            }

        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return numRecords == 1;
    }

    private String getSalt(Employee employee) {

        String saltQuery = "SELECT salt FROM employee WHERE username = '" + employee.username + "'";

        String salt = "";

        try {
            ResultSet result = dbmanager.doQuery(saltQuery);
            if (result.next()) {
                salt = result.getString(1);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return salt;
    }

    public static EmployeeModel getDefault() {
        return _default;
    }

    public int usernameExists(String username) {
        try {
            ResultSet result = dbmanager.doQuery("SELECT int FROM employee WHERE username = '" + username + "'");
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return 0;
    }

    /**
     * Insert new employee into database
     *
     * @param employee
     */
    public void createEmployee(Employee employee) {

        int role_id = employee.role.getID();
        //int airport_id = employee.airport.getID();

        String query = "INSERT INTO employee " +
                "(username, password, salt, first_name, last_name, role_id, contact_details, notes,airports_id)" +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%d', '%s', '%s', ' %s' )";

        Log.display(employee.salt);

        String finalQuery = String.format(
                query, employee.username, employee.password, employee.salt, employee.firstName, employee.lastName,
                role_id, employee.contactDetails, employee.notes, 1
        );

        try {
            Log.display(finalQuery);
            dbmanager.insertQuery(finalQuery);
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }
    
    public void editEmployee(Employee employee)    
    {
        System.out.println("editEmployee - MODEL");
        String editQuery = "UPDATE employee SET "
                //+ "username = '"+ employee.username +"', "
                + "password = '"+ employee.password +"', "
                + "salt = '"+ employee.password +"', "
                + "first_name = '"+ employee.firstName +"', "
                + "last_name = '"+ employee.lastName +"', "
                //+ "role_id = '"+ employee. +"', "
                + "contact_details = '"+ employee.contactDetails +"', "
                + "notes = '"+ employee.notes +"', "
                + "airports_id = 1"
                + "WHERE id = "+ employee.id;
        
       try{
           dbmanager.updateQuery(editQuery);
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }

    /**
     * Hard delete user from database
     *
     * @param userID
     */
    public void deleteEmployee(int userID) {
        //String deleteQuery = "DELETE FROM employee WHERE id = '"+ userID +"'";
        String deleteQuery = "UPDATE employee SET account_status = 'deleted' WHERE id = '" + userID + "'";
        try {
            dbmanager.updateQuery(deleteQuery);
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }
}
