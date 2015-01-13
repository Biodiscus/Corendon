package nl.itopia.corendon.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.itopia.corendon.data.ChooseItem;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.utils.Hashing;
import nl.itopia.corendon.utils.Log;

/**
 * @author wieskueter.com & Jeroentje
 */
public class EmployeeModel {

    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private static final EmployeeModel _default = new EmployeeModel();

    // The current employee that is logged in
    public Employee currentEmployee;


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
    
    public Employee getEmployee(String username) {
        
        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM employee WHERE username = '"+ username +"'");
            
            if (result.next()) {
                int employeeId = Integer.parseInt(result.getString("id"));
                Employee employee = getEmployee(employeeId);
                return employee;
            }
            
         } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
            return null;
        }
        
        return null;
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
            String sql = "SELECT * FROM employee WHERE account_status != 'deleted'";
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
    
    public List<Employee> getLogEmployees() {
        List<Employee> employeeList = new ArrayList<Employee>();
        try {
            String sql = "SELECT DISTINCT(employee.id), employee.username FROM log INNER JOIN employee on employee.id = log.employee_id";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String username = result.getString("username");
                Employee employee = new Employee(id);
                employee.username = username;
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        return employeeList;
    }
    
    public ChooseItem employeeToChoose(Employee employee) {
        return new ChooseItem(employee.getID(), employee.username);
    }    

    public Employee login(Employee employee) {
        if (checkPassword(employee)) {
            Log.display("Password correct");
            /* User exists and password is corect. Return the full employee */
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
            /* password is incorect or the username doesn't exists return null */
            /* @TODO Clean the employee object up */
            return null;
        }
    }

    private boolean checkPassword(Employee employee) {
        if (userExists(employee)) {
            /* username exists, convert plain password to sha256 and attach it to the model */
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

    public boolean userExists(Employee employee) {

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
     * Insert new employee into database,
     * when it's inserted the reference ID will be set to the last inserted ID.
     *
     * @param employee Employee
     */
    public void createEmployee(Employee employee) {

        int role_id = employee.role.getID();
        int airport_id = employee.airport.getID();

        String query = "INSERT INTO employee " +
                "(username, password, salt, first_name, last_name, role_id, contact_details, notes,airports_id)" +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%d', '%s', '%s', ' %s' )";

        String finalQuery = String.format(
                query, employee.username, employee.password, employee.salt, employee.firstName, employee.lastName,
                role_id, employee.contactDetails, employee.notes, airport_id
        );

        try {
            dbmanager.insertQuery(finalQuery);
            // After inserting the item, get the last added employee
            // This way we can set the correct ID to the new employee
            ResultSet result = dbmanager.doQuery("SELECT LAST_INSERT_ID()");
            if(result.next()) {
                employee.setID(result.getInt(1));
            } else {
                // ERROR!
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }
    
    public void editEmployee(Employee employee) {
        
        String editQuery = "UPDATE employee SET "
                + "username = '"+ employee.username +"', ";
                if(null != employee.password ) {
                    /* checking if password is blank or not.When the password is not blank, update the old password with the new one */
                    editQuery += "password = '%s', salt = '%s', ";
                    editQuery = String.format(editQuery, employee.password, employee.salt);
                }
                editQuery += "first_name = '"+ employee.firstName +"', "
                + "last_name = '"+ employee.lastName +"', "
                + "role_id = "+ employee.role.getID() +", "
                + "contact_details = '"+ employee.contactDetails +"', "
                + "notes = '"+ employee.notes +"', "
                + "airports_id = " + employee.airport.getID()
                + " WHERE id = "+ employee.id;
        
        Log.display(editQuery);
       try {
           dbmanager.updateQuery(editQuery);
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }

    /**
     * Hard delete username from database
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
