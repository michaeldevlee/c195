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

public class viewAppointmentController implements Initializable {

    Stage stage;
    @FXML
    private RadioButton weeklyRadioButton;
    @FXML
    private RadioButton monthlyRadioButton;
    @FXML
    private DatePicker fromWeekPicker;
    @FXML
    private DatePicker toWeekPicker;
    @FXML
    private TableView<Appointments> appointmentsTableView;
    @FXML private TableColumn<?, ?> appointmentID;
    @FXML private TableColumn<?, ?> title;
    @FXML private TableColumn<?, ?> description;
    @FXML private TableColumn<?, ?> location;
    @FXML private TableColumn<?, ?> type;
    @FXML private TableColumn<?, ?> start;
    @FXML private TableColumn<?, ?> end;
    @FXML private TableColumn<?, ?> customerID;
    @FXML private TableColumn<?, ?> contactID;
    @FXML private TableColumn<?, ?> userID;

    ObservableList<Appointments> appointments = FXCollections.observableArrayList();

    void fetchAppointments() throws SQLException {
        appointments = AppointmentsQuery.select();
        Collections.sort(appointments, new Comparator<Appointments>() {
            @Override
            public int compare(Appointments o1, Appointments o2) {
                return o1.getStart().compareTo(o2.getStart());
            }
        });
    }


    void populateAppointments() {
        LocalDate selectedDate = fromWeekPicker.getValue();
        ObservableList<Appointments> appointmentsToShow = FXCollections.observableArrayList();



        if (weeklyRadioButton.isSelected() && fromWeekPicker.getValue() != null) {
            toWeekPicker.setValue(selectedDate.plusDays(7));
            for (Appointments appointment : appointments) {
                LocalDateTime startDate = appointment.getStart();
                LocalDateTime endDate = appointment.getEnd();
                LocalDateTime selectedStartDate = fromWeekPicker.getValue().atTime(LocalTime.MIDNIGHT);
                LocalDateTime selectedEndDate = toWeekPicker.getValue().atTime(LocalTime.MIDNIGHT);

                if (selectedStartDate.isBefore(endDate) && selectedEndDate.isAfter(startDate)) {
                    appointmentsToShow.add(appointment);
                }
            }
        } else if (monthlyRadioButton.isSelected() && fromWeekPicker.getValue() != null) {
            toWeekPicker.setValue(selectedDate.plusDays(30));
            for (Appointments appointment : appointments) {
                LocalDateTime startDate = appointment.getStart();
                LocalDateTime endDate = appointment.getEnd();
                LocalDateTime selectedStartDate = fromWeekPicker.getValue().atTime(LocalTime.MIDNIGHT);
                LocalDateTime selectedEndDate = toWeekPicker.getValue().atTime(LocalTime.MIDNIGHT);

                if (selectedStartDate.isBefore(endDate) && selectedEndDate.isAfter(startDate)) {
                    appointmentsToShow.add(appointment);
                }
            }
        }
        appointmentsTableView.setItems(appointmentsToShow);
    }

    @FXML
    void onAddClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("create-appointment-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
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

    @FXML
    void onDeleteClick(ActionEvent event) throws SQLException {
        int appointment_id = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointment_id();
        AppointmentsQuery.delete(appointment_id);
        appointments = AppointmentsQuery.select();
        appointmentsTableView.setItems(appointments);

    }
    @FXML
    void onWeekPickerChange(ActionEvent event) throws IOException{

        populateAppointments();
    }

    @FXML
    void onWeeklyRadioClick(ActionEvent event) throws  IOException{
        populateAppointments();
    }

    @FXML
    void onMonthlyRadioClick(ActionEvent event) throws  IOException{
        populateAppointments();
    }

    @FXML
    void onCustomerListClick(ActionEvent event) throws  IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-customer-page.fxml"));
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
