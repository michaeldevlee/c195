/**

 Controller for the view general report page.
 */
package controller;

import com.main.c195.main;
import dao.AppointmentsQuery;
import dao.ContactsQuery;
import dao.CustomersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.HashMap;
import java.util.ResourceBundle;

public class viewGeneralReportController implements Initializable {

    /**
     * The stage of the application.
     */
    Stage stage;

    /**
     * The table column for appointment IDs in the appointments table view.
     */
    @FXML
    private TableColumn<?, ?> appointmentID;

    /**
     * The combo box for selecting a month to filter appointments by.
     */
    @FXML
    private ComboBox<String> appointmentMonthComboBox;

    /**
     * The combo box for selecting an appointment type to filter appointments by.
     */
    @FXML
    private ComboBox<String> appointmentTypeComboBox;

    /**
     * The table view for displaying appointments.
     */
    @FXML
    private TableView<Appointments> appointmentsTableView;

    /**
     * The combo box for selecting a contact name to filter appointments by.
     */
    @FXML
    private ComboBox<String> contactNameComboBox;

    /**
     * The table column for customer IDs in the appointments table view.
     */
    @FXML
    private TableColumn<?, ?> customerID;

    /**
     * The combo box for selecting a month to filter customers by.
     */
    @FXML
    private ComboBox<String> customerMonthComboBox;

    /**
     * The combo box for selecting a year to filter customers by.
     */
    @FXML
    private ComboBox<Integer> customerYearComboBox;

    /**
     * The table column for appointment descriptions in the appointments table view.
     */
    @FXML
    private TableColumn<?, ?> description;

    /**
     * The table column for appointment end times in the appointments table view.
     */
    @FXML
    private TableColumn<?, ?> end;

    /**
     * The table column for appointment start times in the appointments table view.
     */
    @FXML
    private TableColumn<?, ?> start;

    /**
     * The table column for appointment titles in the appointments table view.
     */
    @FXML
    private TableColumn<?, ?> title;

    /**
     * The text displaying the total number of customers in the report.
     */
    @FXML
    private Text totalCustText;

    /**
     * The text displaying the total number of appointments in the report.
     */
    @FXML
    private Text totalNumAppText;

    /**
     * The table column for appointment types in the appointments table view.
     */
    @FXML
    private TableColumn<?, ?> type;

    /**
     * A map containing the IDs of each contact.
     */
    HashMap<String, Integer> contactIDs = new HashMap<String, Integer>();

    /**
     * Initializes the combo boxes and populates them with the relevant data.
     * @throws SQLException if there is an error accessing the database.
     */
    void comboBoxInit() throws SQLException {
        ObservableList<String> monthStrings = FXCollections.observableArrayList();
        ObservableList<Integer> yearInts = FXCollections.observableArrayList();
        for (Month month : Month.values()) {
            monthStrings.add(month.toString().toLowerCase());
        }

        customerMonthComboBox.setItems(monthStrings);
        appointmentMonthComboBox.setItems(monthStrings);

        ObservableList<String> typeStrings = FXCollections.observableArrayList();

        for (String type : AppointmentsQuery.selectTypes()) {
            typeStrings.add(type);
        }

        appointmentTypeComboBox.setItems(typeStrings);

        for (int i = 1949; i < 2050; i++) {
            yearInts.add(i);
        }
        customerYearComboBox.setItems(yearInts);

        //The justification for using this lambda expression is that it allows for a concise
        // and readable way to iterate over the entries in the contactNames HashMap and
        // perform the necessary operations for adding contact names as options to the
        // ComboBox and storing the corresponding IDs in the contactIDs HashMap.
        HashMap<Integer, String> contactNames = ContactsQuery.getContactsByName();
        contactNames.forEach((contactID, name) -> {
            String option = name;
            contactIDs.put(name, contactID);
            contactNameComboBox.getItems().add(name);
        });
    }

