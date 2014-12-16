package nl.itopia.corendon.controller.administrator;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import nl.itopia.corendon.controller.HelpFunctionController;
import nl.itopia.corendon.controller.LoginController;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.data.table.TableUser;
import nl.itopia.corendon.model.DatabaseManager;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;

/**
 * @author Erik
 */
public class AdministratorController extends Controller {

    //private AdministratorView view;
    private EmployeeModel employeeModel;
    private DatabaseManager dbManager;

    public final ObservableList<TableUser> tableData = FXCollections.observableArrayList();


    public final List<Employee> employeeList = EmployeeModel.getDefault().getEmployees();

    @FXML private AnchorPane userAnchorpane;
    @FXML
    private TableView userTable;
    @FXML
    private TableColumn<Employee, String> userIDtable;
    @FXML
    private TableColumn<Employee, String> usernameTable;
    @FXML
    private TableColumn<Employee, String> firstnameTable;
    @FXML
    private TableColumn<Employee, String> lastnameTable;
    @FXML
    private TableColumn<Employee, String> roleTable;
    @FXML
    private TableColumn<Employee, String> airportTable;
    @FXML
    private Button allusersButton, adduserButton, deleteuserButton, edituserButton, logoutButton, helpButton, logfilesbutton, deletedLuggageButton;

    private ImageView spinningIcon;
    private StackPane iconPane;

    @FXML private Label userName, userID;

    private int deleteUserId;
    private TableUser user;
    private HelpFunctionController helpController;

    public AdministratorController() {

        // Set view
        registerFXML("gui/Overview_administrator.fxml");

        userID.setText(Integer.toString(EmployeeModel.currentEmployee.id));
        userName.setText(EmployeeModel.currentEmployee.firstName + " " + EmployeeModel.currentEmployee.lastName);

        // Show a spinning icon to indicate to the user that we are getting the tableData
        showLoadingIcon();

        employeeModel = EmployeeModel.getDefault();

        allusersButton.setOnAction(this::allUsers);
        adduserButton.setOnAction(this::createNewEmployee);
        edituserButton.setOnAction(this::editEmployee);
        deleteuserButton.setOnAction(this::deleteEmployee);
        logoutButton.setOnAction(this::logoutHandler);
        helpButton.setOnAction(this::helpHandler);
        logfilesbutton.setOnAction(this::logHandler);
        deletedLuggageButton.setOnAction(this::deletedLuggageHandler);
        view.fxmlPane.setOnKeyReleased(this::f1HelpFunction);

        // As long as we don't have any user selected delete and edit user shouldn't be enabled
        edituserButton.setDisable(true);
        deleteuserButton.setDisable(true);

        // Make a new thread that will receive the tableData from the database
        Thread dataThread = new Thread(() -> receiveData());
        dataThread.start();

        // Table headings
        userIDtable.setCellValueFactory(new PropertyValueFactory<>("userID"));
        usernameTable.setCellValueFactory(new PropertyValueFactory<>("userName"));
        firstnameTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        roleTable.setCellValueFactory(new PropertyValueFactory<>("role"));
        airportTable.setCellValueFactory(new PropertyValueFactory<>("airport"));


        this.tableActions();
    }

    private void showLoadingIcon() {
        // Show a spinning icon to indicate to the user that we are getting the tableData
        spinningIcon = new ImageView("img/loader.gif");

        iconPane = new StackPane(spinningIcon);
        iconPane.setPrefWidth(userTable.getPrefWidth());
        iconPane.setPrefHeight(userTable.getPrefHeight());
        userAnchorpane.getChildren().add(iconPane);
    }

    // Fired when the log button is clicked
    private void logHandler(ActionEvent e) {
        changeController(new LogController());
    }

    /**
     * Actions for selected row (edit, delete)
     */
    public void tableActions() {
        userTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {

            edituserButton.setDisable(false);
            deleteuserButton.setDisable(false);

            edituserButton.setOnAction(this::editEmployee);

            // Get the object for the selected user in the table
            this.user = (TableUser) userTable.getSelectionModel().getSelectedItem();

            if (this.deleteUserId != 0) {
                this.deleteUserId = user.getUserID();
                // Trigger click on button and run delete method
                deleteuserButton.setOnAction(this::deleteEmployee);
            }
        });
    }

    public void allUsers(ActionEvent event) {
        changeController(new AdministratorController());
    }

    public void createNewEmployee(ActionEvent event) {

        CreateUserController createUser = new CreateUserController();

        createUser.setControllerDeleteHandler((obj) -> {
            // The createUserController will return a Employee
            Employee employee = (Employee) obj;
            TableUser user = new TableUser(
                    employee.getID(),
                    employee.username,
                    employee.firstName,
                    employee.lastName,
                    employee.role.getName(),
                    employee.airport.getName()
            );
            tableData.add(user);
        });
        addController(createUser);
    }

    private void logoutHandler(ActionEvent e) {
        changeController(new LoginController());
    }

    /**
     * @param event
     */
    public void editEmployee(ActionEvent event) {

        TableUser user = (TableUser) userTable.getSelectionModel().getSelectedItem();
        //addController( new EditUserController(user.getUserID()) );
        addController(new EditUserController(user.getUserID()));
    }

    /**
     * Handle delete action through database
     *
     * @param event
     */
    public void deleteEmployee(ActionEvent event) {

        TableUser user = (TableUser) userTable.getSelectionModel().getSelectedItem();
        employeeModel.deleteEmployee(user.getUserID());
        tableData.remove(user);

        EmployeeModel employeemodel = EmployeeModel.getDefault();
        employeemodel.deleteEmployee(this.deleteUserId);
        //data.remove(this.user);
        //initializeTable();
    }

    private void receiveData() {

        for (Employee employee : employeeList) {

            if (!employee.account_status.equals("deleted")) {

                String role = employee.role.getName();
                String airport = employee.airport.getName();

                TableUser user = new TableUser(employee.id, employee.username, employee.firstName, employee.lastName, role, airport);
                this.tableData.add(user);
            }
        }

        Platform.runLater(() -> {
            userTable.setItems(tableData);
            userAnchorpane.getChildren().remove(iconPane);
        });
    }

    private void f1HelpFunction(KeyEvent e) {
        //opens helpfunction with the f1 key
        if(e.getCode() == KeyCode.F1 && e.getEventType() == KeyEvent.KEY_RELEASED) {
            // If it's already openend, close it
            if(helpController == null) {
                openHelp();
            } else {
                removeController(helpController);
                helpController = null;
            }
        }
    }

    private void helpHandler(ActionEvent e) {
        if(helpController == null) {
            openHelp();
        }
        //opens help function
    }
    
    private void openHelp() {
        helpController = new HelpFunctionController();
        addController(helpController);
    }
    
    private void deletedLuggageHandler(ActionEvent e) {
        changeController(new DeletedLuggageController());
    }

}
