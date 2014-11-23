package nl.itopia.corendon.controller.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import nl.itopia.corendon.data.*;
import nl.itopia.corendon.model.*;
import nl.itopia.corendon.mvc.Controller;


/**
 * © 2014, Biodiscus.net Robin
 */
public class AddLuggageController extends Controller {
    private @FXML Button addButton, cancelButton, browseButton;


    // TODO: brandInputfield should have it's own component!
    @FXML
    private TextField labelInputfield, fileInputfield, brandInputfield, heightInputfield, weightInputfield, notesInputfield,
                      widthInputfield, depthInputfield;

    @FXML
    private ChoiceBox foundonAirportdropdown;


    private LuggageModel luggageModel;

    public AddLuggageController() {
        registerFXML("gui/add_luggage.fxml");

        luggageModel = LuggageModel.getDefault();

//        foundonAirportdropdown

        addButton.setOnAction(this::addHandler);
        cancelButton.setOnAction(this::cancelHandler);
    }

    private void addHandler(ActionEvent e) {
        Luggage luggage = new Luggage();
        luggage.color = ColorModel.getDefault().getColor(1);
        luggage.status = StatusModel.getDefault().getStatus(1);
        luggage.employee = EmployeeModel.getDefault().currentEmployee;
        luggage.customer = CustomerModel.getDefault().getCustomer(2);
        luggage.airport = AirportModel.getDefault().getAirport(1);

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

        luggageModel.addLuggage(luggage);
    }

    private void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
