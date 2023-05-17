package dao;

import helper.JDBC;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;


public abstract class CountryQuery {

    /**

     Selects all countries from the database and returns a map of country names and their corresponding IDs.
     @return a {@code HashMap} containing the name and ID of each country in the database.
     @throws SQLException if a database access error occurs.
     */
    public static HashMap<String, Integer> select() throws SQLException {
        HashMap<String, Integer> countries = new HashMap<>();
        String sql = "SELECT * FROM client_schedule.countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");

            countries.put(country, countryID);

        }

        return countries;
    }



}
