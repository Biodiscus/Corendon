package nl.itopia.corendon.controller.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import nl.itopia.corendon.data.ChooseItem;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;

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
    
    public EditUserController(int userId) {
        
        this.userId = userId;
        // Set view
        registerFXML("gui/edit_user.fxml");
        
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        this.employee = employeemodel.getEmployee(userId);
        
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
    
    private void editHandler(ActionEvent event)
    {
        String userName = usernameInputfield.getText();
        String firstName = firstnameInputfield.getText();
        String lastName = lastnameInputfield.getText();
        String password = passwordInputfield.getText();
        String repeatPassword = repeatpasswordInputfield.getText();
        String contactDetails = contactdetailsInputfield.getText();
        String notes = notesInputfield.getText();
        
        this.newEmployee = new Employee(this.userId);
        this.newEmployee.username = userName;
        this.newEmployee.firstName = firstName;
        this.newEmployee.lastName = lastName;
        this.newEmployee.contactDetails = contactDetails;
        this.newEmployee.notes = notes;
        
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        employeemodel.editEmployee(this.newEmployee);
        removeController(this);
    }
}