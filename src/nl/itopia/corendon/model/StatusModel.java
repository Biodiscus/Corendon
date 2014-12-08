package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Status;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * © 2014, Biodiscus.net Robin
 */
public class StatusModel {
    private static final StatusModel _default = new StatusModel();
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();

    private StatusModel() {}

    public Status getStatus(String name) {
        Status status = null;
        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM status WHERE name = '"+ name+"'");
            if(result.next()) {
                int id = result.getInt("id");
                status = new Status(id, name);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return status;
    }


    public Status getStatus(int id) {
        Status status = null;
        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM status WHERE id = "+ id);
            if(result.next()) {
                String name = result.getString("name");
                status = new Status(id, name);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return status;
    }

    public static StatusModel getDefault() {
        return _default;
    }
}
