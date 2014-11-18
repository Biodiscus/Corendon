package nl.itopia.corendon.mvc;

import javafx.fxml.FXMLLoader;
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

    // Called when JavaFX loaded
    public void initialize() {}

    public void registerFXML(String path) {
        registerFXML(path, new View());
    }

    public void registerFXML(String path, View view) {
        this.view = view;

        URL url = IO.get(path);
        try {
            view.fxmlPane = FXMLLoader.load(url);
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
