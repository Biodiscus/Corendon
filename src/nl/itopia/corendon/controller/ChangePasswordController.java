package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.Hashing;
import nl.itopia.corendon.utils.Validation;

/**
 *
 * @author Kueter
 */
public class ChangePasswordController extends Controller {
    
    @FXML PasswordField oldPasswordInputfield, newPasswordInputfield, repeatPasswordInputfield;
    @FXML Button saveButton, cancelButton;
    private Employee newEmployee;
    
    public ChangePasswordController() {
        registerFXML("gui/change_password.fxml");
        
        saveButton.setOnAction(this::changePassword);
        cancelButton.setOnAction(this::cancelHandler);
    }
    
    protected void changePassword(ActionEvent e) {
        
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        
        int errorCount = 0;
        
        // Password stuff
        String oldPassword = oldPasswordInputfield.getText();
        String oldPasswordHashed = Hashing.sha256(oldPassword + employeemodel.currentEmployee.salt);
        String password = newPasswordInputfield.getText();
        String repeatPassword = repeatPasswordInputfield.getText();
        String salt = Hashing.generateSaltString();

        this.newEmployee = employeemodel.currentEmployee;
        
        if( employeemodel.currentEmployee.password.equals(oldPasswordHashed) && password.length() > 6) {
            
            if( !password.equals(repeatPassword) ) {
                Validation.errorMessage(newPasswordInputfield, "Password don't match.");
                Validation.errorMessage(repeatPasswordInputfield, "Password don't match.");
                errorCount++;
            }
        } else {
            errorCount++;
        }
        
        if(errorCount == 0) {

            this.newEmployee.password = Hashing.sha256(password + salt);
            this.newEmployee.salt = salt;
            
            employeemodel.editEmployee(this.newEmployee);
            removeController(this);
        }
    }
    
    protected void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
