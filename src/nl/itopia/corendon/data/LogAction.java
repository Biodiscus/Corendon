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
    private int date;
    private Action action;
    private Employee employee;
    private Luggage luggage;
    
    public int getDate() {
        return date;
    }
    public Action getAction() {
        return action;
    }
    public Employee getEmployee() {
        return employee;
    }
    public Luggage getLuggage() {
        return luggage;
    }
    public int getID() {
        return id;
    }
}
