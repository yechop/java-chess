package domain.dao;

import java.sql.SQLException;

public class ChessGameDaoImpl implements ChessGameDao {

    private final UserDao userDao;

    public ChessGameDaoImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void saveData(String boardData, String turnData) {
        final var insertQuery = "INSERT INTO chess VALUES(?, ?)";
        try (final var connection = userDao.getConnection();
             final var insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setString(1, boardData);
            insertStatement.setString(2, turnData);
            insertStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void resetChessBoard() {
        final var resetQuery = "TRUNCATE TABLE chess";
        try (final var connection = userDao.getConnection();
             final var resetStatement = connection.prepareStatement(resetQuery);
        ) {
            resetStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String dataToBoard() {
        var query = "SELECT gameStatus FROM chess";

        try (final var connection = userDao.getConnection()) {
            assert connection != null;
            try (final var preparedStatement = connection.prepareStatement(query)) {

                final var resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("gameStatus");
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String dataToTurn() {
        var query = "SELECT turn FROM chess";

        try (final var connection = userDao.getConnection()) {
            assert connection != null;
            try (final var preparedStatement = connection.prepareStatement(query)) {

                final var resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("turn");
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException();
    }
}
