package nl.itopia.corendon.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import nl.itopia.corendon.mvc.View;

/**
 * Look at this: http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
 *
 *
 * © 2014, Biodiscus.net Robin
 */
public class MainView extends View {
    public Button button;
    public Button[] buttons;

    public TextField textField;

    private VBox pane;

    public MainView(int width, int height) {
        super(width, height);

        pane = new VBox();
        pane.setPadding(new Insets(15, 12, 15, 12));
        pane.setSpacing(10);
        pane.setStyle("-fx-background-color: #BB1D14");
        addComponent(pane);

        button = new Button();
        button.setId("custom-button");
        button.setText("Klik mij!");
        pane.getChildren().add(button);

        textField = new TextField("Hello World!");
        pane.getChildren().add(textField);

        buttons = new Button[5];

        for(int i = 0; i < buttons.length; i ++) {
            buttons[i] = new Button();
            buttons[i].setText("Hallo: " + i);

            buttons[i].setPrefWidth(20 + Math.random() * (width - 20));


            pane.getChildren().add(buttons[i]);
        }



    }
}
