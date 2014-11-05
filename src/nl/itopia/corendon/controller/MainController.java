package nl.itopia.corendon.controller;

import nl.itopia.corendon.model.MainModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.mvc.View;
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
    }

    @Override
    public View getView() {
        return view;
    }
}
