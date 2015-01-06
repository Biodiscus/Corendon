package nl.itopia.corendon.controller.employee;

import java.time.LocalDate;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import nl.itopia.corendon.data.Airport;
import nl.itopia.corendon.data.ChooseItem;
import nl.itopia.corendon.data.Color;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.data.Status;
import nl.itopia.corendon.model.AirportModel;
import nl.itopia.corendon.model.ColorModel;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.model.SearchModel;
import nl.itopia.corendon.model.StatusModel;
import nl.itopia.corendon.mvc.Controller;

/**
 * @author Jeroentje
 */
public class SearchLuggageController extends Controller {

    @FXML private ChoiceBox foundonAirportdropdown, colorDropdown;
    @FXML private Button searchButton, cancelButton;
    @FXML private TextField labelInputfield, brandInputfield, heightInputfield, widthInputfield, depthInputfield, weightInputfield, notesInputfield;
    @FXML private RadioButton foundluggageRadiobutton,lostluggageRadiobutton,resolvedluggageRadiobutton;
    @FXML private DatePicker datepicker1, datepicker2;
    
    private AirportModel airportModel;
    private ColorModel colorModel;
    private List<Luggage> luggageList;
    
    public SearchLuggageController() {
        
        // Set view
        registerFXML("gui/Search_luggage.fxml");
        
        airportModel = AirportModel.getDefault();
        colorModel = ColorModel.getDefault();

        /* keep brands disabled until brands are implemented */
        brandInputfield.disableProperty();
        
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
        searchButton.setOnAction(this::searchHandler);
    }
    
    public void cancelHandler(ActionEvent e){
        removeController(this);
    }
    
    public void searchHandler(ActionEvent e) {
        String label,  brand, notes, weight, colorName, airportName;
        String[] dimensions = {
                widthInputfield.getText(),
                heightInputfield.getText(),
                depthInputfield.getText(),
                "cm"
        };
        
        LocalDate beginDate = datepicker1.getValue();
        LocalDate endDate   = datepicker2.getValue();
        
        label = labelInputfield.getText();
        brand = brandInputfield.getText();
        weight = weightInputfield.getText();
        notes = notesInputfield.getText();
        colorName = colorDropdown.getSelectionModel().getSelectedItem().toString();
        airportName  = foundonAirportdropdown.getSelectionModel().getSelectedItem().toString();
        
        Color color = colorModel.getColor(colorName);
        Airport airport = airportModel.getAirport(airportName);
        
        LuggageModel luggagemodel = LuggageModel.getDefault();
        SearchModel searchmodel = SearchModel.getDefault();
        
        Luggage luggage = new Luggage();
        
        if(lostluggageRadiobutton.isSelected()) {
            /* lost lugage is selected */
            Status status = StatusModel.getDefault().getStatus(1);
            luggage.status = status;
        }
        
        if(foundluggageRadiobutton.isSelected()) {
            /* found luggage is selected */
            Status status = StatusModel.getDefault().getStatus(2);
            luggage.status = status;
        }
        
        if(resolvedluggageRadiobutton.isSelected()) {
            /* resolved luggage is selected */
            Status status = StatusModel.getDefault().getStatus(3);
            luggage.status = status;
        }

        luggage.label = label;
        luggage.notes = notes;
        luggage.weight = weight;
        luggage.color = color;
        luggage.airport = airport;
        luggage.setDimensions(dimensions);
        
        luggageList = searchmodel.initSearch(luggage, beginDate, endDate);
        
        removeController(this);
    }
    
    @Override
    protected Object destroyReturn() {
        return luggageList;
    }
}
