package nl.itopia.corendon.controller.manager;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.data.manager.ChartData;
import nl.itopia.corendon.model.DateModel;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.Log;

import java.util.*;

/**
 * © 2014, Biodiscus.net robin
 */
public class ManagerController extends Controller {
    @FXML private LineChart lineDiagram;

    private LuggageModel luggageModel;
    private DateModel dateModel;

    public ManagerController() {
//        registerFXML("gui/manager_bardiagram.fxml");
        registerFXML("gui/manager_linediagram.fxml");

        luggageModel = LuggageModel.getDefault();
        dateModel = DateModel.getDefault();

        // TODO: Is this the way to do it?
//        Map<String, Integer> dates = new HashMap<>();

        List<ChartData> dates = new ArrayList<>();

        List<Luggage> luggages = luggageModel.getAllLuggage();
        for(Luggage luggage : luggages) {
            long uxt = luggage.createDate;

            ChartData contains = null;
            for(ChartData d : dates) {
                // TODO: We're only sorting on date right now :(

                String date1 = dateModel.getTimestampDate(uxt);
                String date2 = dateModel.getTimestampDate(d.timestamp);

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


        XYChart.Series series = new XYChart.Series<>();
        series.setName("Test 1");
        for(ChartData data : dates) {
            String date = dateModel.getTimestampDate(data.timestamp);
            XYChart.Data pointData = new XYChart.Data(date, data.count);
            series.getData().add(pointData);
        }

        XYChart.Series series2 = new XYChart.Series<>();
        series2.setName("Test 2");
        series2.getData().add(new XYChart.Data("2014.11.20", 5));
        series2.getData().add(new XYChart.Data("2014.11.23", 1));
        series2.getData().add(new XYChart.Data("2014.11.24", 1));
        series2.getData().add(new XYChart.Data("2014.11.25", 3));

        lineDiagram.getData().addAll(series, series2);
    }
}
