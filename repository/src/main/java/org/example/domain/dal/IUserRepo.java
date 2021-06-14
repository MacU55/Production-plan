package org.example.domain.dal;

import org.example.domain.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IUserRepo {
    User save(Connection connection, User user) throws SQLException;

    User update(Connection connection, User user) throws SQLException;
    
    User findById(Connection connection, int id) throws SQLException;

    User findByEmail(Connection connection, String email) throws SQLException;

    List<User> findAll(Connection connection) throws SQLException;
}
