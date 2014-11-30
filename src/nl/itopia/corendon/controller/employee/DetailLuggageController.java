package nl.itopia.corendon.controller.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import static nl.itopia.corendon.controller.employee.CreatePDF.generateLuggageReportPDF;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.mvc.Controller;

/**
 * © 2014, Biodiscus.net Robin
 */
public class DetailLuggageController extends Controller {

    @FXML private Label showFoundonairport, showLabel, showColor, showBrand, showWeight,
                        showHeight, showNotes, showWidth, showDepth;

    @FXML private Button printdetailsButton, cancelButton, markasfoundButton, editButton, deleteButton;

    private Luggage currentLuggage;


    public DetailLuggageController(int luggageID) {
        this(LuggageModel.getDefault().getLuggage(luggageID));
    }

    public DetailLuggageController(Luggage luggage) {
        registerFXML("gui/show_details_luggage.fxml");
        currentLuggage = luggage;

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
//        markasfoundButton.setOnAction(this::markHandler);
//        editButton.setOnAction(this::editHandler);
//        deleteButton.setOnAction(this::deleteHandler);
//        printdetailsButton.setDisable(true);
        markasfoundButton.setDisable(true);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    public void printHandler(ActionEvent e) {
        generateLuggageReportPDF(currentLuggage);
        System.out.println("PDF OF LUGGAGE REPORT SAVED IN C:/");  
    }

    public void markHandler(ActionEvent e) {
        // Mark Dialog/Controller(?)
    }

    public void deleteHandler(ActionEvent e) {
        // Delete dialog
    }

    protected void editHandler(ActionEvent e) {
        // Set the edit controller
        Pane parent = (Pane)getView().getParent();
        addController(new EditLuggageController(currentLuggage), parent);
        removeController(this);
    }

    protected void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
