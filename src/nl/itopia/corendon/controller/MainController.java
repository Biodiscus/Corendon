package nl.itopia.corendon.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.mvc.View;

/**
 * © 2014, Biodiscus.net Robin
 */
public class MainController extends Controller {

    @FXML private Button test_button;

    public MainController(int width, int height) {
//        view = new MainView(width, height);
        registerFXML("gui/TestGUI.fxml");

        EmployeeModel model = EmployeeModel.getDefault();
    }

    @Override
    public void initialize() {
        System.out.println(test_button);
    }

    @Override
    public View getView() {
        return view;
    }
}
