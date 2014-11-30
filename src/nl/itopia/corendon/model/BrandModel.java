package nl.itopia.corendon.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.itopia.corendon.data.Airport;
import nl.itopia.corendon.data.Brand;
import nl.itopia.corendon.data.ChooseItem;
import nl.itopia.corendon.utils.Log;

/**
 * © 2014, Biodiscus.net Robin
 */
public class BrandModel {
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private static final BrandModel _default = new BrandModel();

    private BrandModel() {}

    public List<Brand> getBrands() {
        List<Brand> brands = new ArrayList<>();
        try {
            String sql = "SELECT * FROM brand";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                Brand brand = new Brand(id, name);
                brands.add(brand);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return brands;
    }

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

    public ChooseItem brandToChoose(Brand brand) {
        return new ChooseItem(brand.getID(), brand.getName());
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