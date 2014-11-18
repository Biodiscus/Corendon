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
public class Brand {
    private int id;
    private String name;
    
    public Brand(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    public int getID() {
        return id;
    }
}
