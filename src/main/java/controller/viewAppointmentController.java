package controller;

import com.main.c195.main;
import dao.AppointmentsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for the view-appointment-page.fxml file.
 * Handles displaying appointments in a table view and allows for filtering by week or month.
 */
public class viewAppointmentController implements Initializable {

    /**
     * The main stage of the application.
     */
    Stage stage;

    /**
     * The radio button to display appointments by week.
     */
    @FXML
    private RadioButton weeklyRadioButton;

    /**
     * The radio button to display appointments by month.
     */
    @FXML
    private RadioButton monthlyRadioButton;

    /**
     * The date picker for the start of the week.
     */
    @FXML
    private DatePicker fromWeekPicker;

    /**
     * The date picker for the end of the week.
     */
    @FXML
    private DatePicker toWeekPicker;

    /**
     * The table view to display appointments.
     */
    @FXML
    private TableView<Appointments> appointmentsTableView;

    /**
     * The table column for the appointment ID.
     */
    @FXML
    private TableColumn<?, ?> appointmentID;

    /**
     * The table column for the appointment title.
     */
    @FXML
    private TableColumn<?, ?> title;

    /**
     * The table column for the appointment description.
     */
    @FXML
    private TableColumn<?, ?> description;

    /**
     * The table column for the appointment location.
     */
    @FXML
    private TableColumn<?, ?> location;

    /**
     * The table column for the appointment type.
     */
    @FXML
    private TableColumn<?, ?> type;

    /**
     * The table column for the appointment start time.
     */
    @FXML
    private TableColumn<?, ?> start;

    /**
     * The table column for the appointment end time.
     */
    @FXML
    private TableColumn<?, ?> end;

    /**
     * The table column for the customer ID of the appointment.
     */
    @FXML
    private TableColumn<?, ?> customerID;

    /**
     * The table column for the contact ID of the appointment.
     */
    @FXML
    private TableColumn<?, ?> contactID;

    /**
     * The table column for the user ID of the appointment.
     */
    @FXML
    private TableColumn<?, ?> userID;

    /**

     The ObservableList of Appointments to be fetched and displayed in the table view.
     */
    ObservableList<Appointments> appointments = FXCollections.observableArrayList();

    /**

     Fetches all the appointments from the database using the AppointmentsQuery class and sorts them by start date.
     @throws SQLException if an error occurs while executing the SQL query
     */
    void fetchAppointments() throws SQLException {
        appointments = AppointmentsQuery.select();
        Collections.sort(appointments, new Comparator<Appointments>() {
            @Override
            public int compare(Appointments o1, Appointments o2) {
                return o1.getStart().compareTo(o2.getStart());
            }
        });
    }

    /**

     Populates the appointment table view based on the selected date range and filter option
     */
    void populateAppointments() {
        LocalDate selectedDate = fromWeekPicker.getValue();
        ObservableList<Appointments> appointmentsToShow = FXCollections.observableArrayList();



        if (weeklyRadioButton.isSelected() && fromWeekPicker.getValue() != null) {
            toWeekPicker.setValue(selectedDate.plusDays(7));
            for (Appointments appointment : appointments) {
                LocalDateTime startDate = appointment.getStart().toLocalDateTime();
                LocalDateTime endDate = appointment.getEnd().toLocalDateTime();
                LocalDateTime selectedStartDate = fromWeekPicker.getValue().atTime(LocalTime.MIDNIGHT);
                LocalDateTime selectedEndDate = toWeekPicker.getValue().atTime(LocalTime.MIDNIGHT);

                if (selectedStartDate.isBefore(endDate) && selectedEndDate.isAfter(startDate)) {
                    appointmentsToShow.add(appointment);
                }
            }
        } else if (monthlyRadioButton.isSelected() && fromWeekPicker.getValue() != null) {
            toWeekPicker.setValue(selectedDate.plusDays(30));
            for (Appointments appointment : appointments) {
                LocalDateTime startDate = appointment.getStart().toLocalDateTime();
                LocalDateTime endDate = appointment.getEnd().toLocalDateTime();
                LocalDateTime selectedStartDate = fromWeekPicker.getValue().atTime(LocalTime.MIDNIGHT);
                LocalDateTime selectedEndDate = toWeekPicker.getValue().atTime(LocalTime.MIDNIGHT);

                if (selectedStartDate.isBefore(endDate) && selectedEndDate.isAfter(startDate)) {
                    appointmentsToShow.add(appointment);
                }
            }
        }
        appointmentsTableView.setItems(appointmentsToShow);
    }

