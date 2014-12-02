package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.mvc.Controller;

import nl.itopia.corendon.pdf.LuggageReportPDF;

import java.io.File;

import static nl.itopia.corendon.pdf.ManagerStatisticsPDF.generateManagerReportPDF;

/**
 * © 2014, Biodiscus.net robin
 */
public class CustomerController extends Controller {
    @FXML private TextField firstnameInputfield, lastnameInputfield, addressInputfield, zipcodeInputfield,
                            countyInputfield, emailInputfield, phoneInputfield;
    @FXML private Button cancelButton, printdetailsButton;

    private Luggage currentLuggage;

    public CustomerController(Luggage luggage) {
        registerFXML("gui/Found_luggage_input_person.fxml");
        currentLuggage = luggage;

        cancelButton.setOnAction(this::cancelHandler);
        printdetailsButton.setOnAction(this::printDetailsButton);
    }

    private void printDetailsButton(ActionEvent e) {
        //SAVE FILE WITH FILECHOOSER
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select location to save PDF.");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        File pdf = fileChooser.showSaveDialog(view.getScene().getWindow());


        LuggageReportPDF.generateLuggageReportPDF(pdf, currentLuggage);
        System.out.println("PDF OF CUSTOMER SAVED");
    }

    private void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
