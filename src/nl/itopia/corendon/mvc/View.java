package nl.itopia.corendon.mvc;

import javafx.scene.layout.Pane;
import nl.itopia.corendon.view.DialogBackground;

/**
 * © Biodiscus.net 2014, Robin
 */
public class View extends Pane {
    // The pane the FXML will be added to
    public Pane fxmlPane;

    // For dialogs we need a background
    // If it's null than there is no background
    public DialogBackground dialogBackground;

    public View() {}

    public View(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
}
