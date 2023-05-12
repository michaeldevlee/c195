package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;
import model.Users;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;


public abstract class FirstLevelDivisionQuery {

    public static HashMap<String, Integer> select() throws SQLException {
        HashMap<String, Integer> divisions = new HashMap<String, Integer>();
        String sql = "SELECT * FROM client_schedule.first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("COUNTRY_ID");

            divisions.put(division, divisionID);

        }

        return divisions;
    }

    public static FirstLevelDivisions select(int division_id) throws SQLException {
        String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, division_id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("COUNTRY_ID");

            FirstLevelDivisions firstLevelDivision = new FirstLevelDivisions(
                    divisionID,
                    division,
                    createDate,
                    createdBy,
                    lastUpdate,
                    lastUpdatedBy,
                    countryID
            );

            return  firstLevelDivision;
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
