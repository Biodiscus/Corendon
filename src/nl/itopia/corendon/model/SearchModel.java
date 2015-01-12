/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.itopia.corendon.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.utils.Log;

/**
 *
 * @author Jeroentje
 */
public class SearchModel {
    private static final SearchModel _default = new SearchModel();
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();

    private static final int WHERE_DEFAULT_LENGTH = 6;

    private SearchModel() {}


    public static SearchModel getDefault() {
        return _default;
    }
    
    
    public List<Luggage> initSearch(Luggage luggage, LocalDate beginDate, LocalDate endDate) {
        String searchQuery = "SELECT luggage.id FROM luggage {JOIN} {WHERE}";
        String whereQuery = "WHERE ";
        String innerjoinQuery = "";

        if (null != luggage.airport) {
            // The none value has an ID 0
            if (luggage.airport.getID() != 0) {
            /* innerjoin airport for searching */
                innerjoinQuery += "INNER JOIN airport ON luggage.airport_id = airport.id ";
                whereQuery += " airport.name = '" + luggage.airport.getName() + "'";
            }
        }

        if (null != luggage.color) {
            // The none value has an ID 0
            if (luggage.color.getID() != 0) {
            /* innerjoin for color */
                Log.display(luggage.color.getID());
                innerjoinQuery += "INNER JOIN color ON luggage.color_id = color.id ";

                // Only add 'AND' when it's not our first statement
                if (whereQuery.length() > WHERE_DEFAULT_LENGTH) {
                    whereQuery += " AND";
                }

                whereQuery += " color.name = '" + luggage.color.getHex() + "' ";

            }
        }

        if (null != luggage.brand) {
            // The none value has an ID 0
            if(luggage.brand.getID() != 0) {
            /* innerjoin for brand */
                innerjoinQuery += "INNER JOIN brand ON luggage.brand_id=brand.id";
                // Only add 'AND' when it's not our first statement
                if (whereQuery.length() > WHERE_DEFAULT_LENGTH) {
                    whereQuery += " AND";
                }
                whereQuery += " brand.name = '" + luggage.brand.getName() + "' ";
            }
        }

        if (!luggage.label.isEmpty()) {
            /* searching records containing the searchpart for labels */
            // Only add 'AND' when it's not our first statement
            if (whereQuery.length() > 0) {
                whereQuery += " AND";
            }
            whereQuery += " luggage.label LIKE '%" + luggage.label + "%'";
        }

        if (!luggage.notes.isEmpty()) {
            /* searching records containing the searchpart for notes */
            // Only add 'AND' when it's not our first statement
            if (whereQuery.length() > WHERE_DEFAULT_LENGTH) {
                whereQuery += " AND";
            }
            whereQuery += " luggage.notes LIKE '%" + luggage.notes + "%'";
        }

        if (!luggage.weight.isEmpty()) {
            /* searching records containing the searchpart for weigth */
            // Only add 'AND' when it's not our first statement
            if (whereQuery.length() > WHERE_DEFAULT_LENGTH) {
                whereQuery += " AND";
            }
            whereQuery += " luggage.weight LIKE '%" + luggage.weight + "%'";
        }

        if (!"xx cm".equals(luggage.dimensions)) {
            /* @TODO make this working, it's now a quick and dirty fix */
            /* searching recorde containing the searchparts for dimensions */
            // Only add 'AND' when it's not our first statement
            if (whereQuery.length() > WHERE_DEFAULT_LENGTH) {
                whereQuery += " AND";
            }
            whereQuery += " luggage.dimensions LIKE '%" + luggage.dimensions + "%'";
        }

        if (null != beginDate && null != endDate) {
            /* check on create date */
            // Only add 'AND' when it's not our first statement
            if (whereQuery.length() > WHERE_DEFAULT_LENGTH) {
                whereQuery += " AND";
            }
            whereQuery += " (DATE_FORMAT(FROM_UNIXTIME(create_date), '%Y-%m-%d') BETWEEN '" + beginDate + "' AND '" + endDate + "')";
        }

        if (null != luggage.status) {
            /* filter on status */
            // Only add 'AND' when it's not our first statement
            if (whereQuery.length() > WHERE_DEFAULT_LENGTH) {
                whereQuery += " AND";
            }
            whereQuery += " luggage.status_id = " + luggage.status.getID();
        }
        
        /* replace the wildcards for the real join and where statements */
        searchQuery = searchQuery.replace("{JOIN}", innerjoinQuery);
        // If no values are given in the WHERE statement, replace it with an empty character
        // The whereQuery string has a length of 6 by default
        String replaceValue = (whereQuery.length() > WHERE_DEFAULT_LENGTH) ? whereQuery : "";
        searchQuery = searchQuery.replace("{WHERE}", replaceValue);
        Log.display(whereQuery.length(), whereQuery,  searchQuery);
        List<Luggage> luggages = executeQuery(searchQuery);
        
        return luggages;
    }
    
    private List<Luggage> executeQuery(String query) {
        List<Luggage> luggageList = new ArrayList<Luggage>();
        
        LuggageModel luggagemodel = LuggageModel.getDefault();
        
        try {
            ResultSet result = dbmanager.doQuery(query);
            while (result.next()) {
                int id = result.getInt("id");
                Luggage luggage = luggagemodel.getLuggage(id);
                luggageList.add(luggage);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        
        return luggageList;
    }
    
}
