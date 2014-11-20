package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Color;
import nl.itopia.corendon.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * © 2014, Biodiscus.net Robin
 */
public class ColorModel {
    private final DBManager dbmanager = DBManager.getDefault();
    private static final ColorModel _default = new ColorModel();

    private ColorModel() {}

    public Color getColor(int id) {
        Color color = null;

        ResultSet result = dbmanager.doQuery("SELECT * FROM color WHERE id = "+ id);

        try {
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

    public static ColorModel getDefault() {
        return _default;
    }
}
