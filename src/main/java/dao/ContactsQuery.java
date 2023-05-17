package dao;

import helper.JDBC;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.*;
import java.util.HashMap;


public abstract class ContactsQuery {

    /**
     * Retrieves a HashMap containing contact IDs and their corresponding email addresses
     *
     * @return HashMap<Integer, String> containing contact IDs and email addresses
     * @throws SQLException if a database access error occurs or the SQL query is invalid
     */
    public static HashMap<Integer, String> getContactsByEmail() throws SQLException {
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM client_schedule.contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        HashMap<Integer, String> contactEmail = new HashMap<Integer, String>();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String email = rs.getString("Email");
            contactEmail.put(contactID, email);
        }
        return contactEmail;
    }

    /**
     * Retrieves a HashMap containing contact IDs and their corresponding names
     *
     * @return HashMap<Integer, String> containing contact IDs and names
     * @throws SQLException if a database access error occurs or the SQL query is invalid
     */
    public static HashMap<Integer, String> getContactsByName() throws SQLException {
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM client_schedule.contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        HashMap<Integer, String> contactNames = new HashMap<Integer, String>();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            contactNames.put(contactID, name);
        }
        return contactNames;
    }


    /**

     Retrieves a Contact object with the specified ID from the database.
     @param contact_id the ID of the contact to retrieve
     @return the Contact object with the specified ID, or null if the contact does not exist
     @throws SQLException if a database access error occurs or the SQL statement is invalid
     */
    public static Contacts select(int contact_id) throws SQLException {
        String sql = "SELECT * FROM client_schedule.contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contact_id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contacts contact = new Contacts(
                    contactID,
                    contactName,
                    email
            );

            return contact;
        }
        return null;
    }
    /**

     Checks if the specified username and password match an existing user in the database.
     @param user_name the username to check
     @param password the password to check
     @return true if the username and password match an existing user, false otherwise
     @throws SQLException if a database access error occurs or the SQL statement is invalid
     */
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

}
