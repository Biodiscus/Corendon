package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.itopia.corendon.Launcher;
import nl.itopia.corendon.controller.employee.DetailLuggageController;
import nl.itopia.corendon.data.*;
import nl.itopia.corendon.data.table.TableUser;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.model.LuggageModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.mvc.View;
import nl.itopia.corendon.utils.Log;
import nl.itopia.corendon.view.MainView;

/**
 * © 2014, Biodiscus.net Robin
 */
public class MainController extends Controller {
//    @FXML public Button test_button;
    private MainView mainView;

    public MainController() {
        this(Launcher.WIDTH, Launcher.HEIGHT);
    }

    public MainController(int width, int height) {
        mainView = new MainView();
        mainView.data.add(new TableUser("Test", "Ing", "06-55556666"));
//        registerFXML("gui/TestGUI.fxml");

//        addController(new DetailLuggageController(3));

//        EmployeeModel model = EmployeeModel.getDefault();
//        model.getEmployee(0);
//
//        LuggageModel luggageModel = LuggageModel.getDefault();
//
//        Luggage luggage = new Luggage(3);
//        luggage.color = new Color(1, "#fff");
//        luggage.status = new Status(1, "NOPE");
//        luggage.employee = new Employee(0);
//        luggage.customer = new Customer(2);
//        luggage.airport = new Airport(1, 1, "Ver weggistan");
//        luggage.dimensions = "20x20x10 cm";
////        luggage.label = "2222-3333-4444-5555";
//        luggage.label = "4444-3333-4444-5555";
//        luggage.notes = "Geen note";
//        luggage.weight = "200KG";
//        luggage.brand = new Brand(1, "Brnad");
//        luggage.foundDate = 1416508718;
//        luggage.createDate = 1416508718;
//        luggage.returnDate = 0;
//        luggageModel.addLuggage(luggage);
//        luggageModel.editLuggage(luggage);

//        Luggage luggage = luggageModel.getLuggage(3);
//        Log.display(luggage, luggage.color, luggage.employee);

//        test_button.setOnAction(this::handle);
    }

    public void handle(ActionEvent e) {
        Log.display("Button clicked", e);
    }

    @Override
    public View getView() {
        return mainView;
    }
}
