package old_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbAccessor {

    public Integer getID(String Name, String idColumn, String nameColumn, String table,Connection connection) throws Exception {
        Integer id = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT " + idColumn + " from blockbuster."
                    + table + " where " + nameColumn + "=?");
            preparedStatement.setString(1, Name);
            resultSet =preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(idColumn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return id;
    }
}
