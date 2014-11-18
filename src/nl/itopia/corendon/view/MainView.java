package nl.itopia.corendon.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.itopia.corendon.mvc.View;

/**
 * Look at this: http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
 *
 *
 * © 2014, Biodiscus.net Robin
 */
public class MainView extends View {
    public Label welcome_label, user_label;
    public Button help_button;
    public Button summary, baggage, log;

    private HBox header_pane;
    private BorderPane border;
    public MainView(int width, int height) {
        super(width, height);

        border = new BorderPane();
        addComponent(border);

        // <editor-fold desc="Header">
        header_pane = new HBox();
        header_pane.setStyle("-fx-background-color: #CECECE");
        border.setTop(header_pane);

        // <editor-fold desc="Header: Buttons">
        HBox header_button_pane = new HBox();
        header_button_pane.setPrefSize(width/2, 50);
        header_button_pane.setAlignment(Pos.CENTER);
        header_button_pane.setSpacing(10);
        header_pane.getChildren().add(header_button_pane);

        summary = new Button("Summary");
        header_button_pane.getChildren().add(summary);

        baggage = new Button("Baggage");
        header_button_pane.getChildren().add(baggage);

        log = new Button("Log book");
        header_button_pane.getChildren().add(log);

        // </editor-fold>

        // <editor-fold desc="Header: User info">
        HBox header_info_pane = new HBox();
        header_info_pane.setSpacing(30);
        header_info_pane.setPadding(new Insets(10));
        header_info_pane.setAlignment(Pos.CENTER_RIGHT);
        header_info_pane.setPrefSize(width/2, 50);

        header_pane.getChildren().add(header_info_pane);

        VBox info_pane = new VBox();
        header_info_pane.getChildren().add(info_pane);

        user_label = new Label("User: 69");
        info_pane.getChildren().add(user_label);

        welcome_label = new Label("Welkom terug: Erik Schouten");
        info_pane.getChildren().add(welcome_label);

        help_button = new Button("?");
        help_button.setPrefSize(50, 50);
        header_info_pane.getChildren().add(help_button);
        // </editor-fold>

        // </editor-fold>

        // <editor-fold desc="Content">

        // <editor-fold desc="Baggage display">
        VBox content_pane = new VBox();
        border.setCenter(content_pane);

        ScrollPane scroll_pane = new ScrollPane();
        scroll_pane.setPrefSize(width, height - 50);
        scroll_pane.setStyle("-fx-background-color: transparent");
        content_pane.getChildren().add(scroll_pane);

        VBox scroll_content_pane = new VBox();
        scroll_pane.setContent(scroll_content_pane);
        scroll_content_pane.setSpacing(10);

        for(int i = 0; i < 20; i ++) {
            VBox display = new VBox();
            scroll_content_pane.getChildren().add(display);

            Label title = new Label("Lost baggage 11-11-2014 18:15");
            title.setStyle("-fx-font-weight: bold");
            display.getChildren().add(title);

            Label found = new Label("On: John F. Kennedy International");
            display.getChildren().add(found);

            Label info = new Label("Info: Samsonite - blue");
            display.getChildren().add(info);
        }

        // </editor-fold>

        // </editor-fold>
    }
}
// <editor-fold desc="">
// </editor-fold>
