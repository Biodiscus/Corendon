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
}
