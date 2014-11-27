package nl.itopia.corendon.controller.administrator;

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
public class HelpFunctionControllerAdmin extends Controller {

   
    @FXML private Button exitButton;
    
   
    
    public HelpFunctionControllerAdmin() {
        registerFXML("gui/Help_function.fxml");
        
       exitButton.setOnAction(this::cancelHandler);
    }
    
    public void cancelHandler(ActionEvent e){
        removeController(this);
    }
    
    
}