    /**

     Event handler for the "Add" button click.
     Changes the scene to the "create-appointment-page.fxml" view.
     @param event the event triggered by the button click
     @throws IOException if an I/O error occurs
     */
    @FXML
    void onAddClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("create-appointment-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }


    /**

     Handles the event when the "Edit" button is clicked by loading the edit appointment page
     and passing the selected appointment to it for editing.
     If no appointment is selected, a warning message is displayed to the user.
     @param event the event triggered by clicking the "Edit" button
     @throws IOException if there is an error loading the FXML file
     @throws SQLException if there is an error executing the SQL query
     */
    @FXML
    void onEditClick(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/main/c195/edit-appointment-page.fxml"));
        loader.load();

        editAppointmentController editController = loader.getController();
        if (appointmentsTableView.getSelectionModel().getSelectedItem() != null){
            editController.sendAppointment(appointmentsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "No item to modify, please select an appointment");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**

     Handles the event when the "Delete" button is clicked.

     Deletes the selected appointment from the database and updates the table view.

     Displays an alert message with the appointment ID and type that was deleted.

     @param event the ActionEvent object generated when the button is clicked

     @throws SQLException if an SQL exception occurs while accessing the database
     */
 @FXML
    void onDeleteClick(ActionEvent event) throws SQLException {
        int appointment_id = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointment_id();
        String appointment_type = AppointmentsQuery.select(appointment_id).getType();
        AppointmentsQuery.delete(appointment_id);
        appointments = AppointmentsQuery.select();
        appointmentsTableView.setItems(appointments);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Canceled appointment | id: " + appointment_id +" | " + " type: " + appointment_type);
        Optional<ButtonType> result = alert.showAndWait();

    }

    /**

     This method is called when the week picker value is changed.
     It calls the populateAppointments() method to update the appointments shown.
     @param event The event triggered by the week picker.
     @throws IOException If an I/O exception occurs.
     */
    @FXML
    void onWeekPickerChange(ActionEvent event) throws IOException{

        populateAppointments();
    }

    /**

     This method is called when the weekly radio button is clicked.
     It calls the populateAppointments() method to update the appointments shown.
     @param event The event triggered by the weekly radio button.
     @throws IOException If an I/O exception occurs.
     */
    @FXML
    void onWeeklyRadioClick(ActionEvent event) throws  IOException{
        populateAppointments();
    }

    /**

     This method is called when the monthly radio button is clicked.
     It calls the populateAppointments() method to update the appointments shown.
     @param event The event triggered by the monthly radio button.
     @throws IOException If an I/O exception occurs.
     */
    @FXML
    void onMonthlyRadioClick(ActionEvent event) throws  IOException{
        populateAppointments();
    }

    /**
     * Handles the event when the "Customer List" button is clicked by opening the "View Customer" page.
     *
     * @param event The event triggered by clicking the "Customer List" button.
     * @throws IOException If an error occurs while loading the "View Customer" page.
     */
    @FXML
    void onCustomerListClick(ActionEvent event) throws  IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-customer-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void onGenerateReportsClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-general-report-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        weeklyRadioButton.setSelected(true);
        toWeekPicker.setDisable(true);
        try {
            fetchAppointments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        contactID.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        userID.setCellValueFactory(new PropertyValueFactory<>("contact_id"));

        appointmentsTableView.setItems(appointments);
    }
}
