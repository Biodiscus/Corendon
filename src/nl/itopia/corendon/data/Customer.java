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
    public String name;
    public String firstName;
    public String lastName;
    public String address;
    public String zipcode;
    public String state;
    public Country country;
    public String email;
    public String phone;

    public Customer(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
    
}
