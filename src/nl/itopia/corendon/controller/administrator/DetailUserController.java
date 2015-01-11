/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.itopia.corendon.controller.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.model.ImageModel;
import nl.itopia.corendon.mvc.Controller;

/**
 *
 * @author Erik
 */
public class DetailUserController extends Controller {
    
    @FXML
    private Label showUsername, showFirstname, showLastname, showRole, showAirport, showContactDetails, showNotes;
    @FXML
    private Button cancelButton;
    
    private ImageModel imageModel;
    private Employee currentEmployee;
    
    public int userId;
    
//    public DetailUserController(int userID) {
//        this(EmployeeModel.getDefault().getEmployee(userID));
//    }
    
    public DetailUserController(int userId) {
        
        this.userId = userId;
        // Set view
        registerFXML("gui/details_user.fxml");
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        currentEmployee = employeemodel.getEmployee(userId);

        imageModel = ImageModel.getDefault();

        showUsername.setText(currentEmployee.username);
        showFirstname.setText(currentEmployee.firstName);
        showLastname.setText(currentEmployee.lastName);
        showRole.setText(currentEmployee.role.getName());
        showAirport.setText(currentEmployee.airport.getName());
        showContactDetails.setText(currentEmployee.contactDetails);
        showNotes.setText(currentEmployee.notes);

        cancelButton.setOnAction(this::cancelHandler);
    
    }

    protected void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
