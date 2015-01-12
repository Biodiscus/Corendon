package nl.itopia.corendon.controller.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.data.table.TableUser;
import nl.itopia.corendon.mvc.Controller;
/**
 *
 * @author Kueter
 */
public class DetailUserController extends Controller {
    
    @FXML private Label showUserID, showUsername, showFirstname, showLastname, showOnlinestatus, showAccountstatus, showRole, showAirport, showContactdetails, showNotes;
    @FXML private Button editButton, cancelButton;
    @FXML private TableView userTable;
    @FXML  private TableColumn<Employee, String> userIDtable;
    @FXML private TableColumn<Employee, String> usernameTable;
    @FXML private TableColumn<Employee, String> firstnameTable;
    @FXML private TableColumn<Employee, String> lastnameTable;
    @FXML private TableColumn<Employee, String> roleTable;
    @FXML private TableColumn<Employee, String> airportTable;
    
    private TableUser user;
    
    public DetailUserController(TableUser user) {
        
        registerFXML("gui/show_details_user.fxml");
        
        this.user = user;
        
        editButton.setOnAction(this::editEmployee);
        cancelButton.setOnAction(this::cancelHandler);
        
        showUserID.setText(Integer.toString(user.getUserID()));
        showUsername.setText(user.getUserName());
        showFirstname.setText(user.getFirstName());
        showLastname.setText(user.getLastName());
        showRole.setText(user.getRole());
    }
    
    /**
     * @param event
     */
    public void editEmployee(ActionEvent event) {

        addController( new EditUserController(this.user.getUserID()) );
    }
    
    protected void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
