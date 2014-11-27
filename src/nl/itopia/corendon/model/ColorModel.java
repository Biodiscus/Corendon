package nl.itopia.corendon.model;

import nl.itopia.corendon.data.ChooseItem;
import nl.itopia.corendon.data.Color;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * © 2014, Biodiscus.net Robin
 */
public class ColorModel {
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private static final ColorModel _default = new ColorModel();

    private ColorModel() {}

    public Color getColor(int id) {
        Color color = null;

        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM color WHERE id = "+ id);
            if(result.next()) {
                color = resultToColor(result);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return color;
    }
    
    public Color getColor(String name) {
        Color color = null;

        try {
            ResultSet result = dbmanager.doQuery("SELECT * FROM color WHERE name = '"+ name + "'");
            if(result.next()) {
                color = resultToColor(result);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return color;
    }

    private Color resultToColor(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String hex = result.getString("name");

        return new Color(id, hex);
    }

    public List<Color> getColors() {
        List<Color> colors = new ArrayList<>();
        try {
            String sql = "SELECT * FROM color";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                colors.add(resultToColor(result));
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }

        return colors;
    }

    public ChooseItem colorToChoose(Color color) {
        return new ChooseItem(color.getID(), color.getHex());
    }

    public static ColorModel getDefault() {
        return _default;
    }
}
