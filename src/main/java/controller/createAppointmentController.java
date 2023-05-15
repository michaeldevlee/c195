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
    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField typeField;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private ComboBox startTimeComboBox;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private ComboBox endTimeComboBox;
    @FXML
    private ComboBox customerDropDown;
    @FXML
    private ComboBox assignUserDropDown;
    @FXML
    private ComboBox contactInfoDropDown;

    @FXML
    private TextField idField;
    @FXML
    private Button createButton;
    @FXML
    private Button exitButton;



    public createAppointmentController() throws SQLException {
    }

    HashMap<String, Integer> userIDs = new HashMap<String, Integer>();
    HashMap<String, Integer> customerIDs = new HashMap<String, Integer>();
    HashMap<String, Integer> contactIDs = new HashMap<String, Integer>();

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
        HashMap<Integer, String> contactEmails = ContactsQuery.getContacts();
        contactEmails.forEach((contactID, email) -> {
            String option = email;
            contactIDs.put(email, contactID);
            contactInfoDropDown.getItems().add(option);
        });

        LoadTimes.loadInTimesByHour(startTimeComboBox);
        LoadTimes.loadInTimesByHour(endTimeComboBox);

    }

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
    @FXML
    void onCancelClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-appointment-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

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
