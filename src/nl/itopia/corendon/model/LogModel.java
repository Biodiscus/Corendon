package nl.itopia.corendon.model;

import nl.itopia.corendon.data.LogAction;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jeroentje, Robin de Jong
 */
public class LogModel {
    private static final LogModel _default = new LogModel();
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();

    private Map<Integer, LogAction> _cache;

    private LogModel() {
        _cache = new HashMap<>();
    }

    private LogAction resultToLogAction(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        int actionId = result.getInt("action_id");
        int employeeId = result.getInt("employee_id");
        int luggageId = result.getInt("luggage_id");
        
        /* models */
        LuggageModel luggagemodel   = LuggageModel.getDefault();
        ActionModel actionmodel     = ActionModel.getDefault();
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        
        LogAction logaction = new LogAction(id);
        logaction.date      = result.getInt("date");
        logaction.action    = actionmodel.getAction(actionId);
        logaction.employee  = employeemodel.getEmployee(employeeId);
        logaction.luggage   = luggagemodel.getLuggage(luggageId);

        return logaction;
    }

    public List<LogAction> getLogFiles() {
        List<LogAction> logFiles = new ArrayList<>();
        try {
            String sql = "SELECT * FROM log";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                logFiles.add(resultToLogAction(result));

                // Add the airport to the cache
//                _cache.put(id, airport);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return logFiles;
    }

    public int insertAction(LogAction log) {
        long date = log.date;
        int action_id = log.action.getID();
        int employee_id = log.employee.getID();
        int luggage_id = log.luggage.getID();

        String query = "INSERT INTO log " +
                "(date, action_id, employee_id, luggage_id) " +
                "VALUES" +
                "('%d', '%d', '%d', '%d')";

        String finalQuery = String.format(
                query, date, action_id, employee_id, luggage_id
        );

        try {
            dbmanager.insertQuery(finalQuery);

            // After inserting the item, get the last added luggage
            // This way we can set the correct ID to the new luggage
            ResultSet result = dbmanager.doQuery("SELECT LAST_INSERT_ID()");
            if(result.next()) {
                return result.getInt(1);
            }

        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return -1;
    }

    public static LogModel getDefault() {
        return _default;
    }
}
