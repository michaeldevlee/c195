package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**

 A utility class for checking validations.
 */
public abstract class validationChecker {
    /**

     Checks if a division exists with the given division ID.

     @param division_id the ID of the division to check

     @return true if the division exists, false otherwise

     @throws SQLException if a database access error occurs
     */
    public static boolean checkIfDivisionExists(int division_id) throws SQLException {
        String sql = "SELECT Division_ID FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, division_id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

}
