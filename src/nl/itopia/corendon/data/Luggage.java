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
<<<<<<< HEAD
    int id;
    Color color;
    Status status;
    nl.itopia.corendon.model.Employee employee;
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
=======
    private int id;
    private Color color;
    private Status status;
    private nl.itopia.corendon.model.Employee employee;
    private Customer customer;
    private Airport airport;
    private String dimensions;
    private List<Pictures> pictureList = new ArrayList<Pictures>();
    private String label;
    private String notes;
    private String weight;
    private Brand brand;
    private int foundDate, returnDate, createDate;
    
    Luggage(int id, Color color, Status status, Employee employee,
    Customer customer, Airport airport, String dimensions, List<Pictures> pictureList,
    String label, String notes, String weight, Brand brand,
    int foundDate, int returnDate, int createDate) {
>>>>>>> 23986103af67ff2a4965699f635db15ae833ecc0
        this.id = id;
        this.color = color;
        this.status = status;
        this.employee = employee;
        this.customer = customer;
        this.airport = airport;
        this.dimensions = dimensions;
        this.pictureList = pictureList;
        this.label = label;
        this.notes = notes;
        this.weight = weight;
        this.brand = brand;
        this.foundDate = foundDate;
        this.returnDate = returnDate;
        this.createDate = createDate;
    }  
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
    
    public void setStatus(Status status){
        this.status = status;
    } 
    
    public Status getStatus() {
        return status;
    }
    
    public void setBrand(Brand brand){
        this.brand = brand;
    }
    
    public Brand getBrand(){
        return brand;
    }
    
    public void setEmployee(nl.itopia.corendon.model.Employee employee){
        this.employee = employee;
    }
    
    public nl.itopia.corendon.model.Employee getEmployee(){
        return employee;
    }
    
    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    
    public Customer getCustomer(){
        return customer;
    }
    
    public void setAirport(Airport airport){
        this.airport = airport;
    }
    
    public Airport getAirport(){
        return airport;
    }
    
    public void setDimensions(String dimensions){
        this.dimensions = dimensions;
    }
    
    public String getDimensions() {
        return dimensions;
    }
    
    public void setPictures(List<Pictures> pictureList) {
        this.pictureList = pictureList;
    }
    
    public List<Pictures> getPictureList (){
        return pictureList;
    }
    
    public void setLabel(String label){
        this.label = label;
    }
    
    public String getLabel(){
        return label;
    }
    
    public void setNotes(String notes){
        this.notes = notes;
    }
    
    public String getNotes(){
        return notes;
    }
    
    public void setWeight (String weight) {
        this.weight = weight;
    }
    
    public String getWeight(){
        return weight;
    }
    
    public void setFoundDate(int foundDate){
        this.foundDate = foundDate;
    }
    
    public int getFoundDate(){
        return foundDate;
    }
    
    public void setReturnDate(int returnDate){
        this.returnDate = returnDate;
    }
    public int getReturnDate(){
        return returnDate;
    }
    
    public void setCreateDate(int createDate){
        this.createDate = createDate;
    }
    
    public int getCreateDate() {
        return createDate;
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
