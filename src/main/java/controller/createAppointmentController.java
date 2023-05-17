/**

 The createAppointmentController class is a controller class for the "create appointment" view.
 It implements the Initializable interface to provide an initialize() method for initialization logic.
 */

package controller;

import com.main.c195.main;
import dao.AppointmentsQuery;
import dao.ContactsQuery;
import dao.CustomersQuery;
import dao.UsersQuery;
import helper.AppValidCheck;
import helper.LoadTimes;
import helper.TimeFormatter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class createAppointmentController implements Initializable {

    Stage stage;
    /**
     * The text field for the title of the appointment.
     */
    @FXML
    private TextField titleField;
    /**
     * The text field for the description of the appointment.
     */
    @FXML
    private TextField descriptionField;
    /**
     * The text field for the location of the appointment.
     */
    @FXML
    private TextField locationField;
    /**
     * The text field for the type of the appointment.
     */
    @FXML
    private TextField typeField;
    /**
     * The date picker for the start date of the appointment.
     */
    @FXML
    private DatePicker fromDatePicker;
    /**
     * The combo box for the start time of the appointment.
     */
    @FXML
    private ComboBox startTimeComboBox;
    /**
     * The date picker for the end date of the appointment.
     */
    @FXML
    private DatePicker toDatePicker;
    /**
     * The combo box for the end time of the appointment.
     */
    @FXML
    private ComboBox endTimeComboBox;
    /**
     * The combo box for the customer of the appointment.
     */
    @FXML
    private ComboBox customerDropDown;
    /**
     * The combo box for the assigned user of the appointment.
     */
    @FXML
    private ComboBox assignUserDropDown;
    /**
     * The combo box for the contact information of the appointment.
     */
    @FXML
    private ComboBox contactInfoDropDown;
    /**
     * The text field for the ID of the appointment.
     */
    @FXML
    private TextField idField;

    /**
     * Creates a controller for appointment creation.
     *
     * @throws SQLException if there is an error with the SQL database
     */
    public createAppointmentController() throws SQLException {
    }

    HashMap<String, Integer> userIDs = new HashMap<String, Integer>();
    HashMap<String, Integer> customerIDs = new HashMap<String, Integer>();
    HashMap<String, Integer> contactIDs = new HashMap<String, Integer>();

    /**
     * Initializes the dropdown menus for users, customers, and contacts.
     *
     * @throws SQLException if there is an error with the SQL database
     */
    void dropDownInit() throws SQLException {
// get the user names and IDs and populate the combo box
        HashMap<Integer, String> userNames = UsersQuery.getUserNames();
        for (Map.Entry<Integer, String> entry : userNames.entrySet()) {
            int userID = entry.getKey();
            String userName = entry.getValue();
            String option = userName;
            userIDs.put(userName, userID);
            assignUserDropDown.getItems().add(option);
        }

        // get the customer names and IDs and populate the combo box
        HashMap<Integer, String> customerNames = CustomersQuery.getCustomerNames();
        for (Map.Entry<Integer, String> entry : customerNames.entrySet()) {
            int customerID = entry.getKey();
            String customerName = entry.getValue();
            String option = customerName;
            customerIDs.put(customerName, customerID);
            customerDropDown.getItems().add(option);
        }

        // get the contact emails and IDs and populate the combo box
        HashMap<Integer, String> contactEmails = ContactsQuery.getContactsByEmail();
        contactEmails.forEach((contactID, email) -> {
            String option = email;
            contactIDs.put(email, contactID);
            contactInfoDropDown.getItems().add(option);
        });

        LoadTimes.loadInTimesByHour(startTimeComboBox);
        LoadTimes.loadInTimesByHour(endTimeComboBox);

    }

    /**

     Handles creating a new appointment when the "Create" button is clicked. Inserts a new appointment into the database

     with the information provided by the user. If the new appointment overlaps with an existing one, a warning dialog box is

     displayed, and the appointment is not created.

     @param event The ActionEvent object that triggered this method.

     @throws IOException If there is an I/O error loading the appointment page FXML file.

     @throws SQLException If there is an error with the SQL query to insert the new appointment into the database.
     */
    @FXML
    void onCreateClick(ActionEvent event) throws IOException, SQLException {
        String titleText = titleField.getText();
        String descText = descriptionField.getText();
        String locText = locationField.getText();
        String typeText = typeField.getText();
        String fromTime = startTimeComboBox.getValue().toString();
        String toTime = endTimeComboBox.getValue().toString();
        Timestamp start = TimeFormatter.getTimeStamp(fromDatePicker.getValue(), fromTime, ZoneId.systemDefault());
        Timestamp end = TimeFormatter.getTimeStamp(toDatePicker.getValue(), toTime, ZoneId.systemDefault());
        Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
        String createdBy = "script";
        Timestamp last_update = Timestamp.valueOf(LocalDateTime.now());
        String updatedBy = "script";
        int customerID = customerIDs.get(customerDropDown.getValue());
        int userID = userIDs.get(assignUserDropDown.getValue());
        int contactID = contactIDs.get(contactInfoDropDown.getValue());

        ObservableList<String> listOfErrors = AppValidCheck.businessHoursErrorList(
                fromTime,
                fromDatePicker,
                toTime,
                toDatePicker
        );

        if (AppointmentsQuery.isOverlapping(start, end, customerID)){

            Alert alert = new Alert(Alert.AlertType.WARNING, "Your date and time frame conflict with existing appointments");
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }


        if(listOfErrors.isEmpty()){
            AppointmentsQuery.insert(
                    titleText,
                    descText,
                    locText,
                    typeText,
                    start,
                    end,
                    createDate,
                    createdBy,
                    last_update,
                    updatedBy,
                    customerID,
                    userID,
                    contactID
            );

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-appointment-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
        else{
            String errorMessage = String.join("\n", listOfErrors);

            Alert alert = new Alert(Alert.AlertType.WARNING, errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
        }



    }
    /**

     Handles cancelling the creation of a new appointment when the "Cancel" button is clicked.
     Returns the user to the appointment page.
     @param event The ActionEvent object that triggered this method.
     @throws IOException If there is an I/O error loading the appointment page FXML file.
     */
    @FXML
    void onCancelClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-appointment-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**

     Initializes the appointment creation form. Disables the ID field and sets its prompt text to "Auto-generated".
     Initializes the drop-down menus for the customers, users, and contact information.
     @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setDisable(true);
        idField.setPromptText("Auto-generated");
        try {
            dropDownInit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
