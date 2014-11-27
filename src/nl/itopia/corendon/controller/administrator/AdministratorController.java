package nl.itopia.corendon.controller.administrator;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.itopia.corendon.controller.administrator.CreateUserController;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.data.table.TableUser;
import nl.itopia.corendon.model.DatabaseManager;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;

/**
 *
 * @author Erik
 */
public class AdministratorController extends Controller {
    
    //private AdministratorView view;
    private Employee employee;
    private DatabaseManager dbManager;
    
    public final ObservableList<TableUser> data = FXCollections.observableArrayList();;
    
    public final List<Employee> employeeList = EmployeeModel.getDefault().getEmployees();;
    @FXML private TableView userTable;
    
    @FXML private TableColumn <Employee,String>userIDtable;
    @FXML private TableColumn <Employee,String>usernameTable;
    @FXML private TableColumn <Employee,String>firstnameTable;
    @FXML private TableColumn <Employee,String>lastnameTable;
    @FXML private TableColumn <Employee,String>roleTable;
    @FXML private TableColumn <Employee,String>airportTable;

    @FXML private Button allusersButton, adduserButton, deleteuserButton, edituserButton,helpButton;
    private String deleteUserId;
    
    public AdministratorController() {
        
        registerFXML("gui/overview_administrator.fxml");
        
        allusersButton.setOnAction(this::allUsers);
        adduserButton.setOnAction(this::createNewEmployee);
        edituserButton.setOnAction(this::editEmployee);
        helpButton.setOnAction(this::helpHandler);
               
        initializeTable();
        
        /**
         * Delete user
         */
        userTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            
            // Get the object for the selected user in the table
            TableUser user = (TableUser) userTable.getSelectionModel().getSelectedItem();
            this.deleteUserId = user.getUserID();
            
            if(deleteUserId != "") {
               // Trigger click on button and run delete method
               deleteuserButton.setOnAction(this::deleteEmployee);               
            }
        });
        
    }
    
    public void allUsers(ActionEvent event) {
        changeController(new AdministratorController());
    }
    
    public void createNewEmployee(ActionEvent event) {
        initializeTable();
        addController(new CreateUserController());
    }
    
    public void editEmployee(ActionEvent event) {
        addController(new editEmployeeController());
    }
    
    /**
     * Handle delete action through database
     * 
     * @param event
     */
    public void deleteEmployee(ActionEvent event) {
        
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        employeemodel.deleteEmployee(this.deleteUserId);
        initializeTable();
    }
    
    public void initializeTable() {
        
        // Table headings
        userIDtable.setCellValueFactory(new PropertyValueFactory<>("userID"));
        usernameTable.setCellValueFactory(new PropertyValueFactory<>("userName"));
        firstnameTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        roleTable.setCellValueFactory(new PropertyValueFactory<>("role"));
        airportTable.setCellValueFactory(new PropertyValueFactory<>("airport"));

        for(Employee employee : employeeList) {
            
            if( !employee.account_status.equals("deleted") ) {
                
                String role = employee.role.getName();
                String airport = employee.airport.getName();
                String id = ""+employee.id;

                TableUser user = new TableUser(id, employee.username, employee.firstName, employee.lastName, role, airport);
                this.data.add(user);
            }
        }
        
        userTable.setItems(data);
    }
    
     private void helpHandler(ActionEvent e) {
        addController(new HelpFunctionControllerAdmin());

        //opens help function
    }
}

