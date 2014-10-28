package nl.itopia.corendon.mvc;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * © Biodiscus.net 2014, Robin
 */
public class View extends Pane {
    private int width, height;

    public View(int width, int height) {
        this.width = width;
        this.height = height;

        setWidth(width);
        setHeight(height);
    }

    public final void addComponent(Node node) {
        getChildren().add(node);
    }
}
