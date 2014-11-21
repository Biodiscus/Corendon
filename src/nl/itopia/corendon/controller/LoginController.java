package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.Log;

/**
 *
 * @author Jeroentje
 */
public class LoginController extends Controller {
    @FXML private TextField usernameField;
    @FXML private Button loginButton;
    @FXML private TextField passwordField;
    
    public LoginController(){
        registerFXML("gui/Inlogscherm.fxml");  
        
        
   
        Log.display(usernameField);
        
        loginButton.setOnAction(this::loginButtonAction);
    }
    
    @FXML
    private void loginButtonAction(ActionEvent event) {
     // Button was clicked, do something...
//     String userName = usernameField.getText();
//     String password = passwordField.getText();
//
//     if(userName.isEmpty() || password.isEmpty())
//     {
//         /* ERROR, username and or password is empty */
//         Log.display("Error, the required fields are empty!");
//     }else{
//         /* User credentials are filled in, create a employee object and check */
//         login(userName,password);
//     }

        changeController(new MainController(1000, 100));
     
    }
    
    private void login(String userName, String password){
        
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        Employee employee = new Employee(0);

        employee.username = userName;
        employee.password = password;
        
        /* login */
        employee = employeemodel.login(employee);
        
        if(employee != null){
            /* SCHERMEN */
            Log.display("Logged in as: " + employee.username);
//            redirectEmployee(employee);
        }else{
            Log.display("Error, account bestaat niet of u heeft de onjuiste gegevens ingevuld");
        }
    }
    
    private void redirectEmployee(Employee employee){
        /* rollen zijn nog niet volledig geimplementeerd dus dit werkt nog niet goed */
        
        switch(employee.role.getName()){
            case "Administrator":
                /* laad beheerder scherm */
                break;
            case "Employee":
                /* laad medewerker scherm */
                break;
            case "Manager":
                /* laad manager scherm */
                break;
            default: 
                 /* fall back scherm */
        }
    }
}
