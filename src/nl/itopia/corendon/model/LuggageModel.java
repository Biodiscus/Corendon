package nl.itopia.corendon.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.utils.Log;

/**
 * © 2014, Biodiscus.net Robin
 */
public class LuggageModel {
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private static final LuggageModel _default = new LuggageModel();

    private EmployeeModel employeeModel;
    private ColorModel colorModel;
    private BrandModel brandModel;
    private CustomerModel customerModel;

    private LuggageModel() {
        employeeModel = EmployeeModel.getDefault();
        colorModel = ColorModel.getDefault();
        brandModel = BrandModel.getDefault();
    }

    public void addLuggage(Luggage luggage) {
        int color_id = luggage.color.getID();
        int status_id = luggage.status.getID();
        int employee_id = luggage.employee.getID();
        int client_id = luggage.customer.getID(); // TODO: In the database this is called client instead of customer
        int airport_id = luggage.airport.getID();
        int brand_id = luggage.brand.getID();


        String query = "INSERT INTO luggage " +
                "(color_id, status_id, employee_id, client_id, airport_id, dimensions, label, notes, weight, brand_id, found_date, return_date, create_date) " +
                "VALUES" +
                "('%d', '%d', '%d', '%d', '%d', '%s', '%s', '%s', '%s', '%d', '%d', '%d', '%d')";

        String finalQuery = String.format(
                query, color_id, status_id, employee_id, client_id, airport_id, luggage.dimensions, luggage.label,
                luggage.notes, luggage.weight, brand_id, luggage.foundDate, luggage.returnDate, luggage.createDate
        );

        try {
            dbmanager.insertQuery(finalQuery);
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }

    public void editLuggage(Luggage luggage) {
        int color_id = luggage.color.getID();
        int status_id = luggage.status.getID();
        int employee_id = luggage.employee.getID();
        int client_id = luggage.customer.getID();
        int airport_id = luggage.airport.getID();
        int brand_id = luggage.brand.getID();

        String query = "UPDATE luggage " +
                "SET color_id='%d', status_id='%d', employee_id='%d', client_id='%d', airport_id='%d', dimensions='%s', " +
                "label='%s', notes='%s', weight='%s', brand_id='%d', found_date='%d', return_date='%d', create_date='%d'" +
                "WHERE id='%d'";

        String finalQuery = String.format(
                query, color_id, status_id, employee_id, client_id, airport_id, luggage.dimensions, luggage.label,
                luggage.notes, luggage.weight, brand_id, luggage.foundDate, luggage.returnDate, luggage.createDate, luggage.getID()
        );
        Log.display(finalQuery);

        try {
            dbmanager.updateQuery(finalQuery);
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }

    public Luggage getLuggage(int id) {
        Luggage luggage = new Luggage(id);

        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM luggage WHERE id = "+ id);

            if(result.next()) {
                luggage = resultToLuggage(result);
            }

            return luggage;
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
            return null;
        }
    }

    private Luggage resultToLuggage(ResultSet result) throws SQLException{
        Luggage luggage = new Luggage(result.getInt("id"));
        // TODO: status, airport, customer (IN CUSTOMER CLASS NOG GEEN LINK NAAR COUNTRY)

        int colorID = result.getInt("color_id");
        luggage.color = colorModel.getColor(colorID);
        int brandID = result.getInt("brand_id");
        luggage.brand = brandModel.getBrand(brandID);
        int customerID = result.getInt("cusomter_id");
        luggage.customer = customerModel.getCustomer(customerID);
        

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
    
    //Gets all luggagedata from DB, puts the data fields in luggage object,
    //and puts all luggageobjects in ArrayList of Luggage objects
    public List<Luggage> getAllLuggage() {
        List<Luggage> luggageList = new ArrayList<Luggage>();
        try {
            String sql = "SELECT * FROM luggage";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                Luggage luggage = getLuggage(id);
                luggageList.add(luggage);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        return luggageList;
    }   

    public static LuggageModel getDefault() {
        return _default;
    }
}
