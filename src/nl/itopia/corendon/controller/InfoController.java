package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.itopia.corendon.mvc.Controller;

/**
 *
 * @author wieskueter.com
 */
public class InfoController extends Controller {
    
    @FXML private Button exitButton;
    
    public InfoController()
    {
        registerFXML("gui/info_screen.fxml");
        System.out.println("This is the info controller");
<<<<<<< HEAD
        exitButton.setOnAction(this::exitHandler);
    }
    protected void exitHandler(ActionEvent e) {
        removeController(this);
    }
}
=======
        
        exitButton.setOnAction(this::cancelHandler);
    }
    
    public void cancelHandler(ActionEvent e){
        removeController(this);
    }
    
}
>>>>>>> origin/master
