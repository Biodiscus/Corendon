package nl.itopia.corendon.controller.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;

/**
 *
 * @author wieskueter.com
 */
public class EditEmployeeController extends Controller{
    @FXML private Button addButton, cancelButton;

    public EditEmployeeController(int userId) {
        this(EmployeeModel.getDefault().getEmployee(userId));
    }

    public EditEmployeeController(Employee employee) {
        // Set view
        registerFXML("gui/add_user.fxml");

        // TODO: Do something with the employee

        cancelButton.setOnAction(this::cancelHandler);
    }

    private void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
