package nl.itopia.corendon.controller.administrator;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import nl.itopia.corendon.data.Airport;
import nl.itopia.corendon.data.ChooseItem;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.data.Role;
import nl.itopia.corendon.model.AirportModel;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.model.RoleModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.Hashing;

/**
 * @author wieskueter.com
 */
public class EditUserController extends Controller {
    
    @FXML private TextField usernameInputfield, firstnameInputfield, lastnameInputfield, passwordInputfield, repeatpasswordInputfield,
            contactdetailsInputfield, notesInputfield;
    @FXML private ChoiceBox<ChooseItem> roleDropdownmenu, airportDropdownmenu;
    @FXML private Button editButton, cancelButton;

    public Employee employee, newEmployee;
    public int userId;
  
    private final List<Role> roleList;
    private final List<Airport> airportList;
    
    public EditUserController(int userId) {
        
        this.userId = userId;
        // Set view
        registerFXML("gui/edit_user.fxml");
        
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        this.employee = employeemodel.getEmployee(userId);
        
        // Populate dropdownmenu with role values
        roleList = RoleModel.getDefault().getRoles();
 
        for (Role role : roleList) {
            roleDropdownmenu.getItems().add(new ChooseItem(role.getID(), role.getName()));
        }
 
        airportList = AirportModel.getDefault().getAirports();
        for (Airport airport : airportList) {
            airportDropdownmenu.getItems().add(new ChooseItem(airport.getID(), airport.getName()));
        }
        
        roleDropdownmenu.getSelectionModel().select(employee.role.getID()-1);
        airportDropdownmenu.getSelectionModel().select(employee.airport.getID()-1);
        
        // Set field data from object being edited
        usernameInputfield.setText(employee.username);
        firstnameInputfield.setText(employee.firstName);
        lastnameInputfield.setText(employee.lastName);
        contactdetailsInputfield.setText(employee.contactDetails);
        notesInputfield.setText(employee.notes);
        
        editButton.setOnAction(this::editHandler);
        cancelButton.setOnAction(this::cancelHandler);
    }

    private void cancelHandler(ActionEvent event) {
        removeController(this);
    }
    
    private void editHandler(ActionEvent event) {
        
        int errorCount = 0;
        
        // Password stuff
        String password = passwordInputfield.getText();
        String repeatPassword = repeatpasswordInputfield.getText();
        String salt = Hashing.generateSaltString();
        
        System.out.println(password);
        
        int userRole = roleDropdownmenu.getValue().getKey();
        
        this.newEmployee = new Employee(this.userId);
        
        if(password.length() < 6) {
            
            password = this.newEmployee.password;
            salt = this.newEmployee.salt;
        } else {
            password = passwordInputfield.getText();
            salt = Hashing.generateSaltString();
            password = Hashing.sha256(password + salt);
        }
        
        /**
         * If there are no errors, edit the user
         */
        if(errorCount == 0) {
            
            this.newEmployee.username = usernameInputfield.getText();
            this.newEmployee.firstName = firstnameInputfield.getText();
            this.newEmployee.lastName = lastnameInputfield.getText();
            this.newEmployee.role = new Role(userRole, "none");
            this.newEmployee.password = password;
            this.newEmployee.salt = salt;
            this.newEmployee.contactDetails = contactdetailsInputfield.getText();
            this.newEmployee.notes = notesInputfield.getText();

            EmployeeModel employeemodel = EmployeeModel.getDefault();
            employeemodel.editEmployee(this.newEmployee);
            removeController(this);
        }
    }
}