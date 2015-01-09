package nl.itopia.corendon.controller.employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import nl.itopia.corendon.components.AutoCompleteComboBoxListener;
import nl.itopia.corendon.components.PictureView;
import nl.itopia.corendon.data.*;
import nl.itopia.corendon.model.*;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.DateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * © 2014, Biodiscus.net Robin
 */
public class EditLuggageController extends Controller {
    
    @FXML private TextField labelInputfield, fileInputfield, heightInputfield, weightInputfield,
            notesInputfield, widthInputfield, depthInputfield;

    @FXML private ChoiceBox<ChooseItem> foundonAirportdropdown, colorDropdown;
    @FXML private ScrollPane imageScrollpane;
    @FXML private Button cancelButton, editButton, browseButton;
    @FXML private ComboBox<ChooseItem> brandInput;

    // Used for the brandInput, to give more control to the combobox
    private AutoCompleteComboBoxListener<ChooseItem> comboBoxListener;

    private VBox imageScrollContent;

    private LuggageModel luggageModel;
    private AirportModel airportModel;
    private ColorModel colorModel;
    private ImageModel imageModel;
    private BrandModel brandModel;

    private Luggage currentLuggage;

    // Deleted pictures is used so when the programs edits the suitcases we can loop trough the array and remove the relation
    private List<Picture> deletedPictures, currentPictures;
    private List<File> addedPictures;

    public EditLuggageController(int luggageID) {
        this(LuggageModel.getDefault().getLuggage(luggageID));
    }

    public EditLuggageController(Luggage luggage) {
        
        // Set view
        registerFXML("gui/edit_luggage.fxml");
        currentLuggage = luggage;

        deletedPictures = new ArrayList<>();
        addedPictures = new ArrayList<>();

        luggageModel = LuggageModel.getDefault();
        airportModel = AirportModel.getDefault();
        colorModel = ColorModel.getDefault();
        imageModel = ImageModel.getDefault();
        brandModel = BrandModel.getDefault();

        labelInputfield.setText(luggage.label);
        notesInputfield.setText(luggage.notes);

        String[] dimensions = luggage.getDimensions();
        widthInputfield.setText(dimensions[0]);
        heightInputfield.setText(dimensions[1]);
        depthInputfield.setText(dimensions[2]);

        weightInputfield.setText(luggage.weight);
        
        // Fill the brand input with the brands in the system
        List<Brand> brands = brandModel.getBrands();
        ObservableList<ChooseItem> brandData = FXCollections.observableArrayList();

        for(Brand brand : brands) {
            ChooseItem c = brandModel.brandToChoose(brand);
            brandData.add(c);
        }
        brandInput.setItems(brandData);
        // Set the current brand to the brand of the luggage
        brandInput.setValue(brandModel.brandToChoose(luggage.brand));

        // Because we set the combobox editable to true, we need to implement our StringConverter
        brandInput.setConverter(new StringConverter<ChooseItem>() {
            @Override
            public String toString(ChooseItem object) {
                if(object == null) return null;
                return object.toString();
            }

            @Override
            public ChooseItem fromString(String string) {
                //TODO: Implement a factory patern for this
                ChooseItem item = null;
                for(ChooseItem data : brandData) {
                    if(data.toString().equals(string)) {
                        item = data;
                        break;
                    }
                }
                return item;
            }
        });
        // Give the brand input our combobox listener
        comboBoxListener = new AutoCompleteComboBoxListener(brandInput);

        // Set the imageScrollpane content
        imageScrollContent = new VBox();
        imageScrollpane.setContent(imageScrollContent);

        // Set the Airports in the foundonAirportdropdown
        List<Airport> airports = airportModel.getAirports();
        int currentAirportPlace = 1; // This will hold the place in the choisebox
        for(int i = 0; i < airports.size(); i ++) {
            Airport airport = airports.get(i);
            ChooseItem c = airportModel.airportToChoose(airport);
            foundonAirportdropdown.getItems().add(c);

            if(airport.getID() == luggage.airport.getID()) {
                currentAirportPlace = i;
            }
        }
        foundonAirportdropdown.getSelectionModel().select(currentAirportPlace);

        // Set the Colors in the colorDropdown
        List<Color> colors = colorModel.getColors();
        int currentColorPlace = 1; // This will hold the place in the choisebox
        for(int i = 0; i < colors.size(); i ++) {
            Color color = colors.get(i);
            ChooseItem c = colorModel.colorToChoose(color);
            colorDropdown.getItems().add(c);

            if(color.getID() == luggage.color.getID()) {
                currentColorPlace = i;
            }
        }
        colorDropdown.getSelectionModel().select(currentColorPlace);

        // Get the photos
        currentPictures = imageModel.getPicturesFromLuggage(luggage.getID());
        for(Picture pic : currentPictures) {
            double width = imageScrollpane.getPrefWidth() - 50;
            PictureView pictureView = new PictureView(pic.getPath(), width, 0, true);
            pictureView.setOnDelete(this::pictureDeleteHandler);
            pictureView.setEditable(true);
            imageScrollContent.getChildren().add(pictureView);
        }

        cancelButton.setOnAction(this::cancelHandler);
        editButton.setOnAction(this::editHandler);
//        browseButton.setOnAction(this::browseHandler);
        browseButton.setDisable(true);
    }

