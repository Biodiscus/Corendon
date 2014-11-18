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
public class Status {
    private int id;
    private String name;
    
    /*
    private String hex;
    Status(int id, String hex){
        this.id = id;
        this.hex = hex;
    }
    */
    
    public String getName() {
        return name;
    }
    public int getID() {
        return id;
    }
}
