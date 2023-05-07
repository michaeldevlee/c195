package dao;

import helper.DivisionCheck;
import helper.JDBC;

import java.sql.*;
import java.time.LocalDate;


public abstract class CustomersQuery {


    public static int insert(int customer_id, String customer_name, String address, String postal_code, String phone, LocalDate create_date, String created_by, Timestamp last_update, String last_updated_by, int division_id) throws SQLException {
        if (!DivisionCheck.checkIfDivisionExists(division_id)) {
            throw new IllegalArgumentException("Division with ID " + division_id + " does not exist");
        }


        String sql = "INSERT INTO client_schedule.customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES(? , ? , ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt( 1, customer_id);
        ps.setString(2, customer_name);
        ps.setString(3, address);
        ps.setString(4, postal_code);
        ps.setString(5, phone);
        ps.setDate(6, Date.valueOf(create_date));
        ps.setString(7, created_by);
        ps.setTimestamp(8, last_update);
        ps.setString(9, last_updated_by);
        ps.setInt(10, division_id);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;

    }

    public static int update(int customer_id, String customer_name, String address, String postal_code, String phone, LocalDate create_date, String created_by, Timestamp last_update, String last_updated_by, int division_id) throws SQLException {
        if (!DivisionCheck.checkIfDivisionExists(division_id)) {
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

    public static void select() throws SQLException {
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
        }
    }

    public static void select(int customer_id) throws SQLException {
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

            System.out.println("Customer ID: " + customerId);
            System.out.println("Customer Name: " + customerName);
            System.out.println("Address: " + address);
            System.out.println("Postal Code: " + postalCode);
            System.out.println("Phone: " + phone);
            System.out.println("Create Date: " + createDate);
            System.out.println("Created By: " + createdBy);
            System.out.println("Last Update: " + lastUpdate);
            System.out.println("Last Updated By: " + lastUpdatedBy);
            System.out.println("Division ID: " + divisionId);
        }
    }


}
