package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.mvc.View;
import nl.itopia.corendon.utils.Log;

/**
 * © 2014, Biodiscus.net Robin
 */
public class MainController extends Controller {

    @FXML public Button test_button;

    public MainController(int width, int height) {
        registerFXML("gui/TestGUI.fxml");

//        EmployeeModel model = EmployeeModel.getDefault();
//        model.getEmployee(0);

        LuggageModel luggageModel = LuggageModel.getDefault();
        Luggage luggage = luggageModel.getLuggage(3);
        Log.display(luggage, luggage.color, luggage.employee);

        test_button.setOnAction(this::handle);
    }

    public void handle(ActionEvent e) {
        Log.display("Button clicked", e);
    }

    @Override
    public View getView() {
        return view;
    }
}
