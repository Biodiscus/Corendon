/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.itopia.corendon.controller;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.itopia.corendon.data.Employee;
//import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.model.DatabaseManager;
import nl.itopia.corendon.model.EmployeeModel;
//import nl.itopia.corendon.model.LuggageModel;
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
    
    @FXML private TableColumn <Employee,String>userIDtable;
    @FXML private TableColumn <Employee,String>usernametable;
    @FXML private TableColumn <Employee,String>userLoginIDtable;
    
    @FXML private TextField usernameInputfield;
    @FXML private TextField nameInputfield;
    @FXML private TextField passwordInputfield;
    @FXML private TextField repeatpasswordInputfield;
    
    @FXML private Button addButton;
    
    public AdministratorController() {
        
        registerFXML("gui/Overview_administrator.fxml");
        System.out.println("Administrator overview - gui/Overview_administrator.fxml");
    }
    
    public void createNewEmployee(ActionEvent event)
    {
        // Set view
        //registerFXML("gui/add_user.fxml");
        
        System.out.println("Employee Controller");
        
        // Trigger button to create new employee
        addButton.setOnAction(this::createNewEmployee);
        
        System.out.println("Creating new employee...");
        
        String userName = usernameInputfield.getText();
        String name = nameInputfield.getText();
        String password = passwordInputfield.getText();
        String repeatPassword = repeatpasswordInputfield.getText();
        //int role = roleDropdownmenu.getInt();
        //String employeeSince = employeeSinceField.getText();
        //String notes = notesInputfield.getText();
        
        System.out.println("EmployeeController.createNewEmployee....");
        /**
         * Check if username already exists
         * Generate random password
         * 
         */
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        employeemodel.createEmployee(name);
    }
    void initializeTable(){
        //Create columns and set their datatype
        
        userIDtable.setCellValueFactory(new PropertyValueFactory<Employee, String>("ID"));
        usernametable.setCellValueFactory(new PropertyValueFactory<Employee, String>("Username"));
        userLoginIDtable.setCellValueFactory(new PropertyValueFactory<Employee, String>("Login ID"));
        
        //MAAK LIST VAN LUGGAGE OBJECTS EN VUL DE KOLOMMEN
        //LuggageModel luggageModel = LuggageModel.getDefault(); 
        //List<Employee> employeeList = EmployeeModel.getAllEmployee();
        //luggageInfo.getItems().setAll(luggageList);
    }
}
