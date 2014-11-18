package nl.itopia.corendon.mvc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import nl.itopia.corendon.utils.IO;
import nl.itopia.corendon.utils.Log;

import java.io.IOException;
import java.net.URL;

/**
 * © Biodiscus.net 2014, Robin
 */
public class View extends Pane {
    public Pane fxmlPane;

    public View() {}

    public View(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
}
