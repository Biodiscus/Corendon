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
public class Status {
    int id;
    String hex;
    Status(int id, String hex){
        this.id = id;
        this.hex = hex;
    }
}