    /**
     * Retrieves the total number of appointments that match the selected type and month.
     * @return the total number of appointments.
     * @throws SQLException if there is an error accessing the database.
     */
    public int getTotalNumberOfAppointments() throws SQLException {
        if (appointmentTypeComboBox.getValue() !=null && appointmentMonthComboBox.getValue() != null){
            String type = appointmentTypeComboBox.getValue();
            String month = appointmentMonthComboBox.getValue();
            return AppointmentsQuery.select(type, month);
        }
        return 0;
    }
    /**
     * Calculates and returns the total number of customers based on the selected month and year.
     * @return total number of customers
     * @throws SQLException
     */
    public int getTotalNumberOfCustomers() throws SQLException{
        if (customerMonthComboBox.getValue() != null && customerYearComboBox.getValue() != null){
            String month = customerMonthComboBox.getValue();
            Integer year = customerYearComboBox.getValue();
            return CustomersQuery.selectByMonthAndYear(month, year);

        }
        return 0;
    }

    /**
     * Returns a list of appointments that match the selected contact name.
     * @return filtered list of appointments
     * @throws SQLException
     */
    public ObservableList<Appointments> getFilteredAppointments() throws SQLException {
        ObservableList<Appointments> filteredList = FXCollections.observableArrayList();
        if(contactNameComboBox.getValue() != null){
            int contactId = contactIDs.get(contactNameComboBox.getValue());
            filteredList = AppointmentsQuery.selectByContactId(contactId);

            return filteredList;
        }

        return filteredList;
    }
    /**
     * Updates the total number of appointments displayed based on the selected appointment type.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onAppointmentMonthComboBoxChange(ActionEvent event) throws SQLException {
        totalNumAppText.setText(String.valueOf(getTotalNumberOfAppointments()));
    }

    /**
     * Updates the total number of appointments displayed based on the selected month.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onAppointmentTypeComboBoxChange(ActionEvent event) throws SQLException {
        totalNumAppText.setText(String.valueOf(getTotalNumberOfAppointments()));
    }

    /**
     * Filters the appointments displayed in the TableView based on the selected contact name.
     * If a contact name is selected, displays only appointments associated with the selected contact.
     * If no contact name is selected, displays all appointments.
     *
     * @param event the action event triggered by selecting a contact name from the contactNameComboBox
     * @throws SQLException if an error occurs while accessing the database
     */
    @FXML
    void onContactNameComboBox(ActionEvent event) throws SQLException {
        ObservableList<Appointments> filteredList = getFilteredAppointments();
        for (Appointments appointment :
                filteredList) {
            System.out.println(appointment.getContact_id());
        }
        appointmentsTableView.setItems(getFilteredAppointments());
    }

    /**
     * Updates the "Total Customers" text to display the number of customers created in the selected month and year.
     *
     * @param event the action event triggered by selecting a month from the customerMonthComboBox or a year from the customerYearComboBox
     * @throws SQLException if an error occurs while accessing the database
     */
    @FXML
    void onCustomerMonthComboBox(ActionEvent event) throws SQLException {
        totalCustText.setText(String.valueOf(getTotalNumberOfCustomers()));
    }

    /**
     * Updates the "Total Customers" text to display the number of customers created in the selected month and year.
     *
     * @param event the action event triggered by selecting a month from the customerMonthComboBox or a year from the customerYearComboBox
     * @throws SQLException if an error occurs while accessing the database
     */
    @FXML
    void onCustomerYearComboBox(ActionEvent event) throws SQLException {
        totalCustText.setText(String.valueOf(getTotalNumberOfCustomers()));
    }

    /**
     * Returns the user to the view-appointment-page.fxml when the "Exit" button is clicked.
     *
     * @param event the action event triggered by clicking the "Exit" button
     * @throws IOException if an error occurs while loading the FXML file for the view-appointment-page
     */
    @FXML
    void onExitClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-appointment-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**

     Initializes the viewGeneralReportController by setting up the comboboxes and table columns.

     @param url The location used to resolve relative paths for the root object, or null if the location is not known.

     @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.

     @throws RuntimeException if there is a problem with the SQL query for initializing the comboboxes.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            comboBoxInit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
    }

}
