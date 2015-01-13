package nl.itopia.corendon.controller.employee;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.itopia.corendon.controller.CustomerController;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.data.Picture;
import nl.itopia.corendon.model.ImageModel;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.mvc.Controller;

import java.util.List;
import javafx.stage.FileChooser;

import static nl.itopia.corendon.pdf.LuggageReportPDF.generateLuggageReportPDF;

/**
 * © 2014, Biodiscus.net Robin
 */
public class DetailLuggageController extends Controller {

    @FXML private Label showFoundonairport, showLabel, showColor, showBrand, showWeight,
            showHeight, showNotes, showWidth, showDepth;
    @FXML private Button printdetailsButton, cancelButton, markasfoundButton, editButton, deleteButton;
    @FXML  private ScrollPane imageScrollpane;
    private VBox imageScrollContent;

    private ImageModel imageModel;
    private Luggage currentLuggage;

    public DetailLuggageController(int luggageID) {
        this(LuggageModel.getDefault().getLuggage(luggageID));
    }

    public DetailLuggageController(Luggage luggage) {
        
        // Set view
        registerFXML("gui/show_details_luggage.fxml");
        currentLuggage = luggage;

        imageModel = ImageModel.getDefault();

        showFoundonairport.setText(luggage.airport.getName());
        showLabel.setText(luggage.label);
        showColor.setText(luggage.color.getHex());
        showBrand.setText(luggage.brand.getName());
        showWeight.setText(luggage.weight + " KG");
        showNotes.setText(luggage.notes);

        String[] dimensions = luggage.getDimensions();
        showWidth.setText(dimensions[0]);
        showHeight.setText(dimensions[1]);
        showDepth.setText(dimensions[2] + " " + dimensions[3]);

        // Set the imageScrollpane content
        imageScrollContent = new VBox();
        imageScrollpane.setContent(imageScrollContent);
        // Get the photos
        List<Picture> pictures = imageModel.getPicturesFromLuggage(luggage.getID());
        for (Picture pic : pictures) {
            double width = imageScrollpane.getPrefWidth() - 50;
            Image image = new Image(pic.getPath(), width, 0, true, true);
            imageScrollContent.getChildren().add(new ImageView(image));
        }

        printdetailsButton.setOnAction(this::printHandler);
        cancelButton.setOnAction(this::cancelHandler);
        markasfoundButton.setOnAction(this::markHandler);
        // markasfoundButton.setOnAction(this::markHandler);
        // editButton.setOnAction(this::editHandler);
        // deleteButton.setOnAction(this::deleteHandler);
        // printdetailsButton.setDisable(true);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    public void printHandler(ActionEvent e) {
        //SAVE FILE WITH FILECHOOSER
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select location to save PDF.");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        
        File file = fileChooser.showSaveDialog(view.getScene().getWindow());
        
        System.out.println("PDF OF MANAGER REPORT SAVED");           
        generateLuggageReportPDF(file, currentLuggage);
    }

    public void markHandler(ActionEvent e) {
        Pane parent = (Pane)getView().getParent();
        addController(new CustomerController(currentLuggage), parent);
        removeController(this);
    }

    public void deleteHandler(ActionEvent e) {
        // Delete dialog
    }

    protected void editHandler(ActionEvent e) {
        // Set the edit controller
        Pane parent = (Pane) getView().getParent();
        addController(new EditLuggageController(currentLuggage), parent);
        removeController(this);
    }

    protected void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
