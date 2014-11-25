package nl.itopia.corendon.controller.administrator;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import nl.itopia.corendon.data.ChooseItem;
import nl.itopia.corendon.data.Role;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.model.RoleModel;
import nl.itopia.corendon.mvc.Controller;
import nl.itopia.corendon.utils.Hashing;
import nl.itopia.corendon.utils.Log;

/**
 * @author wieskueter.com
 */
public class CreateUserController extends Controller {

    @FXML
    private TextField usernameInputfield;
    @FXML
    private TextField nameInputfield;
    @FXML
    private TextField passwordInputfield;
    @FXML
    private TextField repeatpasswordInputfield;
    @FXML
    private TextField notesInputfield;
    @FXML
    private ChoiceBox<ChooseItem> roleDropdownmenu = new ChoiceBox<>();
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    private final List<Role> roleList;

    public CreateUserController() {
        // Set view
        registerFXML("gui/add_user.fxml");

        // Populate dropdownmenu with role values
        roleList = RoleModel.getDefault().getRoles();

        for (Role role : roleList) {
            roleDropdownmenu.getItems().add(new ChooseItem(role.id, role.name));
        }

        roleDropdownmenu.getSelectionModel().select(0);

        // Trigger button to create new employee
        addButton.setOnAction(this::createNewEmployee);
        cancelButton.setOnAction(this::cancelHandler);
    }

    public void createNewEmployee(ActionEvent event) {
        Log.display("Creating new employee...");
        EmployeeModel employeemodel = EmployeeModel.getDefault();
        String errors = "";

        String userName = usernameInputfield.getText();
        String name = nameInputfield.getText();
        String password = passwordInputfield.getText();
        String repeatPassword = repeatpasswordInputfield.getText();
        int userRole = roleDropdownmenu.getValue().getKey();
        //String employeeSince = employeeSinceField.getText();
        //String notes = notesInputfield.getText();

        //System.out.println("Selected role: "+ userRole);

        password = Hashing.sha256(password);
        repeatPassword = Hashing.sha256(repeatPassword);

        // Check if username already exists
        //employeemodel.UsernameExists(String username);

        // Check if passwords match
        if (!password.equals(repeatPassword)) {
            Log.display("Passwords doesn't match");
        }

        if (errors.equals("")) {
            System.out.println(password + " is equal to " + repeatPassword);

            /**
             * Check if username already exists
             * Generate random password
             */
            employeemodel.createEmployee(userName, password, userRole);
            //removeController(this);
        }
    }

    private void cancelHandler(ActionEvent e) {
        removeController(this);
    }
}
