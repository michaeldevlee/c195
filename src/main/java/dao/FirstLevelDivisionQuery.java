package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.*;
import java.util.HashMap;


public abstract class FirstLevelDivisionQuery {

    public static HashMap<String, Integer> selectAndReturnHash() throws SQLException {
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

    public static ObservableList<FirstLevelDivisions> selectAll() throws SQLException {
        String sql = "SELECT * FROM client_schedule.first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ObservableList<FirstLevelDivisions> divisions = FXCollections.observableArrayList();
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

            divisions.add(firstLevelDivision);
        }

        return divisions;
    }

}
