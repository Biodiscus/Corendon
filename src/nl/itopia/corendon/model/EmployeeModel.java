package nl.itopia.corendon.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.utils.Log;

/**
 *
 * @author wieskueter.com & Jeroentje
 */

public class EmployeeModel {
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private static final EmployeeModel _default = new EmployeeModel();
    
    private EmployeeModel() { 
    }
    
    /**
     * Get the employee based on Id
     *
     * @param id    a {@code int} Id
     * @return     Get the full object of employee
     */    
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
    
    /**
     * parse a resultset to a Employee Object
     *
     * @param result    a {@code ResultSet} ResultSet
     * @return     Get the full object of employee
     */ 
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
    
    /**
     * get all employees
     *
     * @return     Arraylist of all employees
     */ 
    public Employee[] getEmployees()
    {
        return null;
    }
    
    public Employee login(Employee employee)
    {
        
        if(checkPassword(employee))
        {
            /* user exists and password is corect. Return the full employee */
            String employeeIdQuery  = "SELECT id FROM employee WHERE username = '" + employee.username + "' AND  password = '" + employee.password + "'";
            
            int employeeId = 0;
            try {
                ResultSet result = dbmanager.doQuery(employeeIdQuery);
                if(result.next()) {
                    employeeId = Integer.parseInt(result.getString("id"));
                }

            } catch (SQLException e) {
                Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
            }
            
            return getEmployee(employeeId);
        }else{
            /* password is incorect or the user doesn't exists return null */ 
            /* @TODO Clean the employee object up */
            return null;
        }

    }
    
    private boolean checkPassword(Employee employee)
    {
        if(userExists(employee))
        {
            /* user exists, convert plain password to sha256 and attach it to the model */
            String hashedPass = sha256(employee.password);
            String salt = getSalt(employee);
            String finalPass =  sha256(salt + hashedPass);
            employee.password = finalPass;
            
            String passwordQuery = "SELECT COUNT(*) as usercounter FROM employee WHERE username = '" + employee.username + "' AND password = '" + finalPass + "'";

            int numRecords  = 0;
            
            try {
                ResultSet result = dbmanager.doQuery(passwordQuery);
                if(result.next()) {
                    String userCount = result.getString("usercounter");
                    numRecords = Integer.parseInt(userCount);
                }

            } catch (SQLException e) {
                Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
            }
            
            return numRecords == 1;
        }else{
            /* user doesn't exists */
            return false;
        }
    }
       private boolean userExists(Employee employee){
        
        String checkUser = "SELECT COUNT(*) AS usercounter FROM employee WHERE username = '" + employee.username + "'";

        int numRecords  = 0;
        
        try {
            ResultSet result = dbmanager.doQuery(checkUser);
            if(result.next()) {
                String userCount = result.getString(1);
                numRecords = Integer.parseInt(userCount);
            }

        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        
        return numRecords == 1;
    }
    
    private String getSalt(Employee employee)
    {
        String saltQuery = "SELECT salt FROM employee WHERE username = '" + employee.username + "'";

        String salt  = "";
        
        try {
            ResultSet result = dbmanager.doQuery(saltQuery);
           if(result.next()) {
                salt = result.getString(1);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        
        return salt;
    }
    
    private byte[] generateSalt()
    {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        
        return salt;
    }
    
    private String sha256(String value) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }    
    
    public static EmployeeModel getDefault() {
        return _default;
    }       
}
