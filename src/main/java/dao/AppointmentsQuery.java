package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.*;


public abstract class AppointmentsQuery {

    public static int insert(String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by, int customer_ID, int user_id, int contact_ID) throws SQLException {

        String sql = "INSERT INTO client_schedule.appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, create_date);
        ps.setString(8, created_by);
        ps.setTimestamp(9, last_update);
        ps.setString(10, last_updated_by);
        ps.setInt(11, customer_ID);
        ps.setInt(12, user_id);
        ps.setInt(13, contact_ID);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;

    }
    public static int update(int appointment_id, String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by, int customer_id, int user_id, int contact_id) throws SQLException {

        String sql = "UPDATE client_schedule.appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?" ;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, create_date);
        ps.setString(8, created_by);
        ps.setTimestamp(9, last_update);
        ps.setString(   10, last_updated_by);
        ps.setInt(11, customer_id);
        ps.setInt(12, user_id);
        ps.setInt(13, contact_id);
        ps.setInt(14, appointment_id);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    public static int delete(int appointment_id) throws SQLException {
        // First delete all appointments associated with this customer
        String deleteAppointmentsSql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
        PreparedStatement deleteAppointmentsPs = JDBC.connection.prepareStatement(deleteAppointmentsSql);
        deleteAppointmentsPs.setInt(1, appointment_id);

        return deleteAppointmentsPs.executeUpdate();
    }
    public static ObservableList<Appointments> select() throws SQLException {
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start =rs.getTimestamp("Start") ;
            Timestamp end = rs.getTimestamp("End");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            Appointments appointment = new Appointments(appointmentID, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
            appointments.add(appointment);
        }

        return appointments;
    }

    public static boolean isOverlapping(Timestamp start, Timestamp end, int customerID) throws SQLException {
        String sql = "SELECT * FROM client_schedule.appointments " +
                "WHERE" +
                "(? >= Start AND ? < End " +
                "OR ? > Start AND ? <= End " +
                "OR ? <= Start AND ? >= End) " +
                " AND Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, start);
        ps.setTimestamp(2, end);
        ps.setTimestamp(3, start);
        ps.setTimestamp(4, end);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, customerID);

        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            return true;
        }
        return false;
    }

    public static Appointments select(int appointment_id) throws SQLException {
        String sql = "SELECT * FROM client_schedule.appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointment_id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start =rs.getTimestamp("Start") ;
            Timestamp end = rs.getTimestamp("End");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            Appointments appointment = new Appointments(appointmentID, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
            return appointment;

        }
        return null;
    }


}