    private void addImageToContent(File file) {
        double width = imageScrollpane.getPrefWidth() - 50;
        PictureView pictureView = new PictureView(file.toURI().toString(), width, 0, true);
        pictureView.setOnDelete(this::pictureDeleteHandler);
        pictureView.setEditable(true);
        imageScrollContent.getChildren().add(pictureView);
    }

    private void browseHandler(ActionEvent e) {
        FileChooser chooser = new FileChooser();

        // Configure the file chooser
        chooser.setTitle("Select image");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File file = chooser.showOpenDialog(view.getScene().getWindow());

        // TODO: error when it's not an image
        if(file != null) {
            // We set the preserverRatio to true, so we don't have to fill in a height
            addImageToContent(file);
            addedPictures.add(file);
        }
    }

    private void pictureDeleteHandler(Object object) {
        PictureView picture = (PictureView) object;

        // Loop trough the pictures
        for(Picture pic : currentPictures) {
            String path = pic.getPath();
            String imagePath = picture.getImagePath();
            if(path.equals(imagePath)) {
                deletedPictures.add(pic);
                currentPictures.remove(pic);
                break;
            }
        }

        imageScrollContent.getChildren().remove(picture);
    }

    private void editHandler(ActionEvent e) {
        
        ChooseItem airport = foundonAirportdropdown.getValue();
        ChooseItem color = colorDropdown.getValue();
        ChooseItem brand = brandInput.getValue();

        Luggage luggage = new Luggage(currentLuggage.getID());
        luggage.color = ColorModel.getDefault().getColor(color.getKey());
        luggage.status = StatusModel.getDefault().getStatus(1);

        // This won't work if no IMAGE_USER is logged in!
        luggage.employee = EmployeeModel.getDefault().currentEmployee;
        //luggage.employee = EmployeeModel.getDefault().getEmployee(0);

        luggage.customer = CustomerModel.getDefault().getCustomer(2);
        luggage.airport = airportModel.getAirport(airport.getKey());

        String[] dimensions = {
                widthInputfield.getText(),
                heightInputfield.getText(),
                depthInputfield.getText(),
                "cm"
        };

        luggage.setDimensions(dimensions);

        luggage.label = labelInputfield.getText();
        luggage.notes = notesInputfield.getText();
        luggage.weight = weightInputfield.getText();
        luggage.brand = new Brand(brand.getKey(), brand.toString());

        long currentTimeStamp = DateUtil.getCurrentTimeStamp();

        luggage.foundDate = currentTimeStamp;
        luggage.createDate = currentTimeStamp;
        luggage.returnDate = 0;

        // Loop trough the deleted pictures and delete those from the database
        for(Picture pic : deletedPictures) {
            imageModel.deleteImage(pic.getID());
        }

        currentLuggage = luggage;
        luggageModel.editLuggage(luggage);


//        Pane parent = (Pane)getView().getParent();
//        addController(new DetailLuggageController(luggage), parent);
        removeController(this);
    }

    private void cancelHandler(ActionEvent e) {
//        Pane parent = (Pane)getView().getParent();
//        addController(new DetailLuggageController(currentLuggage), parent);
        removeController(this);
    }

    @Override
    protected Object destroyReturn() {
        return currentLuggage;
    }
}
