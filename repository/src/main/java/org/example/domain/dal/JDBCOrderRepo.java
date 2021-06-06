package org.example.domain.dal;

import org.example.domain.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCOrderRepo implements IOrderRepo {
    private static final String INSERT_STATEMENT = "INSERT INTO orders(name, quantity, date_shipment_contract, date_design, date_technology, date_sustain, date_shipment) values (?,?,now(),now(),now(),now(),now())";
    private static final String UPDATE_STATEMENT = "UPDATE orders SET name=?,quantity=? WHERE id=?";
    private static final String FIND_BY_ID_STATEMENT = "SELECT * FROM orders WHERE id=?";
    private static final String FIND_ALL_STATEMENT = "SELECT * FROM orders";

    private static JDBCOrderRepo INSTANCE;

    private JDBCOrderRepo() {
    }

    public static synchronized JDBCOrderRepo get() {
        if (INSTANCE == null) {
            INSTANCE = new JDBCOrderRepo();
        }
        return INSTANCE;
    }

    @Override
    public Order save(Connection connection, Order user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getName());
        statement.setInt(2, user.getQuantity());
        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Save User. Failed");
        }
        return this.setIdAndReturn(statement, user);
    }

    @Override
    public Order update(Connection connection, Order user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_STATEMENT);
        statement.setString(1, user.getName());
        statement.setInt(2, user.getQuantity());
        statement.setInt(3, user.getId());
        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Update User. Failed");
        }
        return user;
    }

    @Override
    public Order findById(Connection connection, int orderId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_STATEMENT);
        statement.setInt(1, orderId);
        ResultSet resultSet = statement.executeQuery();
        Order order = null;

        if (resultSet.next()) {
            order = loadFromResult(resultSet);
        }

        return order;
    }

    @Override
    public List<Order> findAll(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_STATEMENT);
        ResultSet resultSet = statement.executeQuery();
        List<Order> orders = new ArrayList<>();

        while (resultSet.next()) {
            Order order = loadFromResult(resultSet);
            orders.add(order);
        }
        return orders;
    }

    public Order setIdAndReturn(PreparedStatement statement, Order user) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        }
        return user;
    }

    private static Order loadFromResult(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        order.setName(resultSet.getString("name"));
        order.setQuantity(resultSet.getInt("quantity"));
        order.setDateShipmentContract(resultSet.getDate("date_shipment_contract"));
        order.setDateDesign(resultSet.getDate("date_design"));
        order.setDateTechnology(resultSet.getDate("date_technology"));
        order.setDateSustain(resultSet.getDate("date_sustain"));
        order.setDateShipment(resultSet.getDate("date_shipment"));
        return order;
    }
}