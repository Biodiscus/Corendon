package nl.itopia.corendon.controller.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.mvc.Controller;

/**
 * © 2014, Biodiscus.net Robin
 */
public class DetailLuggageController extends Controller {
    private LuggageModel luggageModel;

    @FXML private Label showFoundonairport, showLabel, showColor, showBrand, showWeight,
                        showHeight, showNotes, showWidth, showDepth;

    @FXML private Button printdetailsButton, cancelButton, markasfoundButton, editButton, deleteButton;

    public DetailLuggageController(int luggageID) {
        this(LuggageModel.getDefault().getLuggage(luggageID));
    }

    public DetailLuggageController(Luggage luggage) {
        registerFXML("gui/show_details_luggage.fxml");

        luggageModel = LuggageModel.getDefault(); // TODO: Is this one needed?

        showFoundonairport.setText(luggage.airport.getName());
        showLabel.setText(luggage.label);
        showColor.setText(luggage.color.getHex());
        showBrand.setText(luggage.brand.getName());
        showWeight.setText(luggage.weight);
        showNotes.setText(luggage.notes);

        String[] dimensions = luggage.getDimensions();
        showWidth.setText(dimensions[0]);
        showHeight.setText(dimensions[1]);
        showDepth.setText(dimensions[2] + " " + dimensions[3]);

        printdetailsButton.setOnAction(this::printHandler);
        cancelButton.setOnAction(this::cancelHandler);
        markasfoundButton.setOnAction(this::markHandler);
        editButton.setOnAction(this::editHandler);
        deleteButton.setOnAction(this::deleteHandler);
    }

    public void printHandler(ActionEvent e) {
        // Print Controller
    }

    public void markHandler(ActionEvent e) {
        // Mark Dialog/Controller(?)
    }

    public void deleteHandler(ActionEvent e) {
        // Delete dialog
    }

    protected void editHandler(ActionEvent e) {
        // Edit Controller
    }

    protected void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
