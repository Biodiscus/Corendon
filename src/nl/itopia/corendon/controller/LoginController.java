package nl.itopia.corendon.controller;

//import java.awt.event.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import nl.itopia.corendon.controller.administrator.AdministratorController;
import nl.itopia.corendon.controller.employee.EmployeeController;
import nl.itopia.corendon.controller.manager.ManagerController;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.Log;

/**
 *
 * @author Jeroentje & wieskueter.com
 */
public class LoginController extends Controller {
    
    @FXML private TextField usernameField;
    @FXML private Button loginButton;
    @FXML private Button forgottenPasswordbutton;
    @FXML private TextField passwordField;
    
    public LoginController(){
        
        // Set view
        registerFXML("gui/Inlogscherm.fxml");

        usernameField.setOnKeyReleased(this::usernameFieldKeyReleased);
        passwordField.setOnKeyReleased(this::passwordFieldKeyReleased);
        loginButton.setOnKeyReleased(this::buttonEnterReleased);
        forgottenPasswordbutton.setOnKeyReleased(this::buttonEnterReleased);
        loginButton.setOnAction(this::loginButtonAction);
        forgottenPasswordbutton.setOnAction(this::resetPassword);
    }
    
    private void usernameFieldKeyReleased(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER) {
            loginAction();
        }
        if(event.getCode()==KeyCode.ESCAPE) {
            usernameField.clear();
        }
    }
    
    private void buttonEnterReleased(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER) {
            loginAction();
        }
    }
    
    private void passwordFieldKeyReleased(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER) {
            loginAction();
        }
        if(event.getCode()==KeyCode.ESCAPE) {
            passwordField.clear();
        }
    }
    
    private void loginButtonAction(ActionEvent event) {
        loginAction();
    }
    
    private void loginAction() {
        // Store username and password in variable
        String userName = usernameField.getText();
        String password = passwordField.getText();

        // Check if values aren't empty
        if(userName.isEmpty() || password.isEmpty()) {
            // Show error if values are empty
            if(userName.isEmpty()) {
                usernameField.setPromptText("Please enter a username");
                usernameField.getStyleClass().add("error_prompt");
            }

            if(password.isEmpty()) {
                passwordField.setPromptText("Please enter a password");
                passwordField.getStyleClass().add("error_prompt");
            }
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("We couldn't log you in as: "+userName);
            alert.showAndWait();
        }
    }

    private void redirectEmployee(Employee employee) {
        /* User is logged in, redirect user to the right controller by role name */

        switch (employee.role.getName()) {
            case "Administrator":
                changeController(new AdministratorController());
            break;
            case "Employee":
                changeController(new EmployeeController());
            break;
            case "Manager":
                changeController(new ManagerController());
            break;
            default: 
                changeController(new LoginController());
        }
    }
    
    private void resetPassword(ActionEvent event)
    {
        // Show login reset screen
        changeController(new InfoController());
        //changeController(new AdministratorController());
    }
}