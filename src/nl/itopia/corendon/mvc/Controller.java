package nl.itopia.corendon.mvc;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import nl.itopia.corendon.utils.IO;
import nl.itopia.corendon.utils.Log;
import nl.itopia.corendon.view.DialogBackground;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

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
            e.printStackTrace();
        }
    }

    public final void setMVCEngine(MVC mvc) {
        this.mvc = mvc;
    }

    public final void changeController(Controller controller) {
        mvc.setController(controller);
    }


    public final void addController(Controller controller) {
        addController(controller, getView());
    }

    public final void addController(Controller controller, Pane root) {
        // If the root is null, change the controller instead
        if(root == null) {
            changeController(controller);
        } else {
            // Add a shadow on the view
            Scene scene = root.getScene();
//            controller.getView().getChildren().add(new DialogBackground(scene));
            DialogBackground dialogBackground = new DialogBackground(scene);
            // Set the dialog background so the method removeController can remove the view
            controller.getView().dialogBackground = dialogBackground;


            ObservableList<Node> nodeList = root.getChildren();
            nodeList.add(dialogBackground);
            nodeList.add(controller.getView());
        }
    }

    public final void removeController(Controller controller) {
        Pane parent = (Pane)controller.getView().getParent();
        // TODO: When the root in addController is null, it should do something else.
        if(parent != null) {
            View view = controller.getView();
            // When the dialog background is not null, delete it from the view
            if(view.dialogBackground != null) {
                Pane dialogParent = (Pane) view.dialogBackground.getParent();
                dialogParent.getChildren().remove(view.dialogBackground);
            }

            parent.getChildren().remove(view);
        }
    }

    public View getView() {
        return view;
    }
}
