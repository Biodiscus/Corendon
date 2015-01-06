package nl.itopia.corendon.controller.administrator;

import java.time.LocalDate;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import nl.itopia.corendon.controller.LoginController;
import nl.itopia.corendon.controller.HelpFunctionController;
import nl.itopia.corendon.data.LogAction;
import nl.itopia.corendon.model.LogModel;
import nl.itopia.corendon.mvc.Controller;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.data.ChooseItem;
import nl.itopia.corendon.data.table.TableLog;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.utils.DateUtil;

/**
 * @author Jeroentje
 */
public class LogController extends Controller {
    @FXML private Button filterButton, helpButton, logoutButton, printstatisticsButton, overviewbutton, deletedLuggageButton;
    @FXML private CheckBox foundLuggagecheckbox, lostLuggagecheckbox, resolvedLuggagecheckbox;
    @FXML private DatePicker datepicker1, datepicker2;
    @FXML private TableView logInfo;
    @FXML private ChoiceBox users;
    @FXML private AnchorPane logTable;
    public ObservableList<TableLog> tableData;
    public List<LogAction> logFileList;

    @FXML private TableColumn <LogAction,String>ID;    
    @FXML private TableColumn <LogAction,String>user;    
    @FXML private TableColumn <LogAction,String>userID;    
    @FXML private TableColumn <LogAction,String>action;    
    @FXML private TableColumn <LogAction,String>date;    
    
    private LogModel logModel;
    private EmployeeModel employeeModel;
    private ImageView spinningIcon;
    private StackPane iconPane;
    private HelpFunctionController helpController;

    public LogController() {
        
        // Set view
        registerFXML("gui/administrator_logs.fxml");

        logModel = LogModel.getDefault();
        employeeModel = EmployeeModel.getDefault();
        
        logoutButton.setOnAction(this::logoutHandler);
        helpButton.setOnAction(this::helpHandler);
        overviewbutton.setOnAction(this::overviewHandler);
        deletedLuggageButton.setOnAction(this::deletedLuggageHandler);
        filterButton.setOnAction(this::filterHandler);
        helpButton.setOnAction(this::helpHandler);
        // TODO: Implement print
        // printstatisticsButton.setOnAction(this::printStatisticsHandler);
        view.fxmlPane.setOnKeyReleased(this::f1HelpFunction);

        datepicker1.setValue(LocalDate.now());

        // Show a spinning icon to indicate to the user that we are getting the tableData
        showLoadingIcon();

        // Set the Airports in the foundonAirportdropdown
        List<Employee> employees = employeeModel.getLogEmployees();
        users.getItems().add(0, "Maak een keuze");
        for(Employee employee : employees) {
            ChooseItem c = employeeModel.employeeToChoose(employee);
            users.getItems().add(c);
        }
        users.getSelectionModel().selectFirst();
        
        // Create columns and set their datatype for building the Luggage Table
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        action.setCellValueFactory(new PropertyValueFactory<>("Action"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        
        // Make a new thread that will recieve the tableData from the database
        Thread dataThread = new Thread(()->recieveData(logModel.getLogFiles()));
        dataThread.start();
    }

    private void showLoadingIcon() {
        // Show a spinning icon to indicate to the user that we are getting the tableData
        spinningIcon = new ImageView("img/loader.gif");

        iconPane = new StackPane(spinningIcon);
        iconPane.setPrefWidth(logInfo.getPrefWidth());
        iconPane.setPrefHeight(logInfo.getPrefHeight());
        logTable.getChildren().add(iconPane);
    }

    private void overviewHandler(ActionEvent e) {
        changeController(new AdministratorController());
    }

    // We will call this function in a new thread, so the user can still click buttons
    private void recieveData(List<LogAction> logList) {
        logFileList = logList;
        tableData = FXCollections.observableArrayList();

        for(LogAction logEntry : logFileList) {
            String formattedDate = DateUtil.formatDate("dd-MM-yyyy HH:mm:ss", logEntry.date);
            TableLog logTable = new TableLog(
                    logEntry.getID(),
                    logEntry.employee.username,
                    logEntry.employee.id,
                    logEntry.action.getName(),
                    formattedDate
            );

            tableData.add(logTable);
        }

        Platform.runLater(() -> {
            logInfo.setItems(tableData);
            logTable.getChildren().remove(iconPane);
        });
    }
    
    private void filterHandler(ActionEvent e) {
        // Show a spinning icon to indicate to the user that we are getting the tableData
        showLoadingIcon();
        
        /* filtering on date */
        LocalDate searchDate = datepicker1.getValue();
        String username = users.getSelectionModel().getSelectedItem().toString();
        // Make a new thread that will recieve the tableData from the database
        Thread dataThread = new Thread(()->recieveData(logModel.getLogFiles(searchDate,username)));
        dataThread.start();        
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
    
    private void logoutHandler(ActionEvent e) {
        changeController(new LoginController());
    }
    
    private void deletedLuggageHandler(ActionEvent e) {
        changeController(new DeletedLuggageController());
    }
}
