package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import nl.itopia.corendon.controller.employee.AddLuggageController;
import nl.itopia.corendon.controller.employee.EmployeeController;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.Log;

/**
 *
 * @author Jeroentje & Wies
 */
public class LoginController extends Controller {
    
    @FXML private TextField usernameField;
    @FXML private Button loginButton;
    @FXML private Button forgottenPasswordbutton;
    @FXML private TextField passwordField;
    
    public LoginController(){
        
        registerFXML("gui/Inlogscherm.fxml");

        Log.display(usernameField);

        loginButton.setOnAction(this::loginButtonAction);
        forgottenPasswordbutton.setOnAction(this::resetPassword);
    }

    @FXML
    private void loginButtonAction(ActionEvent event) {
        // Store username and password in variable
        String userName = usernameField.getText();
        String password = passwordField.getText();

        // Check if values aren't empty
        if(userName.isEmpty() || password.isEmpty())
        {
            // Show error if values are empty
            Log.display("Error, the required fields are empty!");
        } else {
           /* User credentials are filled in, create a employee object and check */
            login(userName, password);
        }
    }

    private void login(String userName, String password) {

        EmployeeModel employeemodel = EmployeeModel.getDefault();
        Employee employee = new Employee(0);

        employee.username = userName;
        employee.password = password;
        
        /* login */
        employee = employeemodel.login(employee);

        if(employee != null) {
            /* SCHERMEN */
            Log.display("Logged in as: " + employee.username + " ROLE: " + employee.role.getName());
            employeemodel.currentEmployee = employee;
            redirectEmployee(employee);
        } else {
            Log.display("Error, account does not exist or entered data is incorrect.");
        }
    }

    private void redirectEmployee(Employee employee) {
        /* User is logged in, redirect user to the right controller by role name */

        switch (employee.role.getName()) {
            case "Administrator":
                changeController(new AdministratorController());
            break;
            case "Employee":
                // Show employee screen
            break;
            case "Manager":
                // Show manager screen
            break;
            default: 
                // Show default screen
        }
    }
    
    private void resetPassword(ActionEvent event)
    {
        // Show login reset screen
        //changeController(new InfoController());
        changeController(new AdministratorController());
    }
}