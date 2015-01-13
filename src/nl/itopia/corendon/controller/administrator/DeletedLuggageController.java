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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import nl.itopia.corendon.controller.InfoController;
import nl.itopia.corendon.Config;
import nl.itopia.corendon.controller.LoginController;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.data.table.TableLuggage;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.mvc.Controller;

import javax.swing.*;

/**
 *
 * @author Erik
 */
public class DeletedLuggageController extends Controller {

    @FXML private TableView luggageInfo;
    @FXML private AnchorPane LuggageTable;
    
    @FXML private Button revertLuggageButton, helpButton, logoutButton, deleteLuggageButton, overviewbutton, logfilesbutton, refreshButton;
    @FXML private TableView logInfo;
    @FXML private Label userName, userIDLoggedInPerson;
    public ObservableList<TableLuggage> tableData;
    public List<Luggage> luggageList;

    @FXML private TableColumn <Luggage,String>ID;
    @FXML private TableColumn <Luggage,String>Label;
    @FXML private TableColumn <Luggage,String>Brand;
    @FXML private TableColumn <Luggage,String>Dimensions;
    @FXML private TableColumn <Luggage,String>Color;
    @FXML private TableColumn <Luggage,String>Airport;
    @FXML private TableColumn <Luggage,String>Status;
    @FXML private TableColumn <Luggage,String>Notes; 
    
    private LuggageModel luggageModel;
    private ImageView spinningIcon;
    private StackPane iconPane;
    private InfoController infoController;
    private final Timer timer;
    
    public DeletedLuggageController() {
        tableData = FXCollections.observableArrayList();

        // Set view
        registerFXML("gui/deleted_luggage_admin.fxml");

        luggageModel = LuggageModel.getDefault();

        EmployeeModel employeeModel = EmployeeModel.getDefault();
        userIDLoggedInPerson.setText(""+employeeModel.currentEmployee.id);
        userName.setText(employeeModel.currentEmployee.firstName + " " + employeeModel.currentEmployee.lastName);
        // Show a spinning icon to indicate to the user that we are getting the tableData
        showLoadingIcon();
        
        logoutButton.setOnAction(this::logoutHandler);
        helpButton.setOnAction(this::helpHandler);
        overviewbutton.setOnAction(this::overviewHandler);
        logfilesbutton.setOnAction(this::logHandler);
        revertLuggageButton.setOnAction(this::revertHandler);
        deleteLuggageButton.setOnAction(this::deleteHandler);
        view.fxmlPane.setOnKeyReleased(this::keypressHandler);
        helpButton.setOnAction(this::helpHandler);
        refreshButton.setOnAction(this::refreshHandler);
        view.fxmlPane.setOnKeyReleased(this::keypressHandler);
        helpButton.setOnAction(this::helpHandler);
        
        revertLuggageButton.setDisable(true);
        deleteLuggageButton.setDisable(true);

        // Create columns and set their datatype for building the Luggage Table
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Label.setCellValueFactory(new PropertyValueFactory<>("label"));
        Brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        Dimensions.setCellValueFactory(new PropertyValueFactory<>("dimensions"));
        Color.setCellValueFactory(new PropertyValueFactory<>("color"));
        Airport.setCellValueFactory(new PropertyValueFactory<>("airport"));
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        Notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        luggageInfo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            revertLuggageButton.setDisable(false);
            deleteLuggageButton.setDisable(false);
        });

        // Create a timer with a certain interval, every time it ticks refresh the entire to receive new data
        timer = new Timer(Config.DATA_REFRESH_INTERVAL, (e)->refreshHandler(null));
        timer.start();
        refreshButton.setId("button_refresh");
        
        Thread dataThread = new Thread(()-> receiveData());
        dataThread.setDaemon(true); // If for some reason the program quits, let the threads get destroyed with the main thread
        dataThread.start();
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

    private void showLoadingIcon() {
        // Show a spinning icon to indicate to the user that we are getting the tableData
        spinningIcon = new ImageView("img/loader.gif");

        iconPane = new StackPane(spinningIcon);
        iconPane.setPrefWidth(LuggageTable.getPrefWidth());
        iconPane.setPrefHeight(LuggageTable.getPrefHeight());
        LuggageTable.getChildren().add(iconPane);
    }
    
    private void receiveData() {
        luggageList = luggageModel.getAllDeletedLuggage();
        tableData.clear();

        for(Luggage luggage : luggageList) {
            TableLuggage luggageTable = new TableLuggage(
                    luggage.getID(),
                    luggage.label,
                    luggage.dimensions,
                    luggage.notes,
                    luggage.airport.getName(),
                    luggage.brand.getName(),
                    luggage.color.getHex(),
                    luggage.status.getName()
            );

            tableData.add(luggageTable);
        }

        Platform.runLater(() -> {
            luggageInfo.setItems(tableData);
            LuggageTable.getChildren().remove(iconPane);

            refreshButton.setDisable(false);
            refreshButton.setId("button_refresh");
        });
    }
    
    private void overviewHandler(ActionEvent e) {
        changeController(new AdministratorController());
    }

    // We will call this function in a new thread, so the user can still click buttons

    private void logHandler(ActionEvent e) {
        changeController(new LogController());
    }
    
    private void revertHandler(ActionEvent e) {
        // TODO: Show dialog with text: Do you really want to delete this luggage?
        TableLuggage luggage = (TableLuggage) luggageInfo.getSelectionModel().getSelectedItem();
        luggageModel.revertLuggage(luggage.getId());
        tableData.remove(luggage);
    }
    
    private void deleteHandler(ActionEvent e) {
        TableLuggage luggage = (TableLuggage) luggageInfo.getSelectionModel().getSelectedItem();
        luggageModel.permDeleteLuggage(luggage.getId());
        tableData.remove(luggage);
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
            openHelp();
        }
        //opens help function
    }

    private void openHelp() {
        infoController = new InfoController("Reset Password", "test");
        
        infoController.setControllerDeleteHandler((obj)->{
            removeController(infoController);
            infoController = null;
        });
        addController(infoController);
    }
    
    private void logoutHandler(ActionEvent e) {
        changeController(new LoginController());
    }
}
