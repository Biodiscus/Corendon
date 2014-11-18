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
public class Color {
    private int id;
    private String hex;

    public Color(int id, String hex) {
        this.id = id;
        this.hex = hex;
    }
    
    public String getHex() {
        return hex;
    }
    public int getID() {
        return id;
    }
}