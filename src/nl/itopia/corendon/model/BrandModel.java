package nl.itopia.corendon.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import nl.itopia.corendon.data.Brand;
import nl.itopia.corendon.utils.Log;

/**
 * © 2014, Biodiscus.net Robin
 */
public class BrandModel {
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private static final BrandModel _default = new BrandModel();

    private BrandModel() {}

    public Brand getBrand(int id) {
        Brand brand = null;

        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM brand WHERE id = "+ id);
            if(result.next()) {
                brand = resultToBrand(result);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return brand;
    }

    private Brand resultToBrand(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String name = result.getString("name");

        return new Brand(id, name);
    }

    public static BrandModel getDefault() {
        return _default;
    }
}