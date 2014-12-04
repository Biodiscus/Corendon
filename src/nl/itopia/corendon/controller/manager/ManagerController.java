package nl.itopia.corendon.controller.manager;

import java.io.File;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import nl.itopia.corendon.controller.LoginController;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.data.manager.ChartData;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.DateUtil;
import java.time.LocalDate;
import java.util.*;
import javafx.stage.FileChooser;
import static nl.itopia.corendon.pdf.ManagerStatisticsPDF.generateManagerReportPDF;

/**
 * © 2014, Biodiscus.net robin
 */
public class ManagerController extends Controller {
    @FXML private LineChart lineDiagram;

    @FXML private Button filterButton, helpButton;
    @FXML private Button logoutButton;
    @FXML private Button printstatisticsButton;
    @FXML private CheckBox foundLuggagecheckbox, lostLuggagecheckbox, resolvedLuggagecheckbox;
    @FXML private DatePicker datepicker1, datepicker2;

    private LuggageModel luggageModel;

    private ImageView spinningIcon;
    private StackPane iconPane;

    private XYChart.Series<String, Integer> foundSeries, lostSeries, resolvedSeries;

    public ManagerController() {
        registerFXML("gui/manager_linediagram.fxml");

        luggageModel = LuggageModel.getDefault();

        foundLuggagecheckbox.setOnAction(this::filterHandle);
        lostLuggagecheckbox.setOnAction(this::filterHandle);
        resolvedLuggagecheckbox.setOnAction(this::filterHandle);
        logoutButton.setOnAction(this::logoutHandler);
        helpButton.setOnAction(this::helpHandler);
        printstatisticsButton.setOnAction(this::printStatisticsHandler);

        // TODO: Set the datePicker1 to something else
        datepicker1.setValue(LocalDate.of(1970, 1, 1));
        // Set the datePicker2 to today date
        datepicker2.setValue(LocalDate.now());


        // Show a spinning icon to indicate to the user that we are getting the tableData
        Image image = new Image("img/loader.gif", 24, 16.5, true, false);
        spinningIcon = new ImageView(image);

        iconPane = new StackPane();
        iconPane.getChildren().add(spinningIcon);
        view.fxmlPane.getChildren().add(iconPane);

        // Initialize the Series, give them a name and give them a color
        foundSeries = new XYChart.Series<>();
        foundSeries.setName("Found");

        lostSeries = new XYChart.Series<>();
        lostSeries.setName("Lost");

        resolvedSeries = new XYChart.Series<>();
        resolvedSeries.setName("Resolved");

        // Make a new thread that will recieve the tableData from the database
        Thread dataThread = new Thread(()->recieveData());
        dataThread.start();
    }

    private void filterHandle(ActionEvent e) {
        boolean found = foundLuggagecheckbox.isSelected();
        boolean lost = lostLuggagecheckbox.isSelected();
        boolean resolved = resolvedLuggagecheckbox.isSelected();
        showLuggage(found, lost, resolved);
    }

    private void showLuggage(boolean found, boolean lost, boolean resolved) {
        ObservableList<XYChart.Series> series = lineDiagram.getData();
        if(found) {
            // If the series is not in the chart, add it
            if(!series.contains(foundSeries)) {
                series.add(foundSeries);
            }
        } else {
            lineDiagram.getData().remove(foundSeries);
        }

        if(lost) {
            // If the series is not in the chart, add it
            if(!series.contains(lostSeries)) {
                series.add(lostSeries);
            }
        } else {
            lineDiagram.getData().remove(lostSeries);
        }

        if(resolved) {
            // If the series is not in the chart, add it
            if(!series.contains(resolvedSeries)) {
                series.add(resolvedSeries);
            }
        } else {
            lineDiagram.getData().remove(resolvedSeries);
        }
    }


    // We will call this function in a new thread, so the user can still click buttons
    private void recieveData() {
        List<ChartData> dates = new ArrayList<>();

        List<Luggage> luggages = luggageModel.getAllLuggage();
        for(Luggage luggage : luggages) {
            long uxt = luggage.createDate;
            ChartData contains = null;
            for(ChartData d : dates) {
                String date1 = DateUtil.getTimestampDate(uxt);
                String date2 = DateUtil.getTimestampDate(d.timestamp);
                if(date1.equals(date2)) {
                    contains = d;
                    break;
                }
            }
            if(contains == null) {
                dates.add(new ChartData(uxt, 1));
            } else {
                contains.count ++;
            }
        }

        Collections.sort(dates, (o1, o2) -> {
            Date date1 = new Date(o1.timestamp);
            Date date2 = new Date(o2.timestamp);

            return date1.compareTo(date2);
        });

        for(ChartData data : dates) {
            String date = DateUtil.getTimestampDate(data.timestamp);
            XYChart.Data<String, Integer> pointData = new XYChart.Data<>(date, data.count);
            foundSeries.getData().add(pointData);
        }

        lostSeries.getData().add(new XYChart.Data<>("20.11.2014", 5));
        lostSeries.getData().add(new XYChart.Data<>("23.11.2014", 0));
        lostSeries.getData().add(new XYChart.Data<>("24.11.2014", 0));
        lostSeries.getData().add(new XYChart.Data<>("25.11.2014", 3));

        resolvedSeries.getData().add(new XYChart.Data<>("20.11.2014", 3));
        resolvedSeries.getData().add(new XYChart.Data<>("23.11.2014", 6));
        resolvedSeries.getData().add(new XYChart.Data<>("24.11.2014", 4));
        resolvedSeries.getData().add(new XYChart.Data<>("25.11.2014", 2));

        // Notify the javafx thread to run this next command
        Platform.runLater(()->{
            // Update the line diagram with our tableData
            lineDiagram.getData().addAll(foundSeries, lostSeries, resolvedSeries);

            // Remove the spinning icon
            view.fxmlPane.getChildren().remove(iconPane);
        });         
    }
    
    private void helpHandler(ActionEvent e) {
        addController(new HelpFunctionControllerManager());
        //opens help function
    }

    private void logoutHandler(ActionEvent e) {
        changeController(new LoginController());
    }

    private void printStatisticsHandler(ActionEvent e){
        //SAVE FILE WITH FILECHOOSER
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select location to save PDF.");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        
        File pdf = fileChooser.showSaveDialog(view.getScene().getWindow());
        
        
        generateManagerReportPDF(pdf, lineDiagram);
        System.out.println("PDF OF MANAGER REPORT SAVED");         
    }
   

}
