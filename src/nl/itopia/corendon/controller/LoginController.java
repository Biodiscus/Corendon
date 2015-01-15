package nl.itopia.corendon.controller;

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
import nl.itopia.corendon.utils.IO;
import nl.itopia.corendon.utils.Log;
import nl.itopia.corendon.utils.Validation;

/**
 *
 * @author Jeroentje & wieskueter.com
 */
public class LoginController extends Controller {
    
    @FXML private TextField usernameField;
    @FXML private Button loginButton;
    @FXML private Button forgottenPasswordbutton, helpButton;
    @FXML private TextField passwordField;
    
    private InfoController infoController;
    
    public LoginController(){
        
        // Set view
        registerFXML("gui/Inlogscherm.fxml");
        
        usernameField.setOnKeyPressed(this::usernameFieldKeyPressed);
        passwordField.setOnKeyPressed(this::passwordFieldKeyPressed);
        loginButton.setOnKeyPressed(this::buttonEnterPressed);
        forgottenPasswordbutton.setOnKeyPressed(this::buttonEnterPressed);
        loginButton.setOnAction(this::loginButtonAction);
        forgottenPasswordbutton.setOnAction(this::resetPassword);
        view.fxmlPane.setOnKeyReleased(this::keypressHandler);
        helpButton.setOnAction(this::helpHandler);
    }
    
    private void usernameFieldKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            usernameField.clear();
        } else if(event.getCode() == KeyCode.ENTER) {
            loginAction();
        }
    }
    
    private void buttonEnterPressed(KeyEvent event) {
        if(event.getCode()==KeyCode.ESCAPE) {
            passwordField.clear();
        } else if(event.getCode() == KeyCode.ENTER) {
            loginAction();
        }
    }
    
    private void passwordFieldKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            loginAction();
        }
        if(event.getCode() == KeyCode.ESCAPE) {
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

        // Check if values isn't empty
        if(userName.isEmpty() || password.isEmpty()) {
            // Show error if values are empty
            if(userName.isEmpty()) {
                Validation.errorMessage(usernameField, "Please enter a username");
            }
            if(password.isEmpty()) {
                Validation.errorMessage(passwordField, "Please enter a password");
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
            usernameField.requestFocus();
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
    
    private void resetPassword(ActionEvent e) {
        // Show login reset screen
        addController(new InfoController("Reset Password", "test"));
    }
    
    /**
     * Open F1 InfoWindow
     * @param e 
     */
    private void keypressHandler(KeyEvent e) {
        //opens helpfunction with the f1 key
        if(e.getEventType() == KeyEvent.KEY_RELEASED) {
            if (e.getCode() == KeyCode.F1) {
                // If it's already openend, close it
                if (infoController == null) {
                    openHelp();
                } else {
                    removeController(infoController);
                    infoController = null;
                }
            } 
        }
    }
    
    private void helpHandler(ActionEvent e) {
        if(infoController == null) {
            openHelp();
        }
        //opens help function
    }

    private void openHelp() {
        
        System.out.println(IO.get("help/login.html"));
        //infoController = new InfoController("Reset Password", ));
        
        infoController.setControllerDeleteHandler((obj)->{
            removeController(infoController);
            infoController = null;
        });
        addController(infoController);
    }
}
