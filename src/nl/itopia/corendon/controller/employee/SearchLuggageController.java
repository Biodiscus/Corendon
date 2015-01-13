package nl.itopia.corendon.controller.employee;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import nl.itopia.corendon.components.AutoCompleteComboBoxListener;
import nl.itopia.corendon.data.*;
import nl.itopia.corendon.model.*;
import nl.itopia.corendon.mvc.Controller;

/**
 * @author Jeroentje
 */
public class SearchLuggageController extends Controller {

    @FXML private ChoiceBox foundonAirportdropdown, colorDropdown;
    @FXML private Button searchButton, cancelButton;
    @FXML private TextField labelInputfield, heightInputfield, widthInputfield, depthInputfield, weightInputfield, notesInputfield;
    @FXML private ComboBox brandInputfield;
    @FXML private RadioButton foundluggageRadiobutton,lostluggageRadiobutton,resolvedluggageRadiobutton;
    @FXML private DatePicker datepicker1, datepicker2;

    private AutoCompleteComboBoxListener<ChooseItem> comboBoxListener;

    private AirportModel airportModel;
    private ColorModel colorModel;
    private BrandModel brandModel;
    private List<Luggage> luggageList;

    public SearchLuggageController() {
        
        // Set view
        registerFXML("gui/Search_luggage.fxml");
        
        airportModel = AirportModel.getDefault();
        colorModel = ColorModel.getDefault();
        brandModel = BrandModel.getDefault();
        
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

        // Fill the brand input with the brands in the system
        List<Brand> brands = brandModel.getBrands();
        ObservableList<ChooseItem> brandData = FXCollections.observableArrayList();

        for(Brand brand : brands) {
            ChooseItem c = brandModel.brandToChoose(brand);
            brandData.add(c);
        }
        brandInputfield.setItems(brandData);

        // Because we set the combobox editable to true, we need to implement our StringConverter
        brandInputfield.setConverter(new StringConverter<ChooseItem>() {
            @Override
            public String toString(ChooseItem object) {
                if(object == null) return null;
                return object.toString();
            }

            @Override
            public ChooseItem fromString(String string) {
                //TODO: Implement a factory patern for this
                ChooseItem item = null;
                for(ChooseItem data : brandData) {
                    if(data.toString().equals(string)) {
                        item = data;
                        break;
                    }
                }

                // If the item doesn't exist, create a new one with it's id set to 0
                if(item == null) {
                    item = new ChooseItem(-1, string);
                }
                return item;
            }
        });

        // Give the brand input our combobox listener
        comboBoxListener = new AutoCompleteComboBoxListener(brandInputfield);
        
        cancelButton.setOnAction(this::cancelHandler);
        searchButton.setOnAction(this::searchHandler);
    }
    
    public void cancelHandler(ActionEvent e){
        removeController(this);
    }
    
    public void searchHandler(ActionEvent e) {
        String label, notes, weight, colorName, airportName;
        String[] dimensions = {
            widthInputfield.getText(),
            heightInputfield.getText(),
            depthInputfield.getText(),
            "cm"
        };
        
        LocalDate beginDate = datepicker1.getValue();
        LocalDate endDate   = datepicker2.getValue();
        
        label = labelInputfield.getText();
        weight = weightInputfield.getText();
        notes = notesInputfield.getText();
        colorName = colorDropdown.getSelectionModel().getSelectedItem().toString();
        airportName  = foundonAirportdropdown.getSelectionModel().getSelectedItem().toString();
        
        Color color = colorModel.getColor(colorName);
        Airport airport = airportModel.getAirport(airportName);
        Brand brand;

        // Check if the brand input value is null, if get the 'None' brand.
        // Otherwise get the correct brand
        if(brandInputfield.getValue() == null) {
            brand = brandModel.getBrand("None");
        } else {
            brand = brandModel.getBrand(brandInputfield.getValue().toString());
        }

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

        luggage.brand = brand;
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
