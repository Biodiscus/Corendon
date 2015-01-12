package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.itopia.corendon.mvc.Controller;

/**
 * @author Stefan de Groot
 */
public class HelpFunctionController extends Controller {

   
    @FXML private Button exitButton;
    
    public HelpFunctionController() {
        
        // Set view
        registerFXML("gui/help_function.fxml");
        
       exitButton.setOnAction(this::cancelHandler);
    }
    
    public void cancelHandler(ActionEvent e){
        removeController(this);
    }
}
