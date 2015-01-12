package nl.itopia.corendon.controller.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.itopia.corendon.data.table.TableUser;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.model.ImageModel;
import nl.itopia.corendon.mvc.Controller;

/**
 *
 * @author Kueter/ Erik
 */
public class DetailUserController extends Controller {
    
    @FXML private Label showUserID, showUsername, showFirstname, showLastname, showOnlinestatus, showAccountstatus, showRole, showAirport, showContactdetails, showNotes;
    @FXML private Button editButton, cancelButton;
    
    private TableUser user;
    private ImageModel imageModel;
    private Employee currentEmployee;
    private int userId;
    
    public DetailUserController(int userId) {
        
        this.userId = userId;
        
        // Set view
        registerFXML("gui/show_details_user.fxml");
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        currentEmployee = employeemodel.getEmployee(userId);

        imageModel = ImageModel.getDefault();
        
        editButton.setOnAction(this::editEmployee);
        cancelButton.setOnAction(this::cancelHandler);
        
        
        showUserID.setText(Integer.toString(currentEmployee.id));
        showUsername.setText(currentEmployee.username);
        showFirstname.setText(currentEmployee.firstName);
        showLastname.setText(currentEmployee.lastName);
        showRole.setText(currentEmployee.role.getName());
        showAirport.setText(currentEmployee.airport.getName());
        showNotes.setText(currentEmployee.notes);
    }
    
    /**
     * @param event
     */
    public void editEmployee(ActionEvent event) {

        addController( new EditUserController(currentEmployee.id) );
    }
    
    protected void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
