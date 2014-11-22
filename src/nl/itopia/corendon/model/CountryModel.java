package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Country;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * © 2014, Biodiscus.net Robin
 */
public class CountryModel {
    private static final CountryModel _default = new CountryModel();
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();

    private CountryModel() {}

    public Country getCountry(int id) {
        Country country = null;

        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM country WHERE id = "+ id);
            if(result.next()) {
                String name = result.getString("name");
                country = new Country(id, name);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return country;
    }

    public static CountryModel getDefault() {
        return _default;
    }
}
