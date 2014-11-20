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
public class LogAction {
    private int id;
    public int date;
    public Action action;
    public Employee employee;
    public Luggage luggage;

    public LogAction(int id) {
        this.id = id;
    }
    
    public int getID() {
        return id;
    }
}
