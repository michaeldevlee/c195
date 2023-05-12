package dao;

import helper.JDBC;
import model.Users;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public abstract class UsersQuery {


    public static int insert( String user_name, String password, LocalDate create_date, String created_by, Timestamp last_update, String last_updated_by) throws SQLException {

        String sql = "INSERT INTO client_schedule.users (User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By) " +
                "VALUES(? , ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, user_name);
        ps.setString(2, password);
        ps.setDate(3, Date.valueOf(create_date));
        ps.setString(4, created_by);
        ps.setTimestamp(5, last_update);
        ps.setString(6, last_updated_by);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;

    }

    public static int update(int customer_id, String customer_name, String address, String postal_code, String phone, LocalDate create_date, String created_by, Timestamp last_update, String last_updated_by, int division_id) throws SQLException {

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

    public static Users select(int user_id) throws SQLException {
        String sql = "SELECT * FROM client_schedule.users WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, user_id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = new Timestamp(rs.getDate("Last_Update").getTime());
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            Users user = new Users(
                    userID,
                    userName,
                    password,
                    createDate,
                    createdBy,
                    lastUpdate,
                    lastUpdatedBy
            );

            return  user;
        }

        return null;
    }

    public static boolean select(String user_name, String password) throws SQLException {
        String sql = "SELECT * FROM client_schedule.users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, user_name);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String userPassword = rs.getString("Password");

            if (userPassword.equals(password)){
                System.out.println("you can log in");
                return true;
            }
            else{
                System.out.println("Wrong login info");
                return false;
            }

        }
        System.out.println("cant login");
        return false;
    }

    public static HashMap<Integer, String> getUserNames() throws SQLException {
        String sql = "SELECT User_ID, User_Name FROM client_schedule.users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        HashMap<Integer, String> userNames = new HashMap<Integer, String>();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            userNames.put(userID, userName);
        }
        return userNames;
    }






}
