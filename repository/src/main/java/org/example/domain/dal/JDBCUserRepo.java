package org.example.domain.dal;

import org.example.domain.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserRepo implements IUserRepo {

    private static final String INSERT_STATEMENT = "INSERT INTO users(first_name, second_name, password, email) values (?,?,?,?)";
    private static final String UPDATE_STATEMENT = "UPDATE users SET first_name=?,second_name=?,password=?,email=? WHERE id=?";
    private static final String FIND_BY_ID_STATEMENT = "SELECT * FROM users WHERE id=?";
    private static final String FIND_BY_EMAIL_STATEMENT = "SELECT * FROM users WHERE email=?";
    private static final String FIND_ALL_STATEMENT = "SELECT * FROM users";

    private static JDBCUserRepo INSTANCE;

    private JDBCUserRepo() {
    }

    public static synchronized JDBCUserRepo get() {
        if (INSTANCE == null) {
            INSTANCE = new JDBCUserRepo();
        }
        return INSTANCE;
    }

    @Override
    public User save(Connection connection, User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getSecondName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Save User. Failed");
        }
        return this.setIdAndReturn(statement, user);
    }

    @Override
    public User update(Connection connection, User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_STATEMENT);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getSecondName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setInt(5, user.getId());
        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Update User. Failed");
        }
        return user;
    }

    @Override
    public User findById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_STATEMENT);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        User user = null;

        if (resultSet.next()) {
            user = loadFromResult(resultSet);
        }

        return user;
    }

    @Override
    public User findByEmail(Connection connection, String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_STATEMENT);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        User user = null;

        if (resultSet.next()) {
            user = loadFromResult(resultSet);
        }

        return user;
    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_STATEMENT);
        ResultSet resultSet = statement.executeQuery();
        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            User user = loadFromResult(resultSet);
            users.add(user);
        }
        return users;
    }

    public User setIdAndReturn(PreparedStatement statement, User user) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
        return user;
    }

    private static User loadFromResult(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(1));
        user.setFirstName(resultSet.getString(2));
        user.setSecondName(resultSet.getString(3));
        user.setEmail(resultSet.getString(4));
        user.setPassword(resultSet.getString(5));
        return user;
    }
}
