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
    Stage stage;
    @FXML
    private TextField customerName;
    @FXML
    private TextField customerAddress;
    @FXML
    private TextField customerPostalCode;
    @FXML
    private TextField customerPhone;

    @FXML
    private ComboBox <String> stateComboBox;

    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private TextField idField;
    HashMap<String, Integer> divisions;
    HashMap<String, Integer> countries;
    public Customers currentCustomer;

    String findDivisionName(int division_id){
        for (Map.Entry<String, Integer> entry : divisions.entrySet()) {
            if (entry.getValue().equals(division_id)) {
                return entry.getKey();
            }
        }
        return null; // or throw an exception, if desired
    }

    void stateComboBoxInit() throws SQLException {
        divisions = FirstLevelDivisionQuery.selectAndReturnHash();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        divisionNames.addAll(divisions.keySet());
        stateComboBox.setItems(divisionNames.sorted());
    }

    void countryComboBoxInit() throws SQLException{
        countries = CountryQuery.select();
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        countryNames.addAll(countries.keySet());
        countryComboBox.setItems(countryNames);
    }

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
    @FXML
    void onCancelClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-customer-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

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

    public void sendCustomer(Customers customer) throws SQLException {

        currentCustomer = customer;
        customerName.setText(customer.getCustomer_name());
        customerAddress.setText(customer.getAddress());
        customerPhone.setText(customer.getPhone());
        customerPostalCode.setText(customer.getPostal_code());
        stateComboBox.setValue(findDivisionName(customer.getDivision_id()));

    }



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
