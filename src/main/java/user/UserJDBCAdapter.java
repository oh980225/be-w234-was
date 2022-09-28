package user;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static db.MySQLInfo.*;

public class UserJDBCAdapter implements UserFindable, UserRegisterable {
    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM users WHERE user_id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM users";
    private static final String INSERT_SQL = "INSERT INTO USERS VALUES (?, ?, ?, ?)";


    @Override
    public Optional<User> findByUserId(String userId) {
        try (Connection connection = DriverManager.getConnection(URL.getDetail(), USER.getDetail(), PASSWORD.getDetail());
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID_SQL);
             ResultSet resultSet = executeFindByUserIdQuery(preparedStatement, userId)) {

            return getUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<User> findAll() {
        Set<User> userSet = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(URL.getDetail(), USER.getDetail(), PASSWORD.getDetail());
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            makeAllUserSet(userSet, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userSet;
    }

    @Override
    public void register(User user) {
        try (Connection connection = DriverManager.getConnection(URL.getDetail(), USER.getDetail(), PASSWORD.getDetail());
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            makeInsertUserQuery(user, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Optional<User> getUser(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            var userEntity = new UserEntity(resultSet);
            return Optional.of(userEntity.toUser());
        }

        return Optional.empty();
    }

    private ResultSet executeFindByUserIdQuery(PreparedStatement preparedStatement, String userId) throws SQLException {
        preparedStatement.setString(1, userId);
        return preparedStatement.executeQuery();
    }

    private static void makeAllUserSet(Set<User> userSet, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            var userEntity = new UserEntity(resultSet);
            userSet.add(userEntity.toUser());
        }
    }

    private static void makeInsertUserQuery(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getUserId());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getName());
        preparedStatement.setString(4, user.getEmail());
    }
}
