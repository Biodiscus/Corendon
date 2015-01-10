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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import nl.itopia.corendon.controller.CustomerController;


/**
 * © 2014, Biodiscus.net Robin
 */
public class AddLuggageController extends Controller {
    
    @FXML private Button addButton, cancelButton, browseButton;
    @FXML private TextField labelInputfield, fileInputfield, heightInputfield, weightInputfield,
                            notesInputfield, widthInputfield, depthInputfield;
    @FXML private ComboBox<ChooseItem> brandInput;
    @FXML private ChoiceBox<ChooseItem> foundonAirportdropdown, colorDropdown, lostOrFounddropdown;
    @FXML private ScrollPane imageScrollpane;

    private VBox imageScrollContent;

    private EmployeeModel employeeModel;
    private LuggageModel luggageModel;
    private AirportModel airportModel;
    private ColorModel colorModel;
    private BrandModel brandModel;
    private ImageModel imageModel;
    private StatusModel statusModel;
    
    private Luggage currentLuggage;

    private AutoCompleteComboBoxListener<ChooseItem> comboBoxListener;
    private List<File> imagesToUpload;
    boolean labelExists = false;

    public AddLuggageController() {
        
        // Set view
        registerFXML("gui/add_luggage.fxml");

        employeeModel = EmployeeModel.getDefault();
        luggageModel = LuggageModel.getDefault();
        airportModel = AirportModel.getDefault();
        colorModel = ColorModel.getDefault();
        brandModel = BrandModel.getDefault();
        imageModel = ImageModel.getDefault();
        statusModel = StatusModel.getDefault();
        
        imagesToUpload = new ArrayList<>();

        List<Airport> airports = airportModel.getAirports();
        for(Airport airport : airports) {
            ChooseItem c = airportModel.airportToChoose(airport);
            foundonAirportdropdown.getItems().add(c);
        }
        foundonAirportdropdown.getSelectionModel().selectFirst();

        // Set the Colors in the colorDropdown
        List<Color> colors = colorModel.getColors();
        for(Color color : colors) {
            ChooseItem c = colorModel.colorToChoose(color);
            colorDropdown.getItems().add(c);
        }
        colorDropdown.getSelectionModel().selectFirst();

        // Fill the brand input with the brands in the system
        List<Brand> brands = brandModel.getBrands();
        ObservableList<ChooseItem> brandData = FXCollections.observableArrayList();

        for(Brand brand : brands) {
            ChooseItem c = brandModel.brandToChoose(brand);
            brandData.add(c);
        }
        brandInput.setItems(brandData);
        
        /* status */
        List<Status> statuses = statusModel.getFoundLost();
        ObservableList<ChooseItem> statusData = FXCollections.observableArrayList();

        for(Status status : statuses) {
            ChooseItem c = statusModel.statusToChoose(status);
            statusData.add(c);
        }
        lostOrFounddropdown.setItems(statusData);
        lostOrFounddropdown.getSelectionModel().selectFirst();
        
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

                // If the item doesn't exist, create a new one with it's id set to 0
                if(item == null) {
                    item = new ChooseItem(-1, string);
                }

                return item;
            }
        });

        // Give the brand input our combobox listener
        comboBoxListener = new AutoCompleteComboBoxListener(brandInput);


        // Set the imageScrollpane content
        imageScrollContent = new VBox();
        imageScrollContent.setSpacing(10);
        imageScrollpane.setContent(imageScrollContent);

        addButton.setOnAction(this::addHandler);
        cancelButton.setOnAction(this::cancelHandler);
        browseButton.setOnAction(this::browseHandler);
        labelInputfield.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)->{
            labelHandler();
        });
    }

    /**
     * @return void
     * 
     * Handle label actions
     */
    private void labelHandler() {
        String labelNr = labelInputfield.getText();
        
        if(null != labelNr && !labelNr.isEmpty()) {
            labelExists = luggageModel.labelExists(labelNr);            
        }
        
        if(labelExists) {
            lostOrFounddropdown.getSelectionModel().select(1);
            deactivateFields();
        }
    }
    
    /**
     * Disable all input fields
     */
    private void deactivateFields() {
        fileInputfield.setDisable(true);
        brandInput.setDisable(true);
        heightInputfield.setDisable(true);
        widthInputfield.setDisable(true);
        depthInputfield.setDisable(true);
        weightInputfield.setDisable(true);
        notesInputfield.setDisable(true);
        colorDropdown.setDisable(true);
        browseButton.setDisable(true);
    }
    
    /**
     * Handler for file upload
     * 
     * @param e 
     */
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
            double width = imageScrollpane.getWidth() - 50;
            PictureView pictureView = new PictureView(file.toURI().toString(), width, 0, true);
            pictureView.setOnDelete(this::pictureDeleteHandler);
            pictureView.setEditable(true);
            imageScrollContent.getChildren().add(pictureView);
            imagesToUpload.add(file);
        }
    }

    private void pictureDeleteHandler(Object object) {
        PictureView picture = (PictureView) object;

        // Loop to our current images
        // Get our the correct file and delete it so the program won't upload it
        for(int i = 0; i < imagesToUpload.size(); i ++) {
            File file = imagesToUpload.get(i);
            String path = file.toURI().toString();
            String imagePath = picture.getImagePath();
            if(imagePath.equals(path)) {
                imagesToUpload.remove(i);
                break;
            }
        }

        // Remove the pictureview from the content pane
        imageScrollContent.getChildren().remove(picture);
    }

    /**
     * Add luggage to database
     * 
     * @param e ActionEvent
     */
    private void addHandler(ActionEvent e) {
        
        if(labelExists) {
            /* label exists, show customer controller to fill the rest in */
            String label  = labelInputfield.getText();
            ChooseItem airport = foundonAirportdropdown.getValue();
            Luggage luggage = luggageModel.getLuggageByLabel(label);
            /* attach new airport to the luggage */
            luggage.airport = airportModel.getAirport(airport.getKey());
            
            /* update luggage for the newly added airport */
            luggageModel.editLuggage(luggage);
            CustomerController custController = new CustomerController(luggage);
            addController(custController);
            
            custController.setControllerDeleteHandler((o) -> {
                removeController(this);
            });
            
        } else {
            ChooseItem airport = foundonAirportdropdown.getValue();
            ChooseItem color = colorDropdown.getValue();
            ChooseItem brand = brandInput.getValue();


            Luggage luggage = new Luggage();
            luggage.color = ColorModel.getDefault().getColor(color.getKey());
            luggage.status = StatusModel.getDefault().getStatus(1);
            luggage.employee = employeeModel.currentEmployee;
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
            luggage.brand = brandModel.handleBrandInput(brand.toString());

            long currentTimeStamp = DateUtil.getCurrentTimeStamp();

            luggage.foundDate = currentTimeStamp;
            luggage.createDate = currentTimeStamp;
            luggage.returnDate = 0;

            luggageModel.addLuggage(luggage);
            currentLuggage = luggage;
            removeController(this);

            // Upload the images
            for(File img : imagesToUpload) {
                try {
                    String path = imageModel.uploadImage(img);
                    imageModel.insertImage(path, luggage.getID());
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }
            }
        }
    }

    @Override
    protected Object destroyReturn() {
        return currentLuggage;
    }

    private void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
