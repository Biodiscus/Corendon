/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.itopia.corendon.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author igor
 */
public class Luggage {
    private int id;
    public Color color;
    public Status status;
    public Employee employee;
    public Customer customer;
    public Airport airport;
    public String dimensions;
    public List<Pictures> pictures;
    public String label;
    public String notes;
    public String weight;
    public Brand brand;
    public int foundDate;
    public int returnDate;
    public int createDate;

    public Luggage(int id) {
        this.id = id;
    }

    /*
    public List<Luggage> getLuggageList() {
        List<Luggage> luggageList = new ArrayList<Luggage>();
        try {
            String sql = "SELECT * FROM luggage";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                luggageList.add(new Luggage(result.getInt("id"),
                        //getcolor
                        //getstatus
            }
        } catch (SQLException e) {
            System.out.println(Dbmanager.SQL_EXCEPTION + e.getMessage());
        }
        return luggageList;
    }    
    */
}
