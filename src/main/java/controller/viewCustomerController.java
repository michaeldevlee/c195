package controller;

import com.main.c195.main;
import dao.AppointmentsQuery;
import dao.CustomersQuery;
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
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class viewCustomerController implements Initializable {

    Stage stage;
    @FXML
    private TableView<Customers> customersTableView;
    @FXML private TableColumn<?, ?> customerID;
    @FXML private TableColumn<?, ?> name;
    @FXML private TableColumn<?, ?> address;
    @FXML private TableColumn<?, ?> postalCode;
    @FXML private TableColumn<?, ?> phone;
    @FXML private TableColumn<?, ?> createDate;
    @FXML private TableColumn<?, ?> createdBy;
    @FXML private TableColumn<?, ?> lastUpdate;
    @FXML private TableColumn<?, ?> lastUpdatedBy;
    @FXML private TableColumn<?, ?> divisionID;

    ObservableList<Customers> customers = FXCollections.observableArrayList();

    void fetchCustomers() throws SQLException {
        customers = CustomersQuery.select();
    }

    @FXML
    void onAddClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("create-customer-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void onEditClick(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/main/c195/edit-customer-page.fxml"));
        loader.load();

        editCustomersController editController = loader.getController();
        if (customersTableView.getSelectionModel().getSelectedItem() != null){
            editController.sendCustomer(customersTableView.getSelectionModel().getSelectedItem());

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
        int customer_id = customersTableView.getSelectionModel().getSelectedItem().getCustomer_id();
        CustomersQuery.delete(customer_id);
        customers = CustomersQuery.select();
        customersTableView.setItems(customers);
    }
    @FXML
    void onAppointmentListClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-appointment-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fetchCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        customerID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDate.setCellValueFactory(new PropertyValueFactory<>("create_date"));
        createdBy.setCellValueFactory(new PropertyValueFactory<>("created_by"));
        lastUpdate.setCellValueFactory(new PropertyValueFactory<>("last_update"));
        lastUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("last_updated_by"));
        divisionID.setCellValueFactory(new PropertyValueFactory<>("division_id"));

        customersTableView.setItems(customers);
    }
}
