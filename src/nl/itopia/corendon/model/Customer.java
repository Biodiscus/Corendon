/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.itopia.corendon.model;

/**
 *
 * @author Erik
 */
public class Customer {
    private int id;
    private String name;
    private String firstName;
    private String lastName;
    private String address;
    private String zipcode;
    private String state;
    private Country country;
    private String email;
    private String phone;
    
    public void setFirstName(String a) {
        firstName = a;
    }
    public void setLastName(String a) {
        lastName = a;
    }
    public void setAddress(String a) {
        address = a;
    }
    public void setZipcode(String a) {
        zipcode = a;
    }
    public void setState(String a) {
        state = a;
    }
    public void setCountry(Country a) {
        country = a;
    }
    public void setEmail(String a) {
        email = a;
    }
    public void setPhone(String a) {
        phone = a;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getAddress() {
        return address;
    }
    public String getZipcode() {
        return zipcode;
    }
    public String getState() {
        return state;
    }
    public Country getCountry() {
        return country;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public int getID() {
        return id;
    }
    
}
