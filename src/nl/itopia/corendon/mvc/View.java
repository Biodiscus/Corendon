package nl.itopia.corendon.mvc;

import javafx.scene.layout.Pane;

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
