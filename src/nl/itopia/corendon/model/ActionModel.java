package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Action;
import nl.itopia.corendon.data.Airport;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * © 2014, Biodiscus.net Robin
 */
public class ActionModel {
    private static final ActionModel _default = new ActionModel();
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();

    private ActionModel() {}

    public Action getAction(int id) {
        Action action = null;

        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM action WHERE id = "+ id);
            if(result.next()) {
                String name = result.getString("name");
                action = new Action(id, name);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return action;
    }

    public static ActionModel getDefault() {
        return _default;
    }
}
