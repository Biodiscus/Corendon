package nl.itopia.corendon.utils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import nl.itopia.corendon.data.ChooseItem;

/**
 *
 * @author Wies Kueter
 */
public class Validation {

    @FXML private TextField usernameInputfield, firstnameInputfield, lastnameInputfield, passwordInputfield, repeatpasswordInputfield, contactdetailsInputfield, notesInputfield;
    @FXML private ChoiceBox<ChooseItem> roleDropdownmenu, airportDropdownmenu;
    @FXML private Button addButton, cancelButton;
    
    public static boolean minMax(String field, int min, int max)  {
        
        if(field.length() >= min && field.length() <= max) {
            return true;
        }
        
        return false;
    }
    
    public static boolean min(String field, int min) {
        
        return field.length() >= min;
    }
    
    public static boolean max(String field, int max) {
        
        return field.length() <= max;
    }
    
    public static void errorMessage(TextField field, String message) {

        field.setText("");
        field.setPromptText(message);
        field.getStyleClass().add("error_prompt");
    }
}
