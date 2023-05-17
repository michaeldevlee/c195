package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.*;
import java.util.HashMap;


public abstract class FirstLevelDivisionQuery {

    /**
     * Retrieves all first-level divisions from the client_schedule database and returns them as a HashMap
     *
     * @return a HashMap<String, Integer> containing the division names as keys and their corresponding IDs as values
     *
     * @throws SQLException if there is an error executing the SQL query
     */
    public static HashMap<String, Integer> selectAndReturnHash() throws SQLException {
        HashMap<String, Integer> divisions = new HashMap<String, Integer>();
        String sql = "SELECT * FROM client_schedule.first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");

            divisions.put(division, divisionID);

        }

        return divisions;
    }

    /**
     * Retrieves a first-level division from the client_schedule database with the specified division ID and returns it as a FirstLevelDivisions object.
     *
     * @param division_id the ID of the first-level division to retrieve
     * @return a FirstLevelDivisions object representing the division with the specified ID, or null if no such division exists
     * @throws SQLException if there is an error executing the SQL query
     */
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

    /**
     * Retrieves all first-level divisions from the client_schedule database and returns them as an ObservableList of FirstLevelDivisions objects.
     *
     * @return an ObservableList<FirstLevelDivisions> containing all first-level divisions in the database
     * @throws SQLException if there is an error executing the SQL query
     */
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
