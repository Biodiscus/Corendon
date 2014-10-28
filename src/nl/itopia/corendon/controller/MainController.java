package nl.itopia.corendon.controller;

import javafx.scene.control.Button;
import nl.itopia.corendon.model.MainModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.mvc.View;
import nl.itopia.corendon.utils.Log;
import nl.itopia.corendon.view.MainView;

/**
 * © 2014, Biodiscus.net Robin
 */
public class MainController extends Controller {
    private MainView view;
    private MainModel model;

    public MainController(int width, int height) {
        view = new MainView(width, height);
        model = MainModel.getDefault();


        view.button.setOnAction((e) -> Log.display("Hallo Wereld!"));

        for(Button button : view.buttons) {
            button.setOnAction((e) -> Log.display("Button text; "+button.getText()));
        }
    }

    @Override
    public View getView() {
        return view;
    }
}
