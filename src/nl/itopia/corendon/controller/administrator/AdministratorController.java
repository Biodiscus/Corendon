package nl.itopia.corendon.controller.administrator;

import nl.itopia.corendon.Config;
import nl.itopia.corendon.controller.ChangePasswordController;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import nl.itopia.corendon.controller.InfoController;
import nl.itopia.corendon.controller.LoginController;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.data.table.TableUser;
import nl.itopia.corendon.model.DatabaseManager;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;

import javax.swing.Timer;
import nl.itopia.corendon.utils.IO;

/**
 * @author Erik
 */
public class AdministratorController extends Controller {
    private EmployeeModel employeeModel;
    private DatabaseManager dbManager;
    private InfoController infoController;

    public final ObservableList<TableUser> tableData = FXCollections.observableArrayList();

    @FXML private AnchorPane userAnchorpane;
    @FXML private TableView userTable;
    @FXML private TableColumn<Employee, String> userIDtable;
    @FXML private TableColumn<Employee, String> usernameTable;
    @FXML private TableColumn<Employee, String> firstnameTable;
    @FXML private TableColumn<Employee, String> lastnameTable;
    @FXML private TableColumn<Employee, String> roleTable;
    @FXML private TableColumn<Employee, String> airportTable;
    @FXML private Label userName, userIDLoggedInPerson;
    @FXML private Button /*allusersButton, */adduserButton, deleteuserButton, edituserButton, detailsuserButton, logoutButton, changePasswordButton, helpButton, logfilesbutton, deletedLuggageButton, refreshButton;

    private ImageView spinningIcon;
    private StackPane iconPane;
    
    //private final Timer timer;
    private final Timer timer;

    public AdministratorController() {

        // Set view
        registerFXML("gui/Overview_administrator.fxml");

        employeeModel = EmployeeModel.getDefault();

        userIDLoggedInPerson.setText(""+employeeModel.currentEmployee.id);
        userName.setText(employeeModel.currentEmployee.firstName + " " + employeeModel.currentEmployee.lastName);

        // Show a spinning icon to indicate to the user that we are getting the tableData
        showLoadingIcon();

//        allusersButton.setOnAction(this::allUsers);
        adduserButton.setOnAction(this::createNewEmployee);
        edituserButton.setOnAction(this::editEmployee);
        detailsuserButton.setOnAction(this::detailsEmployee);
        deleteuserButton.setOnAction(this::deleteEmployee);
        logoutButton.setOnAction(this::logoutHandler);
        logfilesbutton.setOnAction(this::logHandler);
        changePasswordButton.setOnAction(this::changePassword);
        view.fxmlPane.setOnKeyReleased(this::keypressHandler);
        helpButton.setOnAction(this::helpHandler);
        refreshButton.setOnAction(this::refreshHandler);

        // As long as we don't have any user selected delete and edit user shouldn't be enabled
        edituserButton.setDisable(true);
        deleteuserButton.setDisable(true);
        detailsuserButton.setDisable(true);

        // Table headings
        userIDtable.setCellValueFactory(new PropertyValueFactory<>("userID"));
        usernameTable.setCellValueFactory(new PropertyValueFactory<>("userName"));
        firstnameTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        roleTable.setCellValueFactory(new PropertyValueFactory<>("role"));
        airportTable.setCellValueFactory(new PropertyValueFactory<>("airport"));

        this.tableActions();

        // Create a timer with a certain interval, every time it ticks refresh the entire to receive new data
        timer = new Timer(Config.DATA_REFRESH_INTERVAL, (e)->refreshHandler(null));
        timer.start();
        refreshButton.setId("button_refresh");

        // Make a new thread that will recieve the tableData from the database
        Thread dataThread = new Thread(this::receiveData);
        dataThread.setDaemon(true); // If for some reason the program quits, let the threads get destroyed with the main thread
        dataThread.start();
    }

    private void showLoadingIcon() {
        // Show a spinning icon to indicate to the user that we are getting the tableData
        spinningIcon = new ImageView("img/loader.gif");

        iconPane = new StackPane(spinningIcon);
        iconPane.setPrefWidth(userTable.getPrefWidth());
        iconPane.setPrefHeight(userTable.getPrefHeight());
        userAnchorpane.getChildren().add(iconPane);
    }

    private void refreshHandler(ActionEvent e) {
        Platform.runLater(() -> {
            refreshButton.setDisable(true);
            refreshButton.setId("button_refresh_animate");
        });

        Thread dataThread = new Thread(this::receiveData);
        dataThread.setDaemon(true);
        dataThread.start();
    }
    
    private void changePassword(ActionEvent e) {
        addController( new ChangePasswordController() );
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
            detailsuserButton.setDisable(false);

            edituserButton.setOnAction(this::editEmployee);
        });
    }

//    public void allUsers(ActionEvent event) {
//        changeController(new AdministratorController());
//    }

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
        int selectedIndex = userTable.getSelectionModel().getSelectedIndex();
        EditUserController editUserController =new EditUserController(user.getUserID());
        
        editUserController.setControllerDeleteHandler((obj) -> {
            boolean isBoolean = (obj instanceof Boolean);
            /* check if dialog is canceled */
            if(!isBoolean) {
                /* object is not a boolean, so it means that the dialog is naturally closed */
                // The editUserController will return a Employee
                Employee employee = (Employee) obj;
                TableUser editedUser = new TableUser(
                        employee.getID(),
                        employee.username,
                        employee.firstName,
                        employee.lastName,
                        employee.role.getName(),
                        employee.airport.getName()
                );
                tableData.remove(selectedIndex);
                tableData.add(selectedIndex,editedUser);
            }
        });
        
        addController(editUserController);
    }
    
    public void detailsEmployee(ActionEvent event) {
        TableUser user = (TableUser) userTable.getSelectionModel().getSelectedItem();
        addController(new DetailUserController(user.getUserID()));
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
    }

    private void receiveData() {
        List<Employee> employeeList = employeeModel.getEmployees();

        tableData.clear();

        for (Employee employee : employeeList) {
            if (!employee.account_status.equals("deleted")) {
                String role = employee.role.getName();
                String airport = employee.airport.getName();

                TableUser user = new TableUser(employee.id, employee.username, employee.firstName, employee.lastName, role, airport);
                tableData.add(user);
            }
        }

        Platform.runLater(() -> {
            userTable.setItems(tableData);
            userAnchorpane.getChildren().remove(iconPane);

            refreshButton.setDisable(false);
            refreshButton.setId("button_refresh");
        });
    }

    /**
     * Open F1 InfoWindow
     * @param e 
     */
    private void keypressHandler(KeyEvent e) {
        //opens helpfunction with the f1 key
        if(e.getEventType() == KeyEvent.KEY_RELEASED) {
            if (e.getCode() == KeyCode.F1) {
                // If it's already openend, close it
                if (infoController == null) {
                    openHelp();
                } else {
                    removeController(infoController);
                    infoController = null;
                }
            } 
        }
    }
    
    private void helpHandler(ActionEvent e) {
        if(infoController == null) {
            // Open help function
            openHelp();
        }
    }

    private void openHelp() {
        infoController = new InfoController("Admin information", IO.get("help/admin.htm").toString());
        
        infoController.setControllerDeleteHandler((obj)->{
            removeController(infoController);
            infoController = null;
        });
        addController(infoController);
    }
    
    private void deletedLuggageHandler(ActionEvent e) {
        changeController(new DeletedLuggageController());
    }
}
