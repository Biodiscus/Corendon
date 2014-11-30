package nl.itopia.corendon.controller.employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import nl.itopia.corendon.components.AutoCompleteComboBoxListener;
import nl.itopia.corendon.data.*;
import nl.itopia.corendon.model.*;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.IO;
import nl.itopia.corendon.utils.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * © 2014, Biodiscus.net Robin
 */
public class AddLuggageController extends Controller {
    @FXML private Button addButton, cancelButton, browseButton;
    // TODO: brandInputfield should have it's own component!
    @FXML private TextField labelInputfield, fileInputfield, heightInputfield, weightInputfield,
                            notesInputfield, widthInputfield, depthInputfield;
    @FXML private ComboBox<ChooseItem> brandInput;
    @FXML private ChoiceBox<ChooseItem> foundonAirportdropdown, colorDropdown;
    @FXML private ImageView pictureFrame1, pictureFrame2, pictureFrame3, pictureFrame4;


    private LuggageModel luggageModel;
    private AirportModel airportModel;
    private ColorModel colorModel;
    private BrandModel brandModel;

    private Luggage currentLuggage;

    private AutoCompleteComboBoxListener<ChooseItem> comboBoxListener;


    public AddLuggageController() {
        registerFXML("gui/add_luggage.fxml");

        luggageModel = LuggageModel.getDefault();
        airportModel = AirportModel.getDefault();
        colorModel = ColorModel.getDefault();
        brandModel = BrandModel.getDefault();


        // Set the Airports in the foundonAirportdropdown
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

//        addButton.setOnAction(this::addHandler);
        cancelButton.setOnAction(this::cancelHandler);
        browseButton.setOnAction(this::browseHandler);
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
            Image image = new Image(file.toURI().toString());

            if(pictureFrame1.getImage() == null) {
                pictureFrame1.setImage(image);
            } else if(pictureFrame2.getImage() == null) {
                pictureFrame2.setImage(image);
            } else if(pictureFrame3.getImage() == null) {
                pictureFrame3.setImage(image);
            } else if(pictureFrame4.getImage() == null) {
                pictureFrame4.setImage(image);
            }
        }
    }

    private void addHandler(ActionEvent e) {
        // TODO: Should we reference the Color or Airport in the ChooseItem?
        ChooseItem airport = foundonAirportdropdown.getValue();
        ChooseItem color = colorDropdown.getValue();
        ChooseItem brand = brandInput.getValue();


        Luggage luggage = new Luggage();
        luggage.color = ColorModel.getDefault().getColor(color.getKey());
        luggage.status = StatusModel.getDefault().getStatus(1);
        luggage.employee = EmployeeModel.getDefault().currentEmployee;
        luggage.customer = CustomerModel.getDefault().getCustomer(2);
        luggage.airport = airportModel.getAirport(airport.getKey());
        luggage.brand = new Brand(brand.getKey(), brand.toString());

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
        luggage.brand = BrandModel.getDefault().getBrand(1);

        long currentTimeStamp = DateModel.getDefault().getCurrentTimeStamp();

        luggage.foundDate = currentTimeStamp;
        luggage.createDate = currentTimeStamp;
        luggage.returnDate = 0;

        currentLuggage = luggage;
        luggageModel.addLuggage(luggage);
        removeController(this);
    }

    @Override
    protected Object destroyReturn() {
        return currentLuggage;
    }

    private void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
