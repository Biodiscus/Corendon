package nl.itopia.corendon.controller.administrator;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import nl.itopia.corendon.controller.LoginController;
import nl.itopia.corendon.controller.HelpFunctionController;
import nl.itopia.corendon.data.LogAction;
import nl.itopia.corendon.model.LogModel;
import nl.itopia.corendon.mvc.Controller;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nl.itopia.corendon.data.table.TableLog;
import nl.itopia.corendon.utils.DateUtil;

/**
 * @author Jeroentje
 */
public class LogController extends Controller {
    @FXML private Button filterButton, helpButton, logoutButton, printstatisticsButton, overviewbutton, deletedLuggageButton;
    @FXML private CheckBox foundLuggagecheckbox, lostLuggagecheckbox, resolvedLuggagecheckbox;
    @FXML private DatePicker datepicker1, datepicker2;
    @FXML private TableView logInfo;

    public ObservableList<TableLog> tableData;
    public List<LogAction> logFileList;

    @FXML private TableColumn <LogAction,String>ID;    
    @FXML private TableColumn <LogAction,String>user;    
    @FXML private TableColumn <LogAction,String>userID;    
    @FXML private TableColumn <LogAction,String>action;    
    @FXML private TableColumn <LogAction,String>date;    
    
    private LogModel logModel;

    private ImageView spinningIcon;
    private StackPane iconPane;

    

    public LogController() {
        registerFXML("gui/administrator_logs.fxml");

         logModel = logModel.getDefault();

        logoutButton.setOnAction(this::logoutHandler);
        helpButton.setOnAction(this::helpHandler);
        overviewbutton.setOnAction(this::overviewHandler);
        deletedLuggageButton.setOnAction(this::deletedLuggageHandler);
        // TODO: Implement print
//        printstatisticsButton.setOnAction(this::printStatisticsHandler);
        view.fxmlPane.setOnKeyReleased(this::f1HelpFunction);

//        datepicker1.setValue(LocalDate.now());

        // Show a spinning icon to indicate to the user that we are getting the tableData
        Image image = new Image("img/loader.gif", 24, 16.5, true, false);
        spinningIcon = new ImageView(image);

        iconPane = new StackPane();
        iconPane.setPickOnBounds(false);
        iconPane.getChildren().add(spinningIcon);
        view.fxmlPane.getChildren().add(iconPane);

        // Create columns and set their datatype for building the Luggage Table
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        action.setCellValueFactory(new PropertyValueFactory<>("Action"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        
        // Make a new thread that will recieve the tableData from the database
        Thread dataThread = new Thread(()->recieveData());
        dataThread.start();
    }

    private void overviewHandler(ActionEvent e) {
        changeController(new AdministratorController());
    }

    // We will call this function in a new thread, so the user can still click buttons
    private void recieveData() {
        logFileList = logModel.getLogFiles();
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
            view.fxmlPane.getChildren().remove(iconPane);
        });
    }
    
    private void helpHandler(ActionEvent e) {
        addController(new HelpFunctionController());
        //opens help function
    }

    
    private void f1HelpFunction(KeyEvent e) {
    if(e.getCode() == KeyCode.F1 && e.getEventType() == KeyEvent.KEY_RELEASED) {
        addController(new HelpFunctionController());
        //opens helpfunction with the f1 key
    }
}
    
    
    
    private void logoutHandler(ActionEvent e) {
        changeController(new LoginController());
    }
    
    private void deletedLuggageHandler(ActionEvent e) {
        changeController(new DeletedLuggageController());
    }
   

}
