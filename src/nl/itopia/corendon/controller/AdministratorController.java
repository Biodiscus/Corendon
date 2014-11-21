/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.model.DatabaseManager;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.mvc.View;
import nl.itopia.corendon.utils.Log;

/**
 *
 * @author Erik
 */
public class AdministratorController extends Controller {
    //private AdministratorView view;
    private Employee employee;
    private DatabaseManager dbManager;
    
    @FXML public Button test_button;

    public AdministratorController(int width, int height) {
        registerFXML("gui/TestGUI.fxml");
    }

    public void handle(ActionEvent e) {
        Log.display("Button clicked", e);
    }

    @Override
    public View getView() {
        return view;
    }
}
