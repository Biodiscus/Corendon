package nl.itopia.corendon.mvc;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import nl.itopia.corendon.controller.MainController;
import nl.itopia.corendon.utils.IO;
import nl.itopia.corendon.utils.Log;

import java.io.IOException;
import java.net.URL;

/**
 * © Biodiscus.net 2014, Robin
 */
public abstract class Controller {
    private MVC mvc;
    protected View view;

    public Controller() {}

    public void registerFXML(String path) {
        registerFXML(path, new View());
    }

    public void registerFXML(String path, View view) {
        this.view = view;

        URL url = IO.get(path);
        try {
            view.fxmlPane = new Pane();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setController(this);
            view.fxmlPane = loader.load();
            view.getChildren().add(view.fxmlPane);
        } catch (IOException e) {
            Log.display("IOEXCEPTION", e.getMessage());
        }
    }

    public final void setMVCEngine(MVC mvc) {
        this.mvc = mvc;
    }

    public final void changeController(Controller controller) {
        mvc.setController(controller);
    }

    public View getView() {
        return view;
    }
}
