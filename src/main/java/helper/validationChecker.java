package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class validationChecker {
    public static boolean checkIfDivisionExists(int division_id) throws SQLException {
        String sql = "SELECT Division_ID FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, division_id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

}
