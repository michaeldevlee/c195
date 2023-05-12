package dao;

import helper.validationChecker;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;


public abstract class CustomersQuery {

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
