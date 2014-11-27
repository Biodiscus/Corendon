package nl.itopia.corendon.controller.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import nl.itopia.corendon.data.*;
import nl.itopia.corendon.model.*;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.Log;

import java.util.List;


/**
 * © 2014, Biodiscus.net Robin
 */
public class AddLuggageController extends Controller {
    @FXML private Button addButton, cancelButton, browseButton;


    // TODO: brandInputfield should have it's own component!
    @FXML private TextField labelInputfield, fileInputfield, brandInputfield, heightInputfield, weightInputfield,
                            notesInputfield, widthInputfield, depthInputfield;

    @FXML private ChoiceBox foundonAirportdropdown, colorDropdown;


    private LuggageModel luggageModel;
    private AirportModel airportModel;
    private ColorModel colorModel;

    private Luggage currentLuggage;

    public AddLuggageController() {
        registerFXML("gui/add_luggage.fxml");

        luggageModel = LuggageModel.getDefault();
        airportModel = AirportModel.getDefault();
        colorModel = ColorModel.getDefault();

        brandInputfield.setDisable(true);


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

        addButton.setOnAction(this::addHandler);
        cancelButton.setOnAction(this::cancelHandler);
    }

    private void addHandler(ActionEvent e) {
        // TODO: Should we reference the Color or Airport in the ChooseItem?
        ChooseItem airport = (ChooseItem)foundonAirportdropdown.getValue();
        ChooseItem color = (ChooseItem)colorDropdown.getValue();

        Luggage luggage = new Luggage();
        luggage.color = ColorModel.getDefault().getColor(color.getKey());
        luggage.status = StatusModel.getDefault().getStatus(1);
        luggage.employee = EmployeeModel.getDefault().currentEmployee;
        luggage.customer = CustomerModel.getDefault().getCustomer(2);
        luggage.airport = airportModel.getAirport(airport.getKey());

        String[] dimensions = {
                widthInputfield.getText(),
                heightInputfield.getText(),
                depthInputfield.getText(),
                "cm"
        };

        luggage.setDimensions(dimensions);


        luggage.label = labelInputfield.getText();
        luggage.notes = notesInputfield.getText();
        luggage.weight = weightInputfield.getText();
        luggage.brand = BrandModel.getDefault().getBrand(1);

        long currentTimeStamp = DateModel.getDefault().getCurrentTimeStamp();

        luggage.foundDate = currentTimeStamp;
        luggage.createDate = currentTimeStamp;
        luggage.returnDate = 0;

        currentLuggage = luggage;
        luggageModel.addLuggage(luggage);
        removeController(this);
    }

    @Override
    protected Object destroyReturn() {
        return currentLuggage;
    }

    private void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
