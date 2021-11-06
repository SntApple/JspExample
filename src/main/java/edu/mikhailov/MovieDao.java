package edu.mikhailov;

import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MovieDao {
    private static final Logger LOGGER = getLogger(MovieDao.class);
    private final Connection conn;

    public MovieDao(Connection conn) {
        this.conn = conn;
    }

    public Movie save(Movie movie) {
        try {
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            if (movie.isNew()) {
                final String insertMovieQuery = "INSERT INTO movie(title) VALUES (?)";
                final String insertInfoQuery = "INSERT INTO info(movie_id, description, year) VALUES (?, ?, ?)";

                try (PreparedStatement insertMovieStm = conn.prepareStatement(insertMovieQuery, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement insertInfoStm = conn.prepareStatement(insertInfoQuery)) {
                    insertMovieStm.setString(1, movie.getTitle());
                    insertMovieStm.executeUpdate();

                    try(ResultSet generatedKeys = insertMovieStm.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            movie.setId(generatedKeys.getInt(1));
                        } else {
                            LOGGER.error("Creating movie failed, no ID");
                            throw new SQLException();
                        }
                    }

                    insertInfoStm.setInt(1, movie.getId());
                    insertInfoStm.setString(2, movie.getDescription());
                    insertInfoStm.setInt(3, movie.getYear());
                    insertInfoStm.executeUpdate();
                }
            }

            else {
                final String updateMovieQuery = "UPDATE movie SET title=? WHERE id=?";
                final String updateInfoQuery = "UPDATE info SET description=?, year=? WHERE movie_id=?";
                try (PreparedStatement updateMovieStm = conn.prepareStatement(updateMovieQuery);
                     PreparedStatement updateInfoStm = conn.prepareStatement(updateInfoQuery)) {
                    updateMovieStm.setString(1, movie.getTitle());
                    updateMovieStm.setInt(2, movie.getId());
                    updateMovieStm.executeUpdate();

                    updateInfoStm.setString(1, movie.getDescription());
                    updateInfoStm.setString(2, String.valueOf(movie.getYear()));
                    updateInfoStm.setString(3, String.valueOf(movie.getId()));
                    if (updateInfoStm.executeUpdate() < 1) {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
                LOGGER.error("SQL Exception, transaction rolled back", e);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return movie;
    }

    public List<Movie> findAll() {
        final List<Movie> movies = new ArrayList<>();
        final String sql = "SELECT id, title, description, year" +
                "FROM movie b" +
                "JOIN info i ON b.id = i.movie_id";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            System.out.println("Where");
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            System.out.println("is");
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Exception");
            while (resultSet.next()) {
                Movie movie = new Movie(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("description"), resultSet.getInt("year"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception, error occurred while reading element");
        }
        return movies;
    }

    public Movie get (int id) {
        Movie movie = null;

        final String sql = "SELECT id, title, description, year" +
                "FROM movie b" +
                "JOIN info i ON b.id = i.movie_id" +
                "WHERE b.id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                movie = new Movie(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("description"), resultSet.getInt("year"));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception, error occurred while attempt to get element by id", e);
        }
        return movie;
    }

    public boolean delete(int id) {
        final String sql = "DELETE FROM movie WHERE id=?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            statement.setInt(1, id);
            if (statement.executeUpdate() < 1) {
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception, error was occurred while deleting element", e);
        }
        return true;
    }
}
