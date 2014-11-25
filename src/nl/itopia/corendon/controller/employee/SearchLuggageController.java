package nl.itopia.corendon.controller.employee;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import nl.itopia.corendon.data.Airport;
import nl.itopia.corendon.data.ChooseItem;
import nl.itopia.corendon.data.Color;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.model.AirportModel;
import nl.itopia.corendon.model.ColorModel;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.mvc.Controller;

/**
 * @author Jeroentje
 */
public class SearchLuggageController extends Controller {

    @FXML private ChoiceBox foundonAirportdropdown, colorDropdown;
    @FXML private Button addButton, cancelButton;
    
    private AirportModel airportModel;
    private ColorModel colorModel;
    
    public SearchLuggageController() {
        registerFXML("gui/Search_luggage.fxml");
        
        airportModel = AirportModel.getDefault();
        colorModel = ColorModel.getDefault();


        // Set the Airports in the foundonAirportdropdown
        List<Airport> airports = airportModel.getAirports();
        for(Airport airport : airports) {
            ChooseItem c = airportModel.airportToChoose(airport);
            foundonAirportdropdown.getItems().add(c);
        }
        foundonAirportdropdown.getSelectionModel().selectFirst();

        // Set the Colors in the colorDropdown
        List<Color> colors = colorModel.getColors();
        for(Color color : colors) {
            ChooseItem c = colorModel.colorToChoose(color);
            colorDropdown.getItems().add(c);
        }
        colorDropdown.getSelectionModel().selectFirst();
        
        cancelButton.setOnAction(this::cancelHandler);
    }
    
    public void cancelHandler(ActionEvent e){
        removeController(this);
    }
    
    
}
