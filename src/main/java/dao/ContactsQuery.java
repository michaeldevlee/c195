package dao;

import helper.JDBC;
import model.Contacts;
import model.Users;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;


public abstract class ContactsQuery {

    public static HashMap<Integer, String> getContacts() throws SQLException {
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM client_schedule.contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        HashMap<Integer, String> contactNames = new HashMap<Integer, String>();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String email = rs.getString("Email");
            contactNames.put(contactID, email);
        }
        return contactNames;
    }

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
