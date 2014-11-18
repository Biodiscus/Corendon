/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.itopia.corendon.model;

/**
 *
 * @author igor
 */
public class Luggage {
    int id;
    Color color;
    Status status;
    Employee employee;
    Customer customer;
    Airport airport;
    String dimensions;
    Pictures ArrayList<Picture>;
    String label;
    String notes;
    String weight;
    Brand brand;
    int foundDate;
    int returnDate;
    int createDate;
    Luggage(int id, Color color, Status status, Brand brand) {
        this.id = id;
        this.color = color;
        this.status = status;
        this.brand = brand;
    }  
    
}
