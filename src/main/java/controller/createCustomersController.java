/**

 This class is the controller for the "Create Customer" page in the application. It implements the Initializable interface
 to initialize the stateComboBox with the first level divisions.
 */

package controller;

import com.main.c195.main;
import dao.CustomersQuery;
import dao.FirstLevelDivisionQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.ResourceBundle;

public class createCustomersController implements Initializable {
    /**
     * The stage for the "Create Customer" page
     */
    Stage stage;

    /**
     * The TextField for the customer name
     */
    @FXML
    private TextField customerName;

    /**
     * The TextField for the customer address
     */
    @FXML
    private TextField customerAddress;

    /**
     * The TextField for the customer postal code
     */
    @FXML
    private TextField customerPostalCode;

    /**
     * The TextField for the customer phone number
     */
    @FXML
    private TextField customerPhone;

    /**
     * The ComboBox for the state options. It will be populated with the first level divisions.
     */
    @FXML
    private ComboBox <String> stateComboBox;

    @FXML
    private TextField idField;
    HashMap<String, Integer> divisions;


    /**
     * Initializes the state combo box with division names.
     *
     * @throws SQLException if a database access error occurs.
     */
    void stateComboBoxInit() throws SQLException {
        divisions = FirstLevelDivisionQuery.selectAndReturnHash();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        divisionNames.addAll(divisions.keySet());

        stateComboBox.setItems(divisionNames.sorted());
    }
    /**
     * Handles the creation of a new customer.
     *
     * @param event the action event triggered by the user.
     * @throws IOException if an I/O error occurs.
     * @throws SQLException if a database access error occurs.
     */
    @FXML
    void onCreateClick(ActionEvent event) throws IOException, SQLException {
        String name = customerName.getText();
        String address = customerAddress.getText();
        String postal_code = customerPostalCode.getText();
        String phone = customerPhone.getText();
        int division_id = divisions.get(stateComboBox.getValue());

        CustomersQuery.insert(
                name,
                address,
                postal_code,
                phone,
                LocalDate.now(),
                "script",
                Timestamp.valueOf(LocalDateTime.now()),
                "script",
                division_id
        );

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-customer-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Handles the creation of a new customer.
     *
     * @param event the action event triggered by the user.
     * @throws IOException if an I/O error occurs.
     * @throws SQLException if a database access error occurs.
     */
    @FXML
    void onCancelClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-customer-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method initializes the stateComboBox with the first level divisions in the database.
     * It is called when the "Create Customer" page is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setDisable(true);
        idField.setPromptText("Auto-generated");
        try {
            stateComboBoxInit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
