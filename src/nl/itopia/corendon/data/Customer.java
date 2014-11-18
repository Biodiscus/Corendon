/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.itopia.corendon.data;

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
    
    public void setFirstName(String value) {
        firstName = value;
    }
    public void setLastName(String value) {
        lastName = value;
    }
    public void setAddress(String value) {
        address = value;
    }
    public void setZipcode(String value) {
        zipcode = value;
    }
    public void setState(String value) {
        state = value;
    }
    public void setCountry(Country value) {
        country = value;
    }
    public void setEmail(String value) {
        email = value;
    }
    public void setPhone(String value) {
        phone = value;
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
