package nl.itopia.corendon.controller.manager;

import java.io.File;

import com.sun.javafx.tk.Toolkit;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import nl.itopia.corendon.Config;
import nl.itopia.corendon.controller.HelpFunctionController;
import nl.itopia.corendon.controller.LoginController;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.data.Status;
import nl.itopia.corendon.data.manager.ChartData;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.model.StatusModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.pdf.ManagerStatisticsPDF;
import nl.itopia.corendon.utils.DateUtil;

import java.time.LocalDate;
import java.util.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.utils.Log;

import javax.swing.Timer;
import nl.itopia.corendon.controller.ChangePasswordController;

/**
 * © 2014, Biodiscus.net robin
 */
public class ManagerController extends Controller {
    @FXML private LineChart lineDiagram;
    @FXML private BarChart barDiagram;

    @FXML private Button filterButton, helpButton, logoutButton, printstatisticsButton, lineDiagrambutton, barDiagrambutton,logfilesbutton, changePasswordButton, refreshButton;
    @FXML private CheckBox foundLuggagecheckbox, lostLuggagecheckbox, resolvedLuggagecheckbox;
    @FXML private DatePicker datepicker1, datepicker2;
    @FXML private Label userName, userIDLoggedInPerson;
    private LuggageModel luggageModel;

    private ImageView spinningIcon;
    private StackPane iconPane;

    private Chart currentChart;
    private XYChart.Series<Date, Integer> foundSeries, lostSeries, resolvedSeries;
    private XYChart.Series<String, Integer> foundBarSeries, lostBarSeries, resolvedBarSeries;
    private HelpFunctionController helpController;
    private final Timer timer;

    public ManagerController() {
        // Set view
        registerFXML("gui/manager_linediagram.fxml");

//        lineDiagram.setAnimated(false);
//        lineDiagram.setCreateSymbols(true);
//
//        barDiagram.setAnimated(false);

        EmployeeModel employeeModel = EmployeeModel.getDefault();
        userIDLoggedInPerson.setText(""+employeeModel.currentEmployee.id);
        userName.setText(employeeModel.currentEmployee.firstName + " " + employeeModel.currentEmployee.lastName);

        luggageModel = LuggageModel.getDefault();

        foundLuggagecheckbox.setOnAction(this::filterHandle);
        lostLuggagecheckbox.setOnAction(this::filterHandle);
        resolvedLuggagecheckbox.setOnAction(this::filterHandle);
        logoutButton.setOnAction(this::logoutHandler);
        helpButton.setOnAction(this::helpHandler);

        printstatisticsButton.setOnAction(this::printStatisticsHandler);
        lineDiagrambutton.setOnAction(this::lineDiagramHandler);
        barDiagrambutton.setOnAction(this::barDiagramHandler);
        view.fxmlPane.setOnKeyReleased(this::f1HelpFunction);
        changePasswordButton.setOnAction(this::changePassword);
        refreshButton.setOnAction(this::refreshHandler);

        currentChart = lineDiagram;
        
        // TODO: Set the datePicker1 to something else
        datepicker1.setValue(LocalDate.of(1970, 1, 1));
        datepicker1.setOnAction(this::datePickerHandler);

        // Set the datePicker2 to today date
        datepicker2.setValue(LocalDate.now());
        datepicker2.setOnAction(this::datePickerHandler);

        // Show a spinning icon to indicate to the user that we are getting the tableData
        showLoadingIcon();

        // Initialize the Series, give them a name and give them a color
//        foundSeries = new XYChart.Series<>();
//        foundSeries.setName("Found");
//
//        foundBarSeries = new XYChart.Series<>();
//        foundBarSeries.setName("Found");
//
//        lostSeries = new XYChart.Series<>();
//        lostSeries.setName("Lost");
//
//        lostBarSeries = new XYChart.Series<>();
//        lostBarSeries.setName("Lost");
//
//        resolvedSeries = new XYChart.Series<>();
//        resolvedSeries.setName("Resolved");
//
//        resolvedBarSeries = new XYChart.Series<>();
//        resolvedBarSeries.setName("Resolved");

        // Create a timer with a certain interval, every time it ticks refresh the entire to receive new data
        timer = new Timer(Config.DATA_REFRESH_INTERVAL, (e) -> refreshHandler(null));
        timer.start();
        // Tell the stylesheet that there should be an image on the button
        refreshButton.setId("button_refresh");

        // Make a new thread that will recieve the tableData from the database
        Thread dataThread = new Thread(this::receiveData);
        dataThread.setDaemon(true); // If for some reason the program quits, let the threads get destroyed with the main thread
        dataThread.start();
    }

