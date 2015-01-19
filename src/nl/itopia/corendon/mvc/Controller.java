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

/**
 * © Biodiscus.net 2014, Robin
 */
public abstract class Controller {
    
    private MVC mvc;
    protected View view;

    private ObjectDelete controllerDeleteHandler;

    public Controller() {}

    /**
     * Register a FXML View. The FXML view will be added to a new View.
     * @param path String
     */
    public void registerFXML(String path) {
        registerFXML(path, new View());
    }

    /**
     * Register a FXML View. The FXML view will be added to the View.
     * @param path String
     * @param view View
     */
    public void registerFXML(String path, View view) {
        this.view = view;

        // Get the URL inside the JAR
        URL url = IO.get(path);
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(url);
            loader.setController(this);
            view.fxmlPane = loader.load();

            // Add it to the view
            view.getChildren().add(view.fxmlPane);
        } catch (IOException e) {
            Log.display("IOEXCEPTION", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Set the current MVC engine, this way we can change the controller in a controller
     * @param mvc MVC
     */
    public final void setMVCEngine(MVC mvc) {
        this.mvc = mvc;
    }

    /**
     * Change the current controller to the new one
     * @param controller Controller
     */
    public final void changeController(Controller controller) {
        mvc.setController(controller);
    }

    /**
     * Adds a new controller to the scene. If no root is given, the Controller will be added to the current controller
     * (The one that called this function)
     * @param controller Controller
     */
    public final void addController(Controller controller) {
        addController(controller, getView());
    }

    /**
     * Adds a new controller to the scene. If no root is given, the Controller will be added to the current controller
     * (The one that called this function)
     * @param controller Controller
     */
    public final void addController(Controller controller, Pane root) {
        // If the root is null, change the controller instead
        if(root == null) {
            changeController(controller);
        } else {
            // Add a shadow on the view
            Scene scene = root.getScene();

            DialogBackground dialogBackground = new DialogBackground(scene);
            // Set the dialog background so the method removeController can remove the view
            controller.getView().dialogBackground = dialogBackground;

            ObservableList<Node> nodeList = root.getChildren();
            nodeList.add(dialogBackground);
            nodeList.add(controller.getView());
        }
    }

    /**
     * Removes the given controller. If a controller delete handler is set, fire it.
     * Override destroyReturn() to specify what object will be return upon deletion.
     * @param controller
     */
    public final void removeController(Controller controller) {
        
        Pane parent = (Pane)controller.getView().getParent();
        // TODO: When the root in addController is null, it should do something else.
        if(parent != null) {
            View view = controller.getView();

            if(view.dialogBackground != null) {
                Pane dialogParent = (Pane) view.dialogBackground.getParent();
                dialogParent.getChildren().remove(view.dialogBackground);
            }

            parent.getChildren().remove(view);
        }

        if(controllerDeleteHandler != null) {
            controllerDeleteHandler.action(destroyReturn());
        }
    }

    /**
     * When creating the class, the programs allows for a ObjectDelete handler.
     * This will send an object to the set handler, with a object from the method destroyReturn()
     * @param handler
     */
    public void setControllerDeleteHandler(ObjectDelete handler) {
        this.controllerDeleteHandler = handler;
    }

    // Override this function to give the destroy handler an object to return

    /**
     * Override the destroyReturn function to specify what object to send with the handler when destroyed.
     * @return
     */
    protected Object destroyReturn() {
        return null;
    }

    /**
     * Get the view associated with the controller
     * @return
     */
    public View getView() {
        return view;
    }
}
