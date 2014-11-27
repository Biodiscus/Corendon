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
//import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.model.DatabaseManager;
import nl.itopia.corendon.model.EmployeeModel;
//import nl.itopia.corendon.model.LuggageModel;
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
    @FXML private TableColumn <Employee,String>lastnameTable1;
    
    @FXML private Button adduserButton;
    @FXML private Button deleteuserButton;
    private String deleteUserId;
    
    public AdministratorController() {
        
        registerFXML("gui/Overview_administrator.fxml");
        
        adduserButton.setOnAction(this::createNewEmployee);

        initializeTable();
        
        /**
         * Delete user
         */
        userTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            
            // Get the object for the selected user in the table
            TableUser user = (TableUser) userTable.getSelectionModel().getSelectedItem();
            this.deleteUserId = user.getUserID();
            
            System.out.println(deleteUserId);
            
            // Trigger click on button and run delete method
            deleteuserButton.setOnAction(this::deleteEmployee);
        });
        
    }
    
    public void createNewEmployee(ActionEvent event) {
        
        initializeTable();
        addController(new CreateUserController());
    }
    
    /**
     * Handle delete action through database
     * @param event 
     * @param UserId 
     */
    public void deleteEmployee(ActionEvent event) {
        
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        System.out.println("UserId: "+ this.deleteUserId);
        //employeemodel.deleteEmployee(UserId);
        initializeTable();
    }
    
    public void initializeTable() {
        
        System.out.println("Init table!");
        //userTable.getItems().clear();
        
        userIDtable.setCellValueFactory(new PropertyValueFactory<Employee, String>("userIDtable"));
        usernameTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("usernameTable"));
        firstnameTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstnameTable"));
        //lastnameTable1.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastnameTable1"));
        
        for(Employee employee : employeeList) {
            
            if( !employee.account_status.equals("deleted") ) {
                
                String name = employee.firstName + " " + employee.lastName;
                TableUser user = new TableUser(Integer.toString(employee.id), employee.username, employee.firstName, employee.lastName);
                this.data.add(user);
            }
        }
        
        userTable.setItems(data);
        
        
   
        
        
        
        
        
        
        
        
        
        
        
        //employeeList = EmployeeModel.getDefault().getEmployees();
        
//        for(int i = 0; i < employeeList.size(); i++) {
//            Employee a = employeeList.get(i);
//            Log.display(a.firstName, a.lastName, a.username);
//        }
        
        //data = FXCollections.observableArrayList();    
        
        
        
 //        data = FXCollections.observableArrayList(new TableUser("Wies", "Kueter", "06-12345678"),
//                new TableUser("Robin", "de Jong", "06-12345678"),
//                new TableUser("Jeroen", "Groot", "06-12345678"),
//                new TableUser("Stefan", "de Groot", "06-12345678"),
//                new TableUser("Erik", "Schouten", "06-12345678"),
//                new TableUser("Igor", "Willems", "06-12345678")
//        );
               
        
        
        //Create columns and set their datatype
        
        //MAAK LIST VAN LUGGAGE OBJECTS EN VUL DE KOLOMMEN
        //LuggageModel luggageModel = LuggageModel.getDefault(); 
        //List<Employee> employeeList = EmployeeModel.getAllEmployee();
        //luggageInfo.getItems().setAll(luggageList);
    }
}
