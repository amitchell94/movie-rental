package data;

import application.Movie;
import application.Rental;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    public Rental returnRental(int customerID, int movieID, LocalDate returnDate, Double price) {
        Rental rental = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());

            preparedStatement = connection.prepareStatement("select * from blockbuster.rentals where (r_c_id = ? and r_m_id = ? and r_return_date is null)");
            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, movieID);
            resultSet = preparedStatement.executeQuery();

            rental = new Rental();

            rental = transformToRental(resultSet);

            long daysRented =  ChronoUnit.DAYS.between(rental.getRentalDate(),returnDate);
            if (daysRented == 0) daysRented = 1;
            double totalCost = daysRented * price;

            preparedStatement = connection.prepareStatement("update blockbuster.rentals set r_return_date = ?, r_cost = ?"
                    + " where r_id = ?");
            preparedStatement.setDate(1, Date.valueOf(returnDate));
            preparedStatement.setDouble(2, totalCost);
            preparedStatement.setInt(3, rental.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("select * from blockbuster.rentals where r_id = ?");
            preparedStatement.setInt(1, rental.getId());
            resultSet = preparedStatement.executeQuery();

            rental = transformToRental(resultSet);

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
        return rental;
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

            preparedStatement = connection.prepareStatement("select r_m_id from blockbuster.rentals where r_c_id = ? and r_return_date is null");
            preparedStatement.setInt(1, customerID);

            resultSet = preparedStatement.executeQuery();
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

    @Override
    public boolean movieIsRented(int customerId, int movieId) {
        boolean isRented = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());

            preparedStatement = connection.prepareStatement("select * from blockbuster.rentals where r_c_id = ? and r_m_id = ? and r_return_date is null");
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, movieId);

            resultSet = preparedStatement.executeQuery();
            isRented = resultSet.next();

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
        return isRented;
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
            Integer movieID = resultSet.getInt("r_m_id");
            movieIdList.add(movieID);
        }
        return movieIdList;
    }

    private Rental transformToRental(ResultSet resultSet) throws SQLException {
        Rental rental = new Rental();
        if (resultSet.next()) {
            rental.setId(resultSet.getInt("r_id"));
            rental.setCustomerID(resultSet.getInt("r_c_id"));
            rental.setMovieID(resultSet.getInt("r_m_id"));
            rental.setRentalDate(resultSet.getDate("r_rental_date").toLocalDate());
            if (resultSet.getDate("r_return_date") != null) {
                rental.setReturnDate(resultSet.getDate("r_return_date").toLocalDate());
            } else {
                rental.setReturnDate(null);
            }
            rental.setCost(resultSet.getDouble("r_cost"));
        }
        return rental;
    }
}
