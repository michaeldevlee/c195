package dao;

import helper.validationChecker;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.HashMap;


public abstract class CustomersQuery {

    /**

     Inserts a new customer record into the database.
     @param customer_name The name of the customer.
     @param address The address of the customer.
     @param postal_code The postal code of the customer.
     @param phone The phone number of the customer.
     @param create_date The date the customer record was created.
     @param created_by The name of the user who created the customer record.
     @param last_update The timestamp when the customer record was last updated.
     @param last_updated_by The name of the user who last updated the customer record.
     @param division_id The ID of the division associated with the customer.
     @return The number of rows affected by the INSERT statement.
     @throws SQLException If an error occurs while executing the SQL statement.
     @throws IllegalArgumentException If the division_id provided does not exist in the database.
     */
    public static int insert(String customer_name, String address, String postal_code, String phone, LocalDate create_date, String created_by, Timestamp last_update, String last_updated_by, int division_id) throws SQLException {
        if (!validationChecker.checkIfDivisionExists(division_id)) {
            throw new IllegalArgumentException("Division with ID " + division_id + " does not exist");
        }

        String sql = "INSERT INTO client_schedule.customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES(? , ? , ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer_name);
        ps.setString(2, address);
        ps.setString(3, postal_code);
        ps.setString(4, phone);
        ps.setDate(5, Date.valueOf(create_date));
        ps.setString(6, created_by);
        ps.setTimestamp(7, last_update);
        ps.setString(8, last_updated_by);
        ps.setInt(9, division_id);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;

    }

    /**

     Updates the customer information in the database.

     @param customer_id the ID of the customer to update

     @param customer_name the updated name of the customer

     @param address the updated address of the customer

     @param postal_code the updated postal code of the customer

     @param phone the updated phone number of the customer

     @param create_date the updated date when the customer was created

     @param created_by the updated name of the user who created the customer

     @param last_update the updated timestamp when the customer was last updated

     @param last_updated_by the updated name of the user who last updated the customer

     @param division_id the updated ID of the division where the customer belongs to

     @return the number of rows affected by the update operation

     @throws SQLException if a database access error occurs

     @throws IllegalArgumentException if the specified division ID does not exist in the database
     */
    public static int update(int customer_id, String customer_name, String address, String postal_code, String phone, LocalDate create_date, String created_by, Timestamp last_update, String last_updated_by, int division_id) throws SQLException {
        if (!validationChecker.checkIfDivisionExists(division_id)) {
            throw new IllegalArgumentException("Division with ID " + division_id + " does not exist");
        }

        String sql = "UPDATE client_schedule.customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?" ;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer_name);
        ps.setString(2, address);
        ps.setString(3, postal_code);
        ps.setString(4, phone);
        ps.setDate(5, Date.valueOf(create_date));
        ps.setString(6, created_by);
        ps.setTimestamp(7, last_update);
        ps.setString(8, last_updated_by);
        ps.setInt(9, division_id);
        ps.setInt(10, customer_id);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    /**

     Deletes a customer and all of their associated appointments from the database.
     @param customer_id the ID of the customer to be deleted
     @return the total number of rows affected (appointments + customer)
     @throws SQLException if a database error occurs
     */
    public static int delete(int customer_id) throws SQLException {
        // First delete all appointments associated with this customer
        String deleteAppointmentsSql = "DELETE FROM client_schedule.appointments WHERE Customer_ID = ?";
        PreparedStatement deleteAppointmentsPs = JDBC.connection.prepareStatement(deleteAppointmentsSql);
        deleteAppointmentsPs.setInt(1, customer_id);
        int appointmentsRowsAffected = deleteAppointmentsPs.executeUpdate();

        // Then delete the customer record
        String deleteCustomerSql = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
        PreparedStatement deleteCustomerPs = JDBC.connection.prepareStatement(deleteCustomerSql);
        deleteCustomerPs.setInt(1, customer_id);
        int customerRowsAffected = deleteCustomerPs.executeUpdate();

        // Return the total number of rows affected (appointments + customer)
        return appointmentsRowsAffected + customerRowsAffected;
    }

    /**

     Selects all customers from the database and returns an ObservableList of Customers.
     @return an ObservableList of Customers containing all customers in the database.
     @throws SQLException if an error occurs while executing the SQL statement.
     */
    public static ObservableList<Customers> select() throws SQLException {
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");

            Customers customer = new Customers(
                    customerId,
                    customerName,
                    address,
                    postalCode,
                    phone,
                    createDate,
                    createdBy,
                    lastUpdate,
                    lastUpdatedBy,
                    divisionId
            );
            customers.add(customer);
        }
        return customers;
    }

    /**

     Retrieves a customer from the database with the given ID.

     @param customer_id the ID of the customer to retrieve

     @return the customer with the given ID, or null if no customer was found with that ID

     @throws SQLException if there is an error accessing the database
     */
    public static Customers select(int customer_id) throws SQLException {
        String sql = "SELECT * FROM client_schedule.customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer_id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");

            Customers customer = new Customers(
                    customerId,
                    customerName,
                    address,
                    postalCode,
                    phone,
                    createDate,
                    createdBy,
                    lastUpdate,
                    lastUpdatedBy,
                    divisionId
            );

            return customer;
        }

        return null;
    }

    /**
     * Returns the count of customers created in the specified month and year.
     *
     * @param month the month to filter by (in the format "January", "February", etc.)
     * @param year the year to filter by (e.g. 2021)
     * @return the count of customers created in the specified month and year
     * @throws SQLException if a database access error occurs
     */

    public static int selectByMonthAndYear(String month, Integer year) throws SQLException {
        int count = 0;
        String sql = "SELECT * FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            LocalDate createDate = rs.getDate("Create_Date").toLocalDate();

            if (createDate.getMonth() == Month.valueOf(month.toUpperCase()) && createDate.getYear()== year){
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves a mapping of customer IDs to customer names for all customers in the database.
     * @return a HashMap containing customer IDs as keys and customer names as values.
     * @throws SQLException if a database access error occurs.
     */
    public static HashMap<Integer, String> getCustomerNames() throws SQLException {
        String sql = "SELECT Customer_ID, Customer_Name FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        HashMap<Integer, String> customerNames = new HashMap<Integer, String>();
        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            customerNames.put(customerID, customerName);
        }
        return customerNames;
    }


}
