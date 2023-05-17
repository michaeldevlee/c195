/**

 Controller class for the edit appointment page.
 */
package controller;

import com.main.c195.main;
import dao.AppointmentsQuery;
import dao.ContactsQuery;
import dao.CustomersQuery;
import dao.UsersQuery;
import helper.LoadTimes;
import helper.TimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class editAppointmentController implements Initializable {

    Stage stage;
    /**
     * The text field for the appointment title.
     */
    @FXML
    private TextField titleField;

    /**
     * The text field for the appointment description.
     */
    @FXML
    private TextField descriptionField;

    /**
     * The text field for the appointment location.
     */
    @FXML
    private TextField locationField;

    /**
     * The text field for the appointment type.
     */
    @FXML
    private TextField typeField;

    /**
     * The date picker for the appointment start date.
     */
    @FXML
    private DatePicker fromDatePicker;

    /**
     * The combo box for the appointment start time.
     */
    @FXML
    private ComboBox startTimeComboBox;

    /**
     * The date picker for the appointment end date.
     */
    @FXML
    private DatePicker toDatePicker;

    /**
     * The combo box for the appointment end time.
     */
    @FXML
    private ComboBox endTimeComboBox;

    /**
     * The combo box for selecting the customer associated with the appointment.
     */
    @FXML
    private ComboBox customerDropDown;

    /**
     * The combo box for selecting the user assigned to the appointment.
     */
    @FXML
    private ComboBox assignUserDropDown;

    /**
     * The combo box for selecting the contact associated with the appointment.
     */
    @FXML
    private ComboBox contactInfoDropDown;

    /**
     * The text field for the appointment ID.
     */
    @FXML
    private TextField idField;

    /**
     * The current appointment being edited.
     */
    public Appointments currentAppointment;

    /**

     HashMap to hold the IDs of users.
     The keys are the names of the users and the values are their corresponding IDs.
     */
    HashMap<String, Integer> userIDs = new HashMap<String, Integer>();
    /**

     HashMap to hold the IDs of customers.
     The keys are the names of the customers and the values are their corresponding IDs.
     */
    HashMap<String, Integer> customerIDs = new HashMap<String, Integer>();
    /**

     HashMap to hold the IDs of contacts.
     The keys are the names of the contacts and the values are their corresponding IDs.
     */
    HashMap<String, Integer> contactIDs = new HashMap<String, Integer>();

    /**

     Initializes the drop-down menus with the appropriate values retrieved from the database.

     @throws SQLException if there is an error while accessing the database
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
        //The justification for using the lambda is that it allows for more readable
        // and maintainable code, and reduces the amount of boilerplate code needed for the iteration process.
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

     Handles the submit button click to update an appointment's details in the database and return to the main appointments page.

     @param event An ActionEvent representing the click event.

     @throws IOException if there is an error loading the FXML file for the main appointments page.

     @throws SQLException if there is an error updating the appointment details in the database.
     */
    @FXML
    void onSubmitClick(ActionEvent event) throws IOException, SQLException {

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

        AppointmentsQuery.update(
                currentAppointment.getAppointment_id(),
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
    /**

     This method handles the "Cancel" button click event and redirects the user back to the appointment view page.
     @param event The event object that triggers the method call.
     @throws IOException If an error occurs while loading the appointment view page FXML file.
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

     This method sets the values of the fields in the form to those of the given appointment and populates the dropdown menus
     with relevant options.
     @param appointment The appointment object whose data is to be displayed in the form.
     @throws SQLException if there is an error accessing the database.
     */
    public void sendAppointment(Appointments appointment) throws SQLException {
        currentAppointment = appointment;
        titleField.setText(appointment.getTitle());
        descriptionField.setText(appointment.getDescription());
        locationField.setText(appointment.getLocation());
        typeField.setText(appointment.getType());
        fromDatePicker.setValue(appointment.getStart().toLocalDateTime().toLocalDate());
        toDatePicker.setValue(appointment.getEnd().toLocalDateTime().toLocalDate());
        startTimeComboBox.setValue(appointment.getStart().toLocalDateTime().toLocalTime());
        endTimeComboBox.setValue(appointment.getEnd().toLocalDateTime().toLocalTime());

        Customers customer = CustomersQuery.select(appointment.getCustomer_id());
        Users user = UsersQuery.select(appointment.getUser_id());
        Contacts contact = ContactsQuery.select(appointment.getContact_id());

        customerDropDown.setValue(customer.getCustomer_name());
        assignUserDropDown.setValue(user.getUser_name());
        contactInfoDropDown.setValue(contact.getEmail());
    }

    /**
     * Initializes the controller class.
     * Disables the ID field and sets its prompt text to "Auto-generated".
     * Initializes the drop-down menus for users, customers, and contacts.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     * @throws RuntimeException if there is a problem initializing the drop-down menus.
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
