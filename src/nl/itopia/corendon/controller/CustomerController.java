package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.mvc.Controller;
import java.io.File;
import nl.itopia.corendon.data.Customer;
import nl.itopia.corendon.data.Status;
import nl.itopia.corendon.model.LuggageModel;
import static nl.itopia.corendon.pdf.LuggageResolvedPDF.generateLuggageResolvedPDF;

/**
 * © 2014, Biodiscus.net robin
 */
public class CustomerController extends Controller {
    
    @FXML private TextField firstnameInputfield, lastnameInputfield, addressInputfield, zipcodeInputfield,
        countryInputfield, emailInputfield, phoneInputfield, stateInputfield;
    @FXML private Button cancelButton, printdetailsButton;

    private Luggage currentLuggage;
    private LuggageModel luggageModel;
    private Customer customer;

    public CustomerController(Luggage luggage) {
        
        // Set view
        registerFXML("gui/Found_luggage_input_person.fxml");
        
        currentLuggage = luggage;
        
        cancelButton.setOnAction(this::cancelHandler);
        printdetailsButton.setOnAction(this::printDetailsButton);
    }
    
    //AND SET LUGGAGE ON RESOLVED
    private void printDetailsButton(ActionEvent e) {
        
        //SAVE FILE WITH FILECHOOSER 
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select location to save PDF.");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        File pdf = fileChooser.showSaveDialog(view.getScene().getWindow());
        
        customer = new Customer(0);
        customer.firstName = firstnameInputfield.getText();
        customer.lastName = lastnameInputfield.getText();
        customer.address = addressInputfield.getText();
        customer.email = emailInputfield.getText();
        // customer.country.name = countryInputfield.getText();
        customer.zipcode = zipcodeInputfield.getText();
        customer.phone = phoneInputfield.getText();
        customer.state = stateInputfield.getText();

        generateLuggageResolvedPDF(pdf, currentLuggage, customer);
        System.out.println("PDF OF CUSTOMER SAVED");
        
        currentLuggage.status = new Status(3, "Resolved");
        luggageModel = LuggageModel.getDefault();
        luggageModel.editLuggage(currentLuggage);
        removeController(this);
    }

    private void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
