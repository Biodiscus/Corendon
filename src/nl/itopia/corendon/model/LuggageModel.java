package nl.itopia.corendon.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.data.LogAction;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.utils.DateUtil;
import nl.itopia.corendon.utils.Log;

/**
 * © 2014, Biodiscus.net Robin
 */
public class LuggageModel {
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private static final LuggageModel _default = new LuggageModel();

    private final EmployeeModel employeeModel;
    private final ColorModel colorModel;
    private final BrandModel brandModel;
    private final CustomerModel customerModel;
    private final AirportModel airportModel;
    private final StatusModel statusModel;
    private final ActionModel actionModel;
    private final LogModel logModel;


    private LuggageModel() {
        employeeModel = EmployeeModel.getDefault();
        colorModel = ColorModel.getDefault();
        brandModel = BrandModel.getDefault();
        customerModel = CustomerModel.getDefault();
        airportModel = AirportModel.getDefault();
        statusModel = StatusModel.getDefault();
        actionModel = ActionModel.getDefault();
        logModel = LogModel.getDefault();
    }

    // Sets the current luggage id, to the inserted ID
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


            // After inserting the item, get the last added luggage
            // This way we can set the correct ID to the new luggage
            ResultSet result = dbmanager.doQuery("SELECT LAST_INSERT_ID()");
            if(result.next()) {
                luggage.setID(result.getInt(1));

                // Add a new action of an user adding the luggage
                LogAction log = new LogAction(-1);
                log.date = DateUtil.getCurrentTimeStamp();
                log.action = actionModel.getAction("Added luggage");
                log.employee = luggage.employee;
                log.luggage = luggage;
                logModel.insertAction(log);
            } else {
                // ERROR!
            }

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

        try {
            dbmanager.updateQuery(finalQuery);

            // Add a new action of an user editing the luggage
            LogAction log = new LogAction(-1);
            log.date = DateUtil.getCurrentTimeStamp();
            log.action = actionModel.getAction("Edited luggage");
            log.employee = luggage.employee;
            log.luggage = luggage;
            logModel.insertAction(log);
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
    
    public Luggage getLuggageByLabel(String label) {
        Luggage luggage = new Luggage();

        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM luggage WHERE label = '" + label + "'");

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
        // TODO: customer (IN CUSTOMER CLASS NOG GEEN LINK NAAR COUNTRY)

        int colorID = result.getInt("color_id");
        int statusID = result.getInt("status_id");
        int employeeID = result.getInt("employee_id");
        int customerID = result.getInt("client_id");
        int airportID = result.getInt("airport_id");
        int brandID = result.getInt("brand_id");


        luggage.brand = brandModel.getBrand(brandID);
        luggage.customer = customerModel.getCustomer(customerID);
        luggage.color = colorModel.getColor(colorID);
        luggage.status = statusModel.getStatus(statusID);
        luggage.employee = employeeModel.getEmployee(employeeID); // TODO: Hier is een wachtwoord + salt opvragen van een employee inderdaad een beetje nutteloos
        luggage.customer = customerModel.getCustomer(customerID);
        luggage.airport = airportModel.getAirport(airportID);
        luggage.dimensions = result.getString("dimensions");
        luggage.label = result.getString("label");
        luggage.notes = result.getString("notes");
        luggage.weight = result.getString("weight");
        luggage.brand = brandModel.getBrand(brandID);

        luggage.foundDate = result.getInt("found_date");
        luggage.returnDate = result.getInt("return_date");
        luggage.createDate = result.getInt("create_date");

        return luggage;
    }

    public void deleteLuggage(int id) {
        String deleteQuery = "UPDATE luggage SET deleted = '1' WHERE id = '" + id + "'";
        try {
            dbmanager.updateQuery(deleteQuery);
            Luggage luggage = getLuggage(id);

            // Add a new action of an user editing the luggage
            LogAction log = new LogAction(-1);
            log.date = DateUtil.getCurrentTimeStamp();
            log.action = actionModel.getAction("Deleted luggage");
            log.employee = luggage.employee;
            log.luggage = luggage;
//            logModel.insertAction(log);
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }
    
    public void revertLuggage(int id) {
        String deleteQuery = "UPDATE luggage SET deleted = '0' WHERE id = '" + id + "'";
        try {
            dbmanager.updateQuery(deleteQuery);
            Luggage luggage = getLuggage(id);

            // Add a new action of an user editing the luggage
            LogAction log = new LogAction(-1);
            log.date = DateUtil.getCurrentTimeStamp();
            log.action = actionModel.getAction("Deleted luggage");
            log.employee = luggage.employee;
            log.luggage = luggage;
//            logModel.insertAction(log);
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }
    
    public void permDeleteLuggage(int id) {
        String deleteQuery = "DELETE FROM luggage WHERE id = '" + id + "'";
        try {
            dbmanager.updateQuery(deleteQuery);
            Luggage luggage = getLuggage(id);

//             Add a new action of an user editing the luggage
            LogAction log = new LogAction(-1);
            log.date = DateUtil.getCurrentTimeStamp();
            log.action = actionModel.getAction("Permanent deleted luggage");
            log.employee = luggage.employee;
            log.luggage = luggage;
//            logModel.insertAction(log);
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }
    
    public List<Luggage> getAllDeletedLuggage() {
        List<Luggage> deletedLuggageList = new ArrayList<Luggage>();
        try {
            String sql = "SELECT * FROM luggage WHERE deleted='1'";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                Luggage luggage = resultToLuggage(result);
                deletedLuggageList.add(luggage);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        return deletedLuggageList;
    }
    
    public boolean labelExists(String label) {
        int numRecords = 0;
        try {
            ResultSet result = dbmanager.doQuery("SELECT count(*) as labelcounter FROM luggage WHERE label = '" + label + "'");
            if (result.next()) {
                String labelCounter = result.getString("labelcounter");
                numRecords = Integer.parseInt(labelCounter);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        
        return numRecords == 1;
    }
    
    //Gets all luggagedata from DB, puts the tableData fields in luggage object,
    //and puts all luggageobjects in ArrayList of Luggage objects
    public List<Luggage> getAllLuggage() {
        List<Luggage> luggageList = new ArrayList<Luggage>();
        try {
            String sql = "SELECT * FROM luggage WHERE deleted='0'";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                Luggage luggage = resultToLuggage(result);
                luggageList.add(luggage);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        return luggageList;
    }

    
     public List<Luggage> getAllLostLuggage() {
        List<Luggage> lostLuggageList = new ArrayList<Luggage>();
        try {
            String sql = "SELECT * FROM luggage WHERE status_id ='1'";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                Luggage luggage = resultToLuggage(result);
                lostLuggageList.add(luggage);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        return lostLuggageList;
    }
     
     
          public List<Luggage> getAllFoundLuggage() {
        List<Luggage> foundLuggageList = new ArrayList<Luggage>();
        try {
            String sql = "SELECT * FROM luggage WHERE status_id ='2'";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                Luggage luggage = resultToLuggage(result);
                foundLuggageList.add(luggage);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        return foundLuggageList;
    }
          
          
          
               public List<Luggage> getAllResolvedLuggage() {
        List<Luggage> resolvedLuggageList = new ArrayList<Luggage>();
        try {
            String sql = "SELECT * FROM luggage WHERE status_id ='3'";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                Luggage luggage = resultToLuggage(result);
                resolvedLuggageList.add(luggage);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        return resolvedLuggageList;
    }
    
    public static LuggageModel getDefault() {
        return _default;
    }
}
