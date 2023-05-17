package dao;

import helper.JDBC;
import model.Users;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public abstract class UsersQuery {


    /**
     * Inserts a new user into the client_schedule database with the given user name, password, create date, created by, and last updated by fields.
     *
     * @param user_name the user name for the new user
     * @param password the password for the new user
     * @param create_date the create date for the new user
     * @param created_by the created by field for the new user
     * @param last_update the last update timestamp for the new user
     * @param last_updated_by the last updated by field for the new user
     * @return the number of rows affected by the insert operation
     * @throws SQLException if there is an error executing the SQL insert statement
     */
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

    /**
     * Retrieves a user with the given user ID from the client_schedule database and returns it as a Users object.
     *
     * @param user_id the user ID of the user to retrieve
     * @return a Users object representing the user with the given user ID, or null if no such user exists in the database
     * @throws SQLException if there is an error executing the SQL query
     */
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

    /**
     * Checks if the given username and password match a user in the database.
     *
     * @param user_name The username to check.
     * @param password The password to check.
     * @return true if the given username and password match a user in the database; false otherwise.
     * @throws SQLException If an error occurs while executing the SQL query.
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

    /**
     * Retrieves a map of user IDs and corresponding usernames from the database.
     *
     * @return A HashMap containing the user IDs as keys and the corresponding usernames as values.
     * @throws SQLException If an error occurs while executing the SQL query.
     */
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
