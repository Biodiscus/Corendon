package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Status;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.itopia.corendon.data.ChooseItem;

/**
 * © 2014, Biodiscus.net Robin
 */
public class StatusModel {
    private static final StatusModel _default = new StatusModel();
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();

    private Map<Integer, Status> _cache;

    private StatusModel() {
        _cache = new HashMap<>();
    }

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

    public List<Status> getFoundLost() {
        List<Status> status = new ArrayList<>();
        try {
            String sql = "SELECT * FROM status WHERE id != 3";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                Status stat = new Status(id, name);
                status.add(stat);

                // Add the airport to the cache
                _cache.put(id, stat);
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
    
    public ChooseItem statusToChoose(Status status) {
        return new ChooseItem(status.getID(), status.getName());
    }    

    public static StatusModel getDefault() {
        return _default;
    }
}
