package nl.itopia.corendon.components;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nl.itopia.corendon.mvc.ObjectDelete;

/**
 * Used to show the images of our system,
 * if editable is set to true the user can delete the image
 *
 * © 2014, Biodiscus.net Robin
 */
public class PictureView extends Pane {
    private ImageView imageView;
    private Image image;
    private String path;

    private Button deleteButton;

    private boolean editable;

    private ObjectDelete objectDelete;

    public PictureView(String path, double width, double height, boolean preserveRatio) {
        this.path = path;

        image = new Image(path, width, height, preserveRatio, true);
        imageView = new ImageView(image);
        getChildren().add(imageView);

        deleteButton = new Button("Delete");
        deleteButton.setOnAction(this::deleteHandler);
        getChildren().add(deleteButton);

        editable = false;
    }

    private void deleteHandler(ActionEvent event) {
        if(objectDelete != null) {
            objectDelete.action(this);
        }
    }

    public void setOnDelete(ObjectDelete objectDelete) {
        this.objectDelete = objectDelete;
    }

    public String getImagePath() {
        return path;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean getEditable() {
        return editable;
    }
}
