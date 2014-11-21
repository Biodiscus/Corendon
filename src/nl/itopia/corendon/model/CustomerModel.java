package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Customer;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * © 2014, Biodiscus.net Robin
 */
public class CustomerModel {
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private static final CustomerModel _default = new CustomerModel();

    private CustomerModel() {}

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
        Customer customer = new Customer(id);
        customer.firstName = result.getString("first_name");
        customer.lastName = result.getString("last_name");
        customer.address = result.getString("address");
        customer.zipcode = result.getString("zip_code");
        customer.state = result.getString("state");
        //todo countryobject
        //customer.countryID = result.getInt("country_id");
        customer.email = result.getString("email");
        customer.phone = result.getString("phone");

        return customer;
    }

    public static CustomerModel getDefault() {
        return _default;
    }
}
