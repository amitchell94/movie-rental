package data;

import logic.movie.Movie;
import logic.movie.MovieRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbMovieRepository implements MovieRepository {
    @Override
    public void save(Movie movie) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());

            preparedStatement = connection.prepareStatement("insert into  blockbuster.movies (m_title, m_actor, m_year, m_genre, m_price) " +
                    "values (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getActor());
            preparedStatement.setInt(3, movie.getYear());
            preparedStatement.setString(4, movie.getGenre());
            preparedStatement.setDouble(5, movie.getPrice());
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
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();

        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());

            resultSet = connection.createStatement().executeQuery("select * from blockbuster.movies");

            movieList = transformToMovieList(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return movieList;
    }

    private List<Movie> transformToMovieList(ResultSet resultSet) throws SQLException {
        List<Movie> movieList = new ArrayList<>();
        while (resultSet.next()) {
            Movie movie = new Movie();
            movie.setId(resultSet.getInt("m_id"));
            movie.setTitle(resultSet.getString("m_title"));
            movie.setActor(resultSet.getString("m_actor"));
            movie.setYear(resultSet.getInt("m_year"));
            movie.setGenre(resultSet.getString("m_genre"));
            movie.setPrice(resultSet.getDouble("m_price"));
            movieList.add(movie);
        }
        return movieList;
    }

    @Override
    public Movie getMovieFromTitle(String title) {
        Movie movie = new Movie();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());
            preparedStatement = connection.prepareStatement("SELECT * from blockbuster.movies where m_title like ?");
            preparedStatement.setString(1,"%" +  title + "%");
            resultSet =preparedStatement.executeQuery();
            if (resultSet.next()) {
                movie.setId(resultSet.getInt("m_id"));
                movie.setTitle(resultSet.getString("m_title"));
                movie.setActor(resultSet.getString("m_actor"));
                movie.setYear(resultSet.getInt("m_year"));
                movie.setPrice(resultSet.getDouble("m_price"));
            }
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
        return movie;
    }

    @Override
    public Movie getMovieFromId(int movieId) {
        Movie movie = new Movie();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());
            preparedStatement = connection.prepareStatement("SELECT * from blockbuster.movies where m_id = ?");
            preparedStatement.setInt(1,movieId);
            resultSet =preparedStatement.executeQuery();
            if (resultSet.next()) {
                movie.setId(resultSet.getInt("m_id"));
                movie.setTitle(resultSet.getString("m_title"));
                movie.setActor(resultSet.getString("m_actor"));
                movie.setYear(resultSet.getInt("m_year"));
                movie.setPrice(resultSet.getDouble("m_price"));
            }
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
        return movie;
    }
}
