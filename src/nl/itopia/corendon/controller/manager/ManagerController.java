package nl.itopia.corendon.controller.manager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.model.DateModel;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.mvc.Controller;
import java.util.*;

/**
 * © 2014, Biodiscus.net robin
 */
public class ManagerController extends Controller {
    @FXML private LineChart lineDiagram;

    private LuggageModel luggageModel;
    private DateModel dateModel;

    private ImageView spinningIcon;
    private StackPane iconPane;

    public ManagerController() {
//        registerFXML("gui/manager_bardiagram.fxml");
        registerFXML("gui/manager_linediagram.fxml");

        luggageModel = LuggageModel.getDefault();
        dateModel = DateModel.getDefault();

        // Show a spinning icon to indicate to the user that we are getting the data
        Image image = new Image("img/loader.gif", 24, 16.5, true, false);
        spinningIcon = new ImageView(image);

        iconPane = new StackPane();
        iconPane.getChildren().add(spinningIcon);
        view.fxmlPane.getChildren().add(iconPane);

        // Make a new thread that will recieve the data from the database
        Thread dataThread = new Thread(()->recieveData());
        dataThread.start();
    }


    // We will call this function in a new thread, so the user can still click buttons
    private void recieveData() {
        List<Long> dates = new ArrayList<>();

        List<Luggage> luggages = luggageModel.getAllLuggage();
        for(Luggage luggage : luggages) {
            long uxt = luggage.createDate;
            dates.add(uxt);
        }

        Collections.sort(dates, (o1, o2) -> {
            Date date1 = new Date(o1);
            Date date2 = new Date(o2);

            return date1.compareTo(date2);
        });


        XYChart.Series series = new XYChart.Series<>();
        series.setName("Test 1");
        for(Long timestamp : dates) {
            String date = dateModel.getTimestampDate(timestamp);
            XYChart.Data pointData = new XYChart.Data(date, 1);
            series.getData().add(pointData);
        }

        XYChart.Series series2 = new XYChart.Series<>();
        series2.setName("Test 2");
        series2.getData().add(new XYChart.Data("20.11.2014", 5));
        series2.getData().add(new XYChart.Data("23.11.2014", 1));
        series2.getData().add(new XYChart.Data("24.11.2014", 1));
        series2.getData().add(new XYChart.Data("25.11.2014", 3));


        // Notify the javafx thread to run this next command
        Platform.runLater(()->{
            // Update the line diagram with our data
            lineDiagram.getData().addAll(series, series2);

            // Remove the spinning icon
            view.fxmlPane.getChildren().remove(iconPane);
        });
    }

}
