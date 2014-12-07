/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.itopia.corendon.components;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import nl.itopia.corendon.data.Airport;
import nl.itopia.corendon.data.ChooseItem;
import nl.itopia.corendon.data.Role;
import nl.itopia.corendon.model.AirportModel;
import nl.itopia.corendon.model.RoleModel;

/**
 *
 * @author Kueter
 */
public class EmployeeDropdownLists {
    
    @FXML private static ChoiceBox<ChooseItem> airportDropdownmenu;
    private static final List<Role> roleList = RoleModel.getDefault().getRoles();
    private static final List<Airport> airportList = AirportModel.getDefault().getAirports();
    
    /*public static EmployeeDropdownLists()
    {
        // Populate dropdownmenu with role values
        this.roleList = RoleModel.getDefault().getRoles();
        this.airportList = AirportModel.getDefault().getAirports();
    }*/
    
    public static void roleDropdown(ChoiceBox<ChooseItem> roleDropdownmenu)
    {
        for (Role role : roleList) {
            System.out.println("TEST");
            roleDropdownmenu.getItems().add(new ChooseItem(role.getID(), role.getName()));
        }
        roleDropdownmenu.getSelectionModel().select(0);
    }
    
    public static void airportDropdown()
    {
        for (Airport airport : airportList) {
            airportDropdownmenu.getItems().add(new ChooseItem(airport.getID(), airport.getName()));
        }
        airportDropdownmenu.getSelectionModel().select(0);               
    }
    
    public static void test()
    {
        System.out.println("TEST");
    }
}
