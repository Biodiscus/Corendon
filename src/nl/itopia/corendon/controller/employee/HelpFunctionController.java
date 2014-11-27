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
 * @author Stefan de Groot
 */
public class helpFunctionController extends Controller {

   
    @FXML private Button cancelButton;
    
   
    
    public helpFunctionController() {
        registerFXML("gui/Help_function.fxml");
        
       cancelButton.setOnAction(this::cancelHandler);
    }
    
    public void cancelHandler(ActionEvent e){
        removeController(this);
    }
    
    
}
