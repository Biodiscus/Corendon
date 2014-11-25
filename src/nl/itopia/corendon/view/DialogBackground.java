package nl.itopia.corendon.view;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.itopia.corendon.mvc.View;

/**
 * © 2014, Biodiscus.net Robin
 */
public class DialogBackground extends View {
    public DialogBackground(Scene scene) {
        double width = scene.getWidth();
        double height = scene.getHeight();

        Color color = new Color(0, 0, 0, 0.5);

        Rectangle rectangle = new Rectangle(width, height, color);
        getChildren().add(rectangle);
    }
}
