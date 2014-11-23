package nl.itopia.corendon.mvc;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import nl.itopia.corendon.utils.IO;
import nl.itopia.corendon.utils.Log;
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
            root.getChildren().add(controller.getView());
        }
    }

    public final void removeController(Controller controller) {
        Pane parent = (Pane)controller.getView().getParent();
        // TODO: When the root in addController is null, it should do something else.
        if(parent != null) {
            parent.getChildren().remove(controller.getView());
        }
    }

    public View getView() {
        return view;
    }
    
    
    
    // Functions shared throughout the entire framework
    public String sha256(String value) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }
}
