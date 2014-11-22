package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import nl.itopia.corendon.mvc.Controller;

/**
 *
 * @author wieskueter.com
 */
public class EmployeeController extends Controller {
    
    @FXML private TextField usernameInputfield;
    @FXML private Button addButton;
    
    public EmployeeController()
    {
        // Set view
        registerFXML("gui/add_user.fxml");
        
        System.out.println("Employee Controller");
        
        // Trigger button to create new employee
        addButton.setOnAction(this::createNewEmployee);
    }
    
    public void createNewEmployee(ActionEvent event)
    {
        System.out.println("Creating new employee...");
        String userName = usernameInputfield.getText();
        
        //String name = nameInputfield.getText();
        //String password = passwordInputfield.getText();
        //String repeatPassword = repeatpasswordInputfield.getText();
        //int role = roleDropdownmenu.getInt();
        //String employeeSince = employeeSinceField.getText();
        //String notes = notesInputfield.getText();
        
        System.out.println(userName);
        /**
         * Check if username already exists
         * Generate random password
         * 
         */
        
        //EmployeeModel employeemodel = EmployeeModel.getDefault();
        //employeemodel.createEmployee();
    }
}
