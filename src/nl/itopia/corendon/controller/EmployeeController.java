package nl.itopia.corendon.controller;

import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;

/**
 *
 * @author wieskueter.com
 */
public class EmployeeController extends Controller {
    
    public EmployeeController()
    {
        // Set view
        registerFXML("gui/Inlogscherm.fxml");
        
        System.out.println("Employee Controller");
        
        // Trigger button to create new employee
        //createEmployee.setOnAction(this::createNewEmployee);
    }
    
    public void createNewEmployee()
    {
        //String userName = usernameField.getText();
        //String name = nameField.getText();
        //String password = passwordField.getText();
        //String repeatPassword = repeatPasswordField.getText();
        //int role = roleField.getInt();
        //String employeeSince = employeeSinceField.getText();
        //String notes = notesField.getText();
        
        /**
         * Check if username already exists
         * Generate random password
         * 
         */
        
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        employeemodel.createEmployee();
    }
}
