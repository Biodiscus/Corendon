package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Action;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * © 2014, Biodiscus.net Robin
 */
public class ActionModel {
    
    private static final ActionModel _default = new ActionModel();
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();

    private Map<Integer, Action> _cache;

    private ActionModel() {
        _cache = new HashMap<>();
    }

    public Action getAction(int id) {
        Action action = null;
        if(_cache.containsKey(id)) {
            action = _cache.get(id);
        } else {
            try {
                ResultSet result = dbmanager.doQuery("SELECT * FROM action WHERE id = " + id);
                if (result.next()) {
                    String name = result.getString("name");
                    action = new Action(id, name);
                    // Add the action to the cache
                    _cache.put(id, action);

                }
            } catch (SQLException e) {
                Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
            }
        }

        return action;
    }

    public Action getAction(String name) {
        Action action = null;
        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM action WHERE name = '" + name + "'");
            if (result.next()) {
                int id = result.getInt("id");
                action = new Action(id, name);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return action;
    }

    public List<Action> getActions() {
        List<Action> actions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM airport";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                Action action = new Action(id, name);
                actions.add(action);

                // Add the action to the cache
                _cache.put(id, action);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return actions;
    }

    public static ActionModel getDefault() {
        return _default;
    }
}