    // When the datepickers received a action event
    private void datePickerHandler(ActionEvent event) {
//        Log.display(event.getSource());
//        DatePicker source = (DatePicker)event.getSource();

        // Simulate the refresh button
        refreshHandler(null);
    }

    // Fired when lineDiagrambutton is clicked
    private void lineDiagramHandler(ActionEvent e) {
        lineDiagrambutton.setDisable(true);
        barDiagrambutton.setDisable(false);

        lineDiagram.setVisible(true);
        barDiagram.setVisible(false);
        currentChart = lineDiagram;
    }

    // Fired when barDiagrambutton is clicked
    private void barDiagramHandler(ActionEvent e) {
        lineDiagrambutton.setDisable(false);
        barDiagrambutton.setDisable(true);

        lineDiagram.setVisible(false);
        barDiagram.setVisible(true);
        currentChart = barDiagram;
    }

    private void filterHandle(ActionEvent e) {
        boolean found = foundLuggagecheckbox.isSelected();
        boolean lost = lostLuggagecheckbox.isSelected();
        boolean resolved = resolvedLuggagecheckbox.isSelected();

        showLuggage(found, lost, resolved);
    }

    private void showLuggage(boolean found, boolean lost, boolean resolved) {
        ObservableList<XYChart.Series> lineSeries = lineDiagram.getData();
        ObservableList<XYChart.Series> barSeries = barDiagram.getData();

        if (found) {
            // If the chartSeries is not in the chart, add it
            if (!lineSeries.contains(foundSeries)) {
                lineSeries.add(foundSeries);
            }
            if(!barSeries.contains(foundBarSeries)) {
                barSeries.add(foundBarSeries);
            }

        } else {
            lineDiagram.getData().remove(foundSeries);
            barDiagram.getData().remove(foundBarSeries);
        }

        if (lost) {
            // If the chartSeries is not in the chart, add it
            if (!lineSeries.contains(lostSeries)) {
                lineSeries.add(lostSeries);
            }

            if(!barSeries.contains(lostBarSeries)) {
                barSeries.add(lostBarSeries);
            }
        } else {
            lineDiagram.getData().remove(lostSeries);
            barDiagram.getData().remove(lostBarSeries);
        }

        if (resolved) {
            // If the chartSeries is not in the chart, add it
            if (!lineSeries.contains(resolvedSeries)) {
                lineSeries.add(resolvedSeries);
            }

            if(!barSeries.contains(resolvedBarSeries)) {
                barSeries.add(resolvedBarSeries);
            }
        } else {
            lineDiagram.getData().remove(resolvedSeries);
            barDiagram.getData().remove(resolvedBarSeries);
        }
    }

    private void receiveData() {
        // The Date object is in milliseconds, toEpochDay() is in seconds
        Date date1 = DateUtil.localDateToDate(datepicker1.getValue());
        Date date2 = DateUtil.localDateToDate(datepicker2.getValue());

        // The first date will stay on 0:00:00, the second date will be set to the end of the day 23:59:59
        date2 = DateUtil.getEndOfDTheDay(date2);
        receiveData(date1, date2);
    }

