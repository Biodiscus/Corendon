/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.itopia.corendon.data;

/**
 *
 * @author igor
 */
public class Airport {
    private int id;
    private int code;
    private String name;
    
    public Airport(int id, int code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
    
    private String getName() {
        return name;
    }
    private int getCode() {
        return code;
    }
    private int getID() {
        return id;
    }
}
