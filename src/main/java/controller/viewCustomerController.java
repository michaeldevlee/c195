/**

 Controller class for the view customer page.
 */

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

    /**

     The stage for the customer view.
     */
    Stage stage;
    /**

     Table view for displaying the list of customers.
     */
    @FXML
    private TableView<Customers> customersTableView;
    /**

     Column for displaying the customer ID.
     */
    @FXML
    private TableColumn<?, ?> customerID;
    /**

     Column for displaying the customer name.
     */
    @FXML
    private TableColumn<?, ?> name;
    /**

     Column for displaying the customer address.
     */
    @FXML
    private TableColumn<?, ?> address;
    /**

     Column for displaying the customer postal code.
     */
    @FXML
    private TableColumn<?, ?> postalCode;
    /**

     Column for displaying the customer phone number.
     */
    @FXML
    private TableColumn<?, ?> phone;
    /**

     Column for displaying the date the customer was created.
     */
    @FXML
    private TableColumn<?, ?> createDate;
    /**

     Column for displaying the user who created the customer.
     */
    @FXML
    private TableColumn<?, ?> createdBy;
    /**

     Column for displaying the last update date of the customer.
     */
    @FXML
    private TableColumn<?, ?> lastUpdate;
    /**

     Column for displaying the user who last updated the customer.
     */
    @FXML
    private TableColumn<?, ?> lastUpdatedBy;
    /**

     Column for displaying the division ID of the customer.
     */
    @FXML
    private TableColumn<?, ?> divisionID;
    /**

     The list of customers to be displayed in the table view.
     */
    ObservableList<Customers> customers = FXCollections.observableArrayList();

    /**

     Fetches all customers from the database and sets them in the customers ObservableList.
     @throws SQLException If an error occurs while interacting with the database.
     */
    void fetchCustomers() throws SQLException {
        customers = CustomersQuery.select();
    }
    /**

     Handles the click event of the "Add" button, opens the "Create Customer" page when clicked.
     @param event The click event.
     @throws IOException If an error occurs while loading the FXML file for the "Create Customer" page.
     */
    @FXML
    void onAddClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("create-customer-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Handles the event when the edit button is clicked.
     * If a customer is selected, loads the customer's data into the edit form.
     * Otherwise, displays a warning message.
     *
     * @param event the ActionEvent that triggered this method
     * @throws IOException if an error occurs while loading the edit customer form
     * @throws SQLException if an error occurs while executing the SQL query
     */
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer has been deleted");
        Optional<ButtonType> result = alert.showAndWait();
    }

    /**

     Deletes the selected customer from the database and refreshes the customer list.
     Displays an information dialog to notify the user of the deletion.
     @param event The event that triggered the method call.
     @throws SQLException if an SQL exception occurs while accessing the database.
     */
    @FXML
    void onAppointmentListClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-appointment-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the controller by fetching the customers from the database, setting up the table columns,
     * and populating the table with the fetched data.
     * @param url the URL of the FXML file that this controller is associated with
     * @param resourceBundle the resource bundle used to localize the FXML file
     */
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