    /**
     * This function will be mosly called in a different thread, so the user can still interact with the scene
     * Supply a start and end date.
     * @param start
     * @param end
     */
    private void receiveData(Date start, Date end) {
        // Disable the input until we have added everything to the UI
        // We need to run this in the JavaFX thread
        Platform.runLater(()->{
            datepicker1.setDisable(true);
            datepicker2.setDisable(true);
            foundLuggagecheckbox.setDisable(true);
            lostLuggagecheckbox.setDisable(true);
            resolvedLuggagecheckbox.setDisable(true);
        });

        long startTimeStamp = DateUtil.dateToTimestamp(start);
        long endTimeStamp = DateUtil.dateToTimestamp(end);

        StatusModel status = StatusModel.getDefault();
        Status foundStatus = status.getStatus("Found");
        Status lostStatus = status.getStatus("Lost");
        Status resolvedStatus = status.getStatus("Resolved");

        List<ChartData> foundDates = new ArrayList<>();
        List<ChartData> lostDates = new ArrayList<>();
        List<ChartData> resolvedDates = new ArrayList<>();

        // If the date is not set, add it to the list.
        // If the date is set, add one to the count
        List<Luggage> luggages = luggageModel.getAllLuggage();

        Platform.runLater(()->{
            // Clear the old table data
            lineDiagram.getData().clear();
            barDiagram.getData().clear();
        });

        // There is a bug with the XYChart. Series where clearing sometimes doesn't work.
        // Because this is sometimes, it can't be trusted. So create a new series
        // Initialize the Series, give them a name and give them a color
        foundSeries = new XYChart.Series<>();
        foundSeries.setName("Found");

        foundBarSeries = new XYChart.Series<>();
        foundBarSeries.setName("Found");

        lostSeries = new XYChart.Series<>();
        lostSeries.setName("Lost");

        lostBarSeries = new XYChart.Series<>();
        lostBarSeries.setName("Lost");

        resolvedSeries = new XYChart.Series<>();
        resolvedSeries.setName("Resolved");

        resolvedBarSeries = new XYChart.Series<>();
        resolvedBarSeries.setName("Resolved");

        for (Luggage luggage : luggages) {
            long uxt = luggage.createDate; // Unix time stamp
            // If the unix timestamp is outside of the given period
            // Continue to the next luggage
            if(!(uxt >= startTimeStamp && uxt <= endTimeStamp)) {
                continue;
            }

            String statusName = luggage.status.getName();
            ChartData contains = null;

            // If the luggage status is Found
            if(statusName.equals(foundStatus.getName())) {
                for (ChartData d : foundDates) {
                    // Format the unix timestamps
                    String date1 = DateUtil.formatDate("MMM yy", uxt);
                    String date2 = DateUtil.formatDate("MMM yy", d.timestamp);
                    // If the first date matches the second one, the date is already in our array
                    // Increment it with 1 later on
                    if (date1.equals(date2)) {
                        contains = d;
                        break;
                    }
                }
            }
            // If the luggage status is Lost
            if (statusName.equals(lostStatus.getName())) {
                for (ChartData d : lostDates) {
                    // Format the unix timestamps
                    String date1 = DateUtil.formatDate("MMM yy", uxt);
                    String date2 = DateUtil.formatDate("MMM yy", d.timestamp);
                    // If the first date matches the second one, the date is already in our array
                    // Increment it with 1 later on
                    if (date1.equals(date2)) {
                        contains = d;
                        break;
                    }
                }
            }

            // If the luggage status is Resolved
            if (statusName.equals(resolvedStatus.getName())) {
                for (ChartData d : resolvedDates) {
                    // Format the unix timestamps
                    String date1 = DateUtil.formatDate("MMM yy", uxt);
                    String date2 = DateUtil.formatDate("MMM yy", d.timestamp);
                    // If the first date matches the second one, the date is already in our array
                    // Increment it with 1 later on
                    if (date1.equals(date2)) {
                        contains = d;
                        break;
                    }
                }
            }

            if (contains == null) {

                // If we didn't find a match, create one
                if (foundStatus.getName().equals(statusName)) {
                    foundDates.add(new ChartData(uxt, 1));
                } else if (lostStatus.getName().equals(statusName)) {
                    lostDates.add(new ChartData(uxt, 1));
                } else if (resolvedStatus.getName().equals(statusName)) {
                    resolvedDates.add(new ChartData(uxt, 1));
                }
            } else {
                // We found a match, increment the count
                contains.count++;
            }
        }


        // The line diagram and bar diagram both need different data.
        // The line diagram works with a Date.
        // The bar diagram works with a String
        for (ChartData data : foundDates) {
            int count = data.count;
            // Create a new Date object from the unix timestamp
            // The Date object works with milliseconds, so we need to convert the unix timestamp to milliseconds
            Date date = DateUtil.timestampToDate(data.timestamp);
            XYChart.Data<Date, Integer> pointData = new XYChart.Data<>(date, count);

            // Format the date
            String dateFormat = DateUtil.formatDate("MMM yy", data.timestamp);
            XYChart.Data<String, Integer> barData = new XYChart.Data<>(dateFormat, count);

            // Be sure to use the javafx thread
            Platform.runLater(()->{
                foundSeries.getData().add(pointData); // Add it to an array created for Found luggage in the Line Diagram
                foundBarSeries.getData().add(barData); // Add it to an array created for Found luggage in the Bar Diagramar Diagram
            });
        }
        for (ChartData data : lostDates) {
            int count = data.count;
            // Create a new Date object from the unix timestamp
            // The Date object works with milliseconds, so we need to convert the unix timestamp to milliseconds
            Date date = DateUtil.timestampToDate(data.timestamp);
            XYChart.Data<Date, Integer> pointData = new XYChart.Data<>(date, count);

            // Format the date
            String dateFormat = DateUtil.formatDate("MMM yy", data.timestamp);
            XYChart.Data<String, Integer> barData = new XYChart.Data<>(dateFormat, count);

            // Be sure to use the javafx thread
            Platform.runLater(()->{
                lostSeries.getData().add(pointData); // Add it to an array created for Lost luggage in the Line Diagram
                lostBarSeries.getData().add(barData); // Add it to an array created for Lost luggage in the Bar Diagram
            });
        }
        for (ChartData data : resolvedDates) {
            int count = data.count;
            // Create a new Date object from the unix timestamp
            // The Date object works with milliseconds, so we need to convert the unix timestamp to milliseconds
            Date date = DateUtil.timestampToDate(data.timestamp);
            XYChart.Data<Date, Integer> pointData = new XYChart.Data<>(date, count);

            // Format the date
            String dateFormat = DateUtil.formatDate("MMM yy", data.timestamp);
            XYChart.Data<String, Integer> barData = new XYChart.Data<>(dateFormat, count);

            // Be sure to use the javafx thread
            Platform.runLater(()->{
                resolvedSeries.getData().add(pointData); // Add it to an array created for Resolved luggage in the Line Diagram
                resolvedBarSeries.getData().add(barData); // Add it to an array created for Resolved luggage in the Bar Diagram
            });
        }

        // Notify the javafx thread to run this next command
        Platform.runLater(() -> {
            // Enable every filter component
            datepicker1.setDisable(false);
            datepicker2.setDisable(false);
            foundLuggagecheckbox.setDisable(false);
            lostLuggagecheckbox.setDisable(false);
            resolvedLuggagecheckbox.setDisable(false);

            // Enable the button, remove the loading icon
            refreshButton.setDisable(false);
            refreshButton.setId("button_refresh");

            // Add the series to the diagram
            lineDiagram.getData().addAll(foundSeries, lostSeries, resolvedSeries);
            barDiagram.getData().addAll(foundBarSeries, lostBarSeries, resolvedBarSeries);

            // Remove the spinning icon
            view.fxmlPane.getChildren().remove(iconPane);
        });
    }

