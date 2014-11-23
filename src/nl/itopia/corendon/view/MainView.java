package nl.itopia.corendon.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.itopia.corendon.data.table.TableUser;
import nl.itopia.corendon.mvc.View;

/**
 * © 2014, Biodiscus.net Robin
 */
public class MainView extends View {

    public TableView table;

    public final ObservableList<TableUser> data;

    public MainView() {
        setWidth(1000);
        setHeight(1000);

        table = new TableView();
        table.setPrefWidth(500);

        TableColumn firstName = new TableColumn("First name");
        TableColumn lastName = new TableColumn("Last name");
        TableColumn telephoneNumber = new TableColumn("Telephone number");

        table.getColumns().addAll(firstName, lastName, telephoneNumber);

        // The fields in the new PropertyValueFactory, needs to be the same as the variable you want to target
        firstName.setCellValueFactory(new PropertyValueFactory<TableUser, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<TableUser, String>("lastName"));
        telephoneNumber.setCellValueFactory(new PropertyValueFactory<TableUser, String>("telephoneNumber"));

        // Put some data in the table
        data = FXCollections.observableArrayList(
                new TableUser("Wies", "Kueter", "06-12345678"),
                new TableUser("Robin", "de Jong", "06-12345678"),
                new TableUser("Jeroen", "Groot", "06-12345678"),
                new TableUser("Stefan", "de Groot", "06-12345678"),
                new TableUser("Erik", "Schouten", "06-12345678"),
                new TableUser("Igor", "Willems", "06-12345678")
        );

        table.setItems(data);


        getChildren().add(table);
    }
}
