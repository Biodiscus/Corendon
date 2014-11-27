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
 *  
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

        //C reate buttons
        addLuggagebutton.setOnAction(this::addHandler);
        editLuggagebutton.setOnAction(this::editHandler);
        deleteLuggagebutton.setOnAction(this::deleteHandler);
        searchLuggagebutton.setOnAction(this::searchHandler);
        
        // Create columns and set their datatype for building the Luggage Table
        Brand.setCellValueFactory(new PropertyValueFactory<Luggage, String>("brand"));
        Dimensions.setCellValueFactory(new PropertyValueFactory<Luggage, String>("dimensions"));
        Color.setCellValueFactory(new PropertyValueFactory<Luggage, String>("color"));
        Airport.setCellValueFactory(new PropertyValueFactory<Luggage, String>("airport"));
        Status.setCellValueFactory(new PropertyValueFactory<Luggage, String>("status"));
        Notes.setCellValueFactory(new PropertyValueFactory<Luggage, String>("notes"));
        
        luggageList = LuggageModel.getDefault().getAllLuggage();
        data = FXCollections.observableArrayList();
        
        for(Luggage luggage : luggageList) {
            
            TableLuggage luggageTabel = new TableLuggage(luggage.dimensions,luggage.notes,
                    luggage.airport.getName(),luggage.brand.getName(),luggage.color.getHex(),
                    luggage.status.getName());
            
            data.add(luggageTabel);
        }
        
        luggageInfo.setItems(data);
    }        


    private void addHandler(ActionEvent e) {
        
        addController(new AddLuggageController());
        // Update our table with the new data
    }
    
    private void searchHandler(ActionEvent e) {
        
        SearchLuggageController searchluggagecontroller = new SearchLuggageController();
        
        searchluggagecontroller.setControllerDeleteHandler((o)->{
            /* cast the object to a list */
            List<Luggage> searchList = (List<Luggage>) o;
            
            /* delete all records from the table view */
            data.clear();
            
            if(null != searchList && searchList.size() >= 1) {
                /* the search query has atleast one record, continue to fill the table view */
                for(Luggage luggage : searchList){
                    TableLuggage luggageTabel = new TableLuggage(luggage.dimensions,luggage.notes,
                            luggage.airport.getName(),luggage.brand.getName(),luggage.color.getHex(),
                            luggage.status.getName());

                    data.add(luggageTabel);
                }
            }

        });
        
        addController(searchluggagecontroller);
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
