package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Customer;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * © 2014, Biodiscus.net Robin
 */
public class CustomerModel {
    private static final CustomerModel _default = new CustomerModel();
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private final CountryModel countryModel;
    private final char COMMA = ',';

    private CustomerModel() {
        countryModel = CountryModel.getDefault();
    }

    public Customer getCustomer(int id) {
        Customer customer = null;

        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM customer WHERE id = "+ id);
            if(result.next()) {
                customer = resultToCustomer(result);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return customer;
    }

    private Customer resultToCustomer(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        int countryID = result.getInt("country_id");

        Customer customer = new Customer(id);
        customer.firstName = result.getString("first_name");
        customer.lastName = result.getString("last_name");
        customer.address = result.getString("address");
        customer.zipcode = result.getString("zip_code");
        customer.state = result.getString("state");
        customer.country = countryModel.getCountry(countryID);
        customer.email = result.getString("email");
        customer.phone = result.getString("phone");

        return customer;
    }
    
    public boolean insertCustomer(Customer customer) {
        String values = customer.firstName + this.COMMA + customer.lastName + this.COMMA + customer.address + this.COMMA + customer.zipcode + this.COMMA + customer.state + this.COMMA + customer.country.getID() + this.COMMA + customer.email + this.COMMA + customer.phone ;
        String insertQuery = "INSERT INTO customer "
                                + " VALUES(" + values + ")";
        try {
            dbmanager.insertQuery(insertQuery);
            return true;
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
            return false;
        }        
        
    }

    public static CustomerModel getDefault() {
        return _default;
    }
}
