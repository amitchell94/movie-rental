package data;

import application.Movie;
import application.Rental;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbRentalRepository implements RentalRepository {
    @Override
    public void save(Rental rental) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());

            preparedStatement = connection.prepareStatement("insert into  blockbuster.rentals (r_c_id, r_m_id, r_rental_date, r_return_date, r_cost) " +
                    "values (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, rental.getCustomerID());
            preparedStatement.setInt(2, rental.getMovieID());
            preparedStatement.setDate(3, Date.valueOf(rental.getRentalDate()));
            if (rental.getReturnDate() != null) {
                preparedStatement.setDate(4, Date.valueOf(rental.getReturnDate()));
            } else {
                preparedStatement.setDate(4,null);
            }
            if (rental.getCost() != null) {
                preparedStatement.setDouble(5, rental.getCost());
            } else {
                preparedStatement.setString(5, null);
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Rental> getAllRentals() {
        List<Rental> rentalList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());

            preparedStatement = connection.prepareStatement("select * from blockbuster.rentals");

            rentalList = transformToRentalList(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rentalList;
    }

    @Override
    public List<Integer> getRentedMovieIDs(int customerID) {
        List<Integer> movieIdList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());

            resultSet = connection.createStatement().executeQuery("select r_m_id from blockbuster.rentals where r_c_id = ? and r_return_date is null");
            preparedStatement.setInt(1, customerID);
            movieIdList = transformToMovieIdList(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return movieIdList;
    }

    private List<Rental> transformToRentalList(ResultSet resultSet) throws SQLException {
        List<Rental> rentalList = new ArrayList<>();
        while (resultSet.next()) {
            Rental rental = new Rental();
            rental.setId(resultSet.getInt("r_id"));
            rental.setCustomerID(resultSet.getInt("r_c_id"));
            rental.setMovieID(resultSet.getInt("r_m_id"));
            rental.setRentalDate(resultSet.getDate("r_rental_date").toLocalDate());
            rental.setReturnDate(resultSet.getDate("r_return_date").toLocalDate());
            rental.setCost(resultSet.getDouble("r_cost"));
            rentalList.add(rental);
        }
        return rentalList;
    }

    private List<Integer> transformToMovieIdList(ResultSet resultSet) throws SQLException {
        List<Integer> movieIdList = new ArrayList<>();
        while (resultSet.next()) {
            Integer movieID = resultSet.getInt("m_id");
            movieIdList.add(movieID);
        }
        return movieIdList;
    }
}
