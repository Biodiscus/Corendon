package nl.itopia.corendon.controller.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.mvc.Controller;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  AUTHOR: IGOR
 *  BEVAT ALLEEN FUNCTIONALITEIT VOOR TABLE VAN KOFFERS
 */
public class EmployeeController extends Controller {
    @FXML private TableView<Luggage> luggageInfo;
    //@FXML private TableColumn ID; ID LATEN ZIEN IN OVERVIEW?? ONGEBRUIKELIJK
    
    @FXML private TableColumn <Luggage,String> Brand;
    @FXML private TableColumn <Luggage,String>Dimensions;
    @FXML private TableColumn <Luggage,String>Color;
    @FXML private TableColumn <Luggage,String>Airport;
    @FXML private TableColumn <Luggage,String>Status;
    @FXML private TableColumn <Luggage,String>Notes;

    @FXML private Button addLuggagebutton, editLuggagebutton, deleteLuggagebutton, searchLuggagebutton;
    
    
    public EmployeeController(){
        registerFXML("gui/Overzichtkoffers.fxml");  
        //Log.display(luggageInfo); ???

        addLuggagebutton.setOnAction(this::addHandler);
        editLuggagebutton.setOnAction(this::editHandler);
        deleteLuggagebutton.setOnAction(this::deleteHandler);
        searchLuggagebutton.setOnAction(this::searchHandler);
    }

    private void addHandler(ActionEvent e) {
        addController(new AddLuggageController());

        // Update our table with the new data
    }
    
    private void searchHandler(ActionEvent e) {
        addController(new SearchLuggageController());
    }

    private void editHandler(ActionEvent e) {
        int id = 5; // Check the table for the current selected item
        addController(new EditLuggageController(id));
    }

    private void deleteHandler(ActionEvent e) {
        int id = 5; // Check the table for the current selected item
        // Show dialog with text: Do you really want to delete this luggage?
    }

    
    public void initializeTable(){
        //Create columns and set their datatype
        
        //FF ALLEEN DE PRIMITIEVE DATATYPES OM TE TESTEN
        //Brand.setCellValueFactory(new PropertyValueFactory<Luggage, String>("Brand"));
        Dimensions.setCellValueFactory(new PropertyValueFactory<Luggage, String>("dimensions"));
        //Color.setCellValueFactory(new PropertyValueFactory<Luggage, String>("Color"));
        //Airport.setCellValueFactory(new PropertyValueFactory<Luggage, String>("Airport"));
        //Status.setCellValueFactory(new PropertyValueFactory<Luggage, String>("Status"));
        Notes.setCellValueFactory(new PropertyValueFactory<Luggage, String>("notes"));
        
        
        //MAAK LIST VAN LUGGAGE OBJECTS EN VUL DE KOLOMMEN
        LuggageModel luggageModel = LuggageModel.getDefault(); 
        List<Luggage> luggageList = luggageModel.getAllLuggage();
        ObservableList<Luggage> luggageObservableList = FXCollections.observableList(luggageList);
        luggageInfo.getItems().setAll(luggageObservableList);
        
    }
}