    private void f1HelpFunction(KeyEvent e) {
        //opens helpfunction with the f1 key
        if(e.getCode() == KeyCode.F1 && e.getEventType() == KeyEvent.KEY_RELEASED) {
            // If it's already openend, close it
            if(helpController == null) {
                openHelp();
            } else {
                removeController(helpController);
                helpController = null;
            }
        }
    }

    private void helpHandler(ActionEvent e) {
        if(helpController == null) {
            openHelp();
        }
        //opens help function
    }

    private void showLoadingIcon() {
        
        // Show a spinning icon to indicate to the IMAGE_USER that we are getting the tableData
        spinningIcon = new ImageView("img/loader.gif");

        iconPane = new StackPane();
        iconPane.setPickOnBounds(false); // Needed to click trough transparent panes
        iconPane.getChildren().add(spinningIcon);
        view.fxmlPane.getChildren().add(iconPane);
    }

    private void refreshHandler(ActionEvent e) {
        Platform.runLater(() -> {
            refreshButton.setDisable(true);
            refreshButton.setId("button_refresh_animate");
        });

        Thread dataThread = new Thread(this::receiveData);
        dataThread.setDaemon(true);
        dataThread.start();
    }
    
    private void changePassword(ActionEvent e) {
        
        addController(new ChangePasswordController());
    }

    private void openHelp() {
        helpController = new HelpFunctionController();
        helpController.setControllerDeleteHandler((obj)->{
            removeController(helpController);
            helpController=null;
        });
        addController(helpController);
    }

    private void logoutHandler(ActionEvent e) {
        changeController(new LoginController());
    }

    private void printStatisticsHandler(ActionEvent e) {
        //SAVE FILE WITH FILECHOOSER
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select location to save PDF.");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        File pdf = fileChooser.showSaveDialog(view.getScene().getWindow());


        if (pdf != null) {
            ManagerStatisticsPDF.generateManagerReportPDF(pdf, currentChart);
            System.out.println("PDF OF MANAGER REPORT SAVED");
        }
    }
}
