package controller;

import com.main.c195.main;
import dao.*;
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
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class editCustomersController implements Initializable {
    /**
     * The stage used for displaying the customer form.
     */
    Stage stage;

    /**
     * The text field for the customer's name.
     */
    @FXML
    private TextField customerName;

    /**
     * The text field for the customer's address.
     */
    @FXML
    private TextField customerAddress;

    /**
     * The text field for the customer's postal code.
     */
    @FXML
    private TextField customerPostalCode;

    /**
     * The text field for the customer's phone number.
     */
    @FXML
    private TextField customerPhone;

    /**
     * The combo box for selecting the customer's state.
     */
    @FXML
    private ComboBox<String> stateComboBox;

    /**
     * The combo box for selecting the customer's country.
     */
    @FXML
    private ComboBox<String> countryComboBox;

    /**
     * The text field for displaying the customer ID (auto-generated).
     */
    @FXML
    private TextField idField;

    /**
     * A map of division names to division IDs.
     */
    HashMap<String, Integer> divisions;

    /**
     * A map of country names to country IDs.
     */
    HashMap<String, Integer> countries;

    /**
     * The current customer being edited or viewed.
     */
    public Customers currentCustomer;

    /**
     * Returns the name of the division with the specified ID.
     *
     * @param division_id the ID of the division to find the name of
     * @return the name of the division with the specified ID, or {@code null} if no such division is found
     */
    String findDivisionName(int division_id){
        for (Map.Entry<String, Integer> entry : divisions.entrySet()) {
            if (entry.getValue().equals(division_id)) {
                return entry.getKey();
            }
        }
        return null; // or throw an exception, if desired
    }

    /**
     * Initializes the state combo box with data obtained from the database.
     * Populates the combo box with the names of the first-level divisions (states or provinces) in the database,
     * and sets up the hash map for division ID lookup.
     *
     * @throws SQLException if there is an error executing the SQL query to retrieve the division data from the database
     */

    void stateComboBoxInit() throws SQLException {
        divisions = FirstLevelDivisionQuery.selectAndReturnHash();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        divisionNames.addAll(divisions.keySet());
        stateComboBox.setItems(divisionNames.sorted());
    }

    /**
     * Initializes the country combo box by populating it with country names from the database.
     * @throws SQLException if there is an error accessing the database
     */
    void countryComboBoxInit() throws SQLException{
        countries = CountryQuery.select();
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        countryNames.addAll(countries.keySet());
        countryComboBox.setItems(countryNames);
    }

    /**

     Handles the event when the submit button is clicked.

     Updates the current customer information with the values input by the user

     and updates the database. Then redirects to the view customer page.

     @param event An ActionEvent that is triggered when the submit button is clicked.

     @throws IOException If an I/O error occurs while redirecting to the view customer page.

     @throws SQLException If a SQL error occurs while updating the database.
     */
    @FXML
    void onSubmitClick(ActionEvent event) throws IOException, SQLException {
        String name = customerName.getText();
        String address = customerAddress.getText();
        String postal_code = customerPostalCode.getText();
        String phone = customerPhone.getText();
        int division_id = divisions.get(stateComboBox.getValue());

        CustomersQuery.update(
                currentCustomer.getCustomer_id(),
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
     * Handles the action when the cancel button is clicked.
     * Returns the user to the view customer page.
     *
     * @param event the action event that triggered the method call
     * @throws IOException if an error occurs while loading the FXML file
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
     * This method is called when the value of the countryComboBox changes.
     * It queries the database for the divisions in the selected country and updates the options in the stateComboBox accordingly.
     * @param event the ActionEvent triggered by the change in the countryComboBox value
     * @throws SQLException if there is an error in the SQL query
     */
    @FXML
    void onCountryComboBoxChange(ActionEvent event) throws SQLException {
        divisions = FirstLevelDivisionQuery.selectAndReturnHash();
        ObservableList<FirstLevelDivisions> divisionList = FirstLevelDivisionQuery.selectAll();

        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for(FirstLevelDivisions division : divisionList){
            if (division.getCountry_id() == countries.get(countryComboBox.getValue())){
                divisionNames.add(division.getDivision());
            }
        }
        stateComboBox.setItems(divisionNames.sorted());
    }

    /**

     Populates the fields in the "Edit Customer" form with the information from the provided {@link Customers} object.

     @param customer the customer object containing the information to be displayed in the form

     @throws SQLException if there is an error while retrieving data from the database
     */
    public void sendCustomer(Customers customer) throws SQLException {

        currentCustomer = customer;
        customerName.setText(customer.getCustomer_name());
        customerAddress.setText(customer.getAddress());
        customerPhone.setText(customer.getPhone());
        customerPostalCode.setText(customer.getPostal_code());
        stateComboBox.setValue(findDivisionName(customer.getDivision_id()));

    }

    /**
     * Initializes the controller class.
     * Sets the "idField" TextField to be disabled with prompt text "Auto-generated".
     * Initializes the state and country ComboBoxes by populating them with data from the database.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * @throws RuntimeException if there is a SQL exception while initializing the state and country ComboBoxes.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setDisable(true);
        idField.setPromptText("Auto-generated");
        try {
            stateComboBoxInit();
            countryComboBoxInit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
