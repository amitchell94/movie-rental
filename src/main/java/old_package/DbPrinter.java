package old_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbPrinter {
     public void writeMetaData(ResultSet resultSet) throws SQLException {
        System.out.println("The columns in the table are: ");
        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
        }
    }

     public void writeRentalsResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.println("---------------------------------");
            System.out.println("Customer ID: " + resultSet.getInt("r_c_id"));
            System.out.println("Movie ID: " + resultSet.getInt("r_m_id"));
            System.out.println("Rental Date: " + resultSet.getString("r_rental_date"));
            System.out.println("Return Date: " + resultSet.getString("r_return_date"));
            System.out.println("Cost: " + resultSet.getDouble("r_cost"));
        }
         System.out.println("---------------------------------");
    }

    public void printMovieList (Connection connection) throws Exception{
        ResultSet resultSet;
        PreparedStatement preparedStatement = null;
        try {
            System.out.println("Here are the movies you can choose from:");
            preparedStatement = connection.prepareStatement("SELECT m_title, m_year, m_actor,m_genre from blockbuster.movies");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.print(resultSet.getString("m_title"));
                System.out.println(" (" + resultSet.getString("m_year").substring(0,4) + ")");
                System.out.println("Starring " + resultSet.getString("m_actor"));
                System.out.println("Genre: " + resultSet.getString("m_genre"));
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}
