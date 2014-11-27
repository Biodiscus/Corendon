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

    private SearchModel() {}


    public static SearchModel getDefault() {
        return _default;
    }
    
    
    public List<Luggage> initSearch(Luggage luggage, LocalDate beginDate, LocalDate endDate) {
        String searchQuery = "SELECT luggage.id FROM luggage {JOIN} {WHERE}";
        String whereQuery = "WHERE ";
        String innerjoinQuery = "";
        
        if(null != luggage.airport){
            /* innerjoin airport for searching */
            innerjoinQuery += "INNER JOIN airport ON luggage.airport_id = airport.id ";
            whereQuery += " airport.name = '" + luggage.airport.getName() + "'";
        }
        
        if(null != luggage.color) {
            /* innerjoin for color */
            innerjoinQuery += "INNER JOIN color ON luggage.color_id = color.id ";
            whereQuery += " AND color.name = '" + luggage.color.getHex() + "' ";
        }
        
        if(!luggage.label.isEmpty()) {
            /* searching records containing the searchpart for labels */
            whereQuery += " AND luggage.label LIKE '%" + luggage.label + "%'";
        }
        
        if(!luggage.notes.isEmpty()) {
            /* searching records containing the searchpart for notes */
            whereQuery += " AND luggage.notes LIKE '%" + luggage.notes + "%'";
        }
        
        if(!luggage.weight.isEmpty()) {
            /* searching records containing the searchpart for weigth */
            whereQuery += " AND luggage.weight LIKE '%" + luggage.weight + "%'";
        }
        
        if(!"xx cm".equals(luggage.dimensions)) {
            /* @TODO make this working, it's now a quick and dirty fix */
            /* searching recorde containing the searchparts for dimensions */
            whereQuery += " AND luggage.dimensions LIKE '%" + luggage.dimensions + "%'";
        }
        
        if(null != beginDate && null != endDate) {
            /* check on create date */
            whereQuery += " AND (DATE_FORMAT(FROM_UNIXTIME(create_date), '%Y-%m-%d') BETWEEN '" + beginDate + "' AND '" + endDate + "')";
        }
        
        if(null != luggage.status) {
            /* filter on status */
            whereQuery += " AND luggage.status_id = " + luggage.status.getID();
        }
        
        /* replace the wildcards for the real join and where statements */
        searchQuery = searchQuery.replace("{JOIN}",innerjoinQuery).replace("{WHERE}", whereQuery);
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
