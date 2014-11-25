package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Role;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * © 2014, Biodiscus.net robin
 */
public class RoleModel {
    private static final RoleModel _default = new RoleModel();
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();

    private RoleModel() {}

    public Role getRole(int id) {
        Role role = null;

        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM role WHERE id = "+ id);
            if(result.next()) {
                String name = result.getString("name");
                //role = new Role(id, name);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return role;
    }

    public static RoleModel getDefault() {
        return _default;
    }
    
    public Role getRoleByUserId(int userId) {
        
        /* Role query */
        String roleQuery = "SELECT roles.id, roles.name FROM employee\n" +
                        "INNER JOIN roles ON roles.id = employee.role_id\n" +
                        "WHERE employee.id = " + userId;
        
        /* create role object so we can manipulate it later on */
        Role role = null;
        
        try {
            /* try to run the sql query */
            ResultSet result = dbmanager.doQuery(roleQuery);
            if(result.next()) {
                /* ok, there is a record. Get the field values */
                int roleId = result.getInt("id");
                String roleName = result.getString("name");
                
                /* make a new role object and send the field values to the constructor */
                role = new Role(roleId, roleName);
                
            }
        } catch (SQLException e) {
           Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        
        /* return the full role */
        return role;
    }
}
