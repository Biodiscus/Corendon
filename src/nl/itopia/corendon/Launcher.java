package nl.itopia.corendon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.itopia.corendon.controller.MainController;
import nl.itopia.corendon.mvc.MVC;
import nl.itopia.corendon.utils.Log;

/**
 * © 2014, Biodiscus.net Robin
 */
public class Launcher extends Application {
    public static final String VERSION = "0.1";
    public static final String TITLE = "Corendon";

    public static final int WIDTH = 1000;
    public static final int HEIGHT = WIDTH / 16 * 9;

    public MVC mvcEngine;

    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(TITLE + ", V"+VERSION);

        mvcEngine = new MVC((e)->{
            scene = new Scene(e, e.getWidth(), e.getHeight());

            scene.getStylesheets().clear();
            scene.getStylesheets().add("stylesheets/style.css");

            stage.setScene(scene);

            Log.display("Changing view");
        });
        mvcEngine.setController(new MainController(WIDTH, HEIGHT));


        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
