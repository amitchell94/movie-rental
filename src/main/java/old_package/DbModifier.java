package old_package;

import java.sql.*;

public class DbModifier {

    public void deleteCourse(Connection connection) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("delete from blockbuster.rentals where r_c_id = ? ; ");
        preparedStatement.setString(1, "");
        preparedStatement.executeUpdate();
    }
}
