package nl.itopia.corendon.controller;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.data.KeyValue.KeyValuePair;
import nl.itopia.corendon.data.Role;
import nl.itopia.corendon.data.table.TableUser;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.model.RoleModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.Hashing;
import nl.itopia.corendon.utils.Log;
//import nl.itopia.corendon.model.LuggageModel;

/**
 *
 * @author wieskueter.com
 */
public class CreateUserController extends Controller {
    
    @FXML private TextField usernameInputfield;
    @FXML private TextField nameInputfield;
    @FXML private TextField passwordInputfield;
    @FXML private TextField repeatpasswordInputfield;
    //@FXML private ChoiceBox roleDropdownmenu;
    @FXML private ChoiceBox<KeyValuePair> roleDropdownmenu = new ChoiceBox<KeyValuePair>();
    @FXML private Button addButton;
    
    private final List<Role> roleList;
    
    public CreateUserController()
    {
        // Set view
        registerFXML("gui/add_user.fxml");
        
        // Populate dropdownmenu with role values
        
        roleList = RoleModel.getDefault().getRoles();
        
        for(Role role : roleList) {
            
            //TableUser user = new TableUser(employee.firstName, employee.lastName, employee.username);
            
            roleDropdownmenu.getItems().add(new KeyValuePair(role.id, role.name));
        }
        
        // Trigger button to create new employee
        addButton.setOnAction(this::createNewEmployee);
    }
    
    public void createNewEmployee(ActionEvent event)
    {
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        String errors = "";
        System.out.println("Creating new employee...");
        
        String userName = usernameInputfield.getText();
        String name = nameInputfield.getText();
        String password = passwordInputfield.getText();
        String repeatPassword = repeatpasswordInputfield.getText();
        //String userRole = (String) roleDropdownmenu.getValue();
        //String employeeSince = employeeSinceField.getText();
        //String notes = notesInputfield.getText();
        
        //System.out.println("Selected role: "+ userRole);
        
        
        password = Hashing.sha256(password);
        repeatPassword = Hashing.sha256(repeatPassword);
        
        // Check if username already exists
        //employeemodel.UsernameExists(String username);
        
        // Check if passwords match
        if(!password.equals(repeatPassword))
        {
            Log.display("Passwords doesn't match");
        }
        
        if(errors.equals(""))
        {
           System.out.println(password +" is equal to "+ repeatPassword);

            /**
             * Check if username already exists
             * Generate random password
             */
            employeemodel.createEmployee(userName, password);
        }
    }
}
