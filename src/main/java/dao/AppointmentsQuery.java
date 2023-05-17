package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.*;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;


public abstract class AppointmentsQuery {

    /**
     * Inserts a new appointment record into the database.
     *
     * @param title           the title of the appointment
     * @param description     the description of the appointment
     * @param location        the location of the appointment
     * @param type            the type of the appointment
     * @param start           the start time of the appointment
     * @param end             the end time of the appointment
     * @param create_date     the create date of the appointment
     * @param created_by      the user who created the appointment
     * @param last_update     the last update time of the appointment
     * @param last_updated_by the user who last updated the appointment
     * @param customer_ID     the ID of the customer associated with the appointment
     * @param user_id         the ID of the user associated with the appointment
     * @param contact_ID      the ID of the contact associated with the appointment
     * @return the number of rows affected by the SQL statement
     * @throws SQLException if an SQL exception occurs while executing the query
     */
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

    /**
     * Updates an appointment in the appointments table in the database.
     * @param appointment_id the ID of the appointment to update
     * @param title the new title of the appointment
     * @param description the new description of the appointment
     * @param location the new location of the appointment
     * @param type the new type of the appointment
     * @param start the new start time of the appointment
     * @param end the new end time of the appointment
     * @param create_date the create date of the appointment
     * @param created_by the creator of the appointment
     * @param last_update the last update time of the appointment
     * @param last_updated_by the last user who updated the appointment
     * @param customer_id the ID of the customer associated with the appointment
     * @param user_id the ID of the user who created the appointment
     * @param contact_id the ID of the contact associated with the appointment
     * @return the number of rows affected by the update
     * @throws SQLException if an error occurs while updating the appointment
     */
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

    /**
     * Deletes an appointment from the database.
     *
     * @param appointment_id the ID of the appointment to delete
     * @return the number of rows affected
     * @throws SQLException if a database access error occurs
     */
    public static int delete(int appointment_id) throws SQLException {
        // First delete all appointments associated with this customer
        String deleteAppointmentsSql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
        PreparedStatement deleteAppointmentsPs = JDBC.connection.prepareStatement(deleteAppointmentsSql);
        deleteAppointmentsPs.setInt(1, appointment_id);

        return deleteAppointmentsPs.executeUpdate();
    }

    /**
     * Retrieves all appointments from the database and returns them as an ObservableList.
     *
     * @return An ObservableList of Appointments retrieved from the database.
     * @throws SQLException If a database access error occurs.
     */
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

    /**

     Selects all appointments associated with a given contact ID.
     @param contact_id the ID of the contact to search for
     @return an ObservableList of Appointments associated with the given contact ID
     @throws SQLException if a database access error occurs
     */
    public static ObservableList<Appointments> selectByContactId(int contact_id) throws SQLException {
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contact_id);
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

    /**

     Checks if the given appointment start and end times overlap with any existing appointments for the specified customer.
     @param start The start time of the appointment to check for overlap.
     @param end The end time of the appointment to check for overlap.
     @param customerID The ID of the customer associated with the appointment.
     @return true if the given appointment overlaps with any existing appointments, false otherwise.
     @throws SQLException If there is an error executing the SQL query.
     */
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

    /**

     Checks if there is an appointment coming up within 15 minutes of the given login time.

     @param loginTime the login time of the user

     @return a message indicating whether or not an appointment is coming up within 15 minutes

     @throws SQLException if a database access error occurs
     */
    public static String isAppointmentComingUp(Timestamp loginTime) throws SQLException {
        Timestamp end = new Timestamp(loginTime.getTime() + (15 * 60 * 1000)); // 15 minutes in milliseconds
        String sql = "SELECT * FROM client_schedule.appointments " +
                "WHERE End >= ? AND End <= ? ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, loginTime);
        ps.setTimestamp(2, end);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // An appointment is coming up within 15 minutes
            String appointmentId = rs.getString("Appointment_ID");
            String appointmentDate = rs.getString("Start").substring(0, 10); // Extract date portion from timestamp
            String appointmentTime = rs.getString("Start").substring(11, 16); // Extract time portion from timestamp
            String message = "You have an appointment coming up within 15 minutes:\n" +
                    "Appointment ID: " + appointmentId + "\n" +
                    "Date: " + appointmentDate + "\n" +
                    "Time: " + appointmentTime;
            return message;
        } else {
            // No appointments within 15 minutes
            String message = "You do not have any upcoming appointments within the next 15 minutes.";

            return message;
        }
    }

    /**

     Selects the count of appointments of a certain type in a given month.

     @param _type the appointment type

     @param _month the month in which the appointments occur

     @return the count of appointments of the given type in the given month

     @throws SQLException if a database access error occurs
     */
    public static int select(String _type, String _month) throws SQLException {
        int count = 0;
        String sql = "SELECT * FROM client_schedule.appointments WHERE Type = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, _type);

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Timestamp start =rs.getTimestamp("Start") ;


            if (start.toLocalDateTime().toLocalDate().getMonth() == Month.valueOf(_month.toUpperCase())){
                count++;
            }
        }
        return count;
    }

    /**

     Retrieves the distinct types of appointments from the database.
     @return A Set of Strings representing the distinct types of appointments.
     @throws SQLException if a database access error occurs or this method is called on a closed connection.
     */
    public static Set<String> selectTypes() throws SQLException {
        String sql = "SELECT * FROM client_schedule.appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        Set<String> types = new HashSet<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String type = rs.getString("Type");

            types.add(type);
        }
        return types;
    }


    /**

     Selects an appointment from the database based on its unique ID.

     @param appointment_id the unique ID of the appointment to be selected

     @return an Appointments object representing the selected appointment, or null if no appointment with the given ID exists

     @throws SQLException if an error occurs while executing the SQL query
     */
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
