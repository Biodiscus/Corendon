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
import nl.itopia.corendon.data.table.TableLuggage;

/**
 *  AUTHOR: IGOR
 *  BEVAT ALLEEN FUNCTIONALITEIT VOOR TABLE VAN KOFFERS
 */
public class EmployeeController extends Controller {
    @FXML private TableView luggageInfo;
    public final ObservableList<TableLuggage> data;
    
    public final List<Luggage> luggageList;    
    @FXML private TableColumn <Luggage,String>Brand;
    @FXML private TableColumn <Luggage,String>Dimensions;
    @FXML private TableColumn <Luggage,String>Color;
    @FXML private TableColumn <Luggage,String>Airport;
    @FXML private TableColumn <Luggage,String>Status;
    @FXML private TableColumn <Luggage,String>Notes;

    @FXML private Button addLuggagebutton, editLuggagebutton, deleteLuggagebutton, searchLuggagebutton;
    
    
    public EmployeeController(){
        registerFXML("gui/Overzichtkoffers.fxml");  

        //Create buttons
        addLuggagebutton.setOnAction(this::addHandler);
        editLuggagebutton.setOnAction(this::editHandler);
        deleteLuggagebutton.setOnAction(this::deleteHandler);
        searchLuggagebutton.setOnAction(this::searchHandler);
        
        
        
        
        //Create columns and set their datatype for building the Luggage Table
        Brand.setCellValueFactory(new PropertyValueFactory<Luggage, String>("brand"));
        Dimensions.setCellValueFactory(new PropertyValueFactory<Luggage, String>("dimensions"));
        Color.setCellValueFactory(new PropertyValueFactory<Luggage, String>("color"));
        Airport.setCellValueFactory(new PropertyValueFactory<Luggage, String>("airport"));
        Status.setCellValueFactory(new PropertyValueFactory<Luggage, String>("status"));
        Notes.setCellValueFactory(new PropertyValueFactory<Luggage, String>("notes"));
        
        luggageList = LuggageModel.getDefault().getLuggage();
        data = FXCollections.observableArrayList();
        
        
        for(Luggage luggage : luggageList){
            TableLuggage luggageTabel = new TableLuggage(luggage.brand.getName(),
                    luggage.dimensions, luggage.color.getHex(), luggage.airport.getName(),
                    luggage.status.getName(), luggage.notes);
            
            data.add(luggageTabel);
        }
        
        luggageInfo.setItems(data);
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
}
