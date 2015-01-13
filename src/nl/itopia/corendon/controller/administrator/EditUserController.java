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
import nl.itopia.corendon.utils.Validation;

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
    public boolean canceled = false;
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
        airportDropdownmenu.getSelectionModel().select(employee.airport.getID());
        
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
        canceled = true;
        removeController(this);
    }
    
    private void editHandler(ActionEvent event) {
        
        int errorCount = 0;
        EmployeeModel employeeModel = EmployeeModel.getDefault();
        
        // Password stuff
        String password = passwordInputfield.getText();
        String repeatPassword = repeatpasswordInputfield.getText();
        String userName  = usernameInputfield.getText();
        String firstName  = firstnameInputfield.getText();
        String lastName = lastnameInputfield.getText();
        String contactDetails = contactdetailsInputfield.getText();
        String notes = notesInputfield.getText();        
        String salt = Hashing.generateSaltString();
        
        int userRoleId = roleDropdownmenu.getValue().getKey();
        String userRoleName = roleDropdownmenu.getSelectionModel().getSelectedItem().toString();
        
        int airportId = airportDropdownmenu.getValue().getKey();
        String airportName = airportDropdownmenu.getSelectionModel().getSelectedItem().toString();
        
        this.newEmployee = new Employee(this.userId);
        
        /* update the username temporary so when can check if the user already exists */
        String tempUsername = employee.username;
        boolean userNameChanged = (!tempUsername.equals(userName));
        employee.username = userName;
        
        /* check if username already exists and check if the username is changed by the user */
        if(employeeModel.userExists(employee) && userNameChanged) {
            Validation.errorMessage(usernameInputfield, "Username already exsists.");
            errorCount++;
        }
        
        // Check if passwords match
        if (!password.equals(repeatPassword)) {
            
            passwordInputfield.setText("");
            repeatpasswordInputfield.setText("");
            
            Validation.errorMessage(passwordInputfield, "Passwords doesn't match.");
            Validation.errorMessage(repeatpasswordInputfield, "Passwords doesn't match.");
            errorCount++;
        }
        
        // Check if the password is the correct size
        if (password.length() < 6 && !password.isEmpty()) {
            Validation.errorMessage(passwordInputfield, "Minimum password length is 6 characters.");
            errorCount++;
        }
        
        // Check if firstname is correct size
        if (firstName.length() == 0) {
            Validation.errorMessage(firstnameInputfield, "Firstname is required.");
            errorCount++;
        }
        
        
        if (lastName.length() == 0) {
            Validation.errorMessage(lastnameInputfield, "Lastname is required.");
            errorCount++;
        }
        
        if (userName.length() == 0) {
            Validation.errorMessage(usernameInputfield, "Username is required.");
            errorCount++;
        }
        
        
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

            this.newEmployee.username = userName;
            this.newEmployee.firstName = firstName;
            this.newEmployee.lastName = lastName;
            this.newEmployee.role = new Role(userRoleId, userRoleName);
            this.newEmployee.password = password;
            this.newEmployee.salt = salt;
            this.newEmployee.contactDetails = contactDetails;
            this.newEmployee.notes = notes;
            this.newEmployee.airport = new Airport(airportId,0, airportName);
            EmployeeModel employeemodel = EmployeeModel.getDefault();
            employeemodel.editEmployee(this.newEmployee);
            removeController(this);
        }
    }
    
    @Override
    protected Object destroyReturn() {
        /* canceled callback, when you cancel the dialog it returns true. Else the dialog is naturally closed and returns the employee object */
        return (canceled) ? true : newEmployee;
    }    
}