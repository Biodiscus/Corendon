package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * © 2014, Biodiscus.net Robin
 */
public class LuggageModel {
    private final DBManager dbmanager = DBManager.getDefault();
    private static final LuggageModel _default = new LuggageModel();

    private EmployeeModel employeeModel;
    private ColorModel colorModel;

    private LuggageModel() {
        employeeModel = EmployeeModel.getDefault();
        colorModel = ColorModel.getDefault();
    }

    public Luggage getLuggage(int id) {
        Luggage luggage = new Luggage(id);

        ResultSet result = dbmanager.doQuery("SELECT * FROM luggage WHERE id = "+ id);

        try {
            if(result.next()) {
                luggage = resultToLuggage(result);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return luggage;
    }

    private Luggage resultToLuggage(ResultSet result) throws SQLException{
        Luggage luggage = new Luggage(result.getInt("id"));
        // TODO: status, client, airport, brand

        int colorID = result.getInt("color_id");
        luggage.color = colorModel.getColor(colorID);

        // TODO: Hier is een wachtwoord + salt opvragen van een employee inderdaad een beetje nutteloos
        int employeeID = result.getInt("employee_id");
        luggage.employee = employeeModel.getEmployee(employeeID);


        luggage.dimensions = result.getString("dimensions");
        luggage.label = result.getString("label");
        luggage.notes = result.getString("notes");
        luggage.weight = result.getString("weight");

        luggage.foundDate = result.getInt("found_date");
        luggage.returnDate = result.getInt("return_date");
        luggage.createDate = result.getInt("create_date");

        return luggage;
    }

    public static LuggageModel getDefault() {
        return _default;
    }
}
