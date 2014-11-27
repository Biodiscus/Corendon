package nl.itopia.corendon.controller.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.itopia.corendon.mvc.Controller;

/**
 *
 * @author wieskueter.com
 */
public class editEmployeeController extends Controller{
    
    @FXML private Button addButton, cancelButton;
    
    public editEmployeeController()
    {
        // Set view
        registerFXML("gui/add_user.fxml");
        
        cancelButton.setOnAction(this::cancelHandler);
    }
    
    private void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
