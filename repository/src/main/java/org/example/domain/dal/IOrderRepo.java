package org.example.domain.dal;

import org.example.domain.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IOrderRepo {
    Order save(Connection connection, Order order) throws SQLException;

    Order update(Connection connection, Order order) throws SQLException;

    Order findById(Connection connection, int orderId) throws SQLException;

    List<Order> findAll(Connection connection) throws SQLException;
}

