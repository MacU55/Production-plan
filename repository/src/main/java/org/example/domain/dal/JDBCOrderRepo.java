package org.example.domain.dal;

import org.example.domain.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class JDBCOrderRepo implements IOrderRepo {
    private static final String INSERT_STATEMENT = "INSERT INTO orders(name, quantity, date_shipment_contract, " +
            "date_design, date_technology, date_sustain, date_shipment) values (?,?,?,?,?,?,?)";
    private static final String UPDATE_STATEMENT = "UPDATE orders SET name=?,quantity=?,date_shipment_contract=?,date_design=?," +
            "date_technology=?,date_sustain=?,date_shipment=? WHERE id=?";
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
    public Order save(Connection connection, Order order) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, order.getName());
        statement.setInt(2, order.getQuantity());

        Date utilDateShipmentContract = order.getDateShipmentContract();
        Date utilDateDesign = order.getDateDesign();
        Date utilDateTechnology = order.getDateTechnology();
        Date utilDateSustain = order.getDateSustain();
        Date utilDateShipment = order.getDateShipment();

        java.sql.Date sqlDateShipmentContract = new java.sql.Date(utilDateShipmentContract.getTime());
        java.sql.Date sqlDateDesign = new java.sql.Date(utilDateDesign.getTime());
        java.sql.Date sqlDateTechnology = new java.sql.Date(utilDateTechnology.getTime());
        java.sql.Date sqlDateSustain = new java.sql.Date(utilDateSustain.getTime());
        java.sql.Date sqlDateShipment = new java.sql.Date(utilDateShipment.getTime());

        statement.setDate(3, sqlDateShipmentContract);
        statement.setDate(4, sqlDateDesign);
        statement.setDate(5, sqlDateTechnology);
        statement.setDate(6, sqlDateSustain);
        statement.setDate(7, sqlDateShipment);

        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Save User. Failed");
        }
        return this.setIdAndReturn(statement, order);
    }

    @Override
    public Order update(Connection connection, Order order) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_STATEMENT);
        statement.setString(1, order.getName());
        statement.setInt(2, order.getQuantity());

        Date utilDateShipmentContract = order.getDateShipmentContract();
        Date utilDateDesign = order.getDateDesign();
        Date utilDateTechnology = order.getDateTechnology();
        Date utilDateSustain = order.getDateSustain();
        Date utilDateShipment = order.getDateShipment();

        java.sql.Date sqlDateShipmentContract = new java.sql.Date(utilDateShipmentContract.getTime());
        java.sql.Date sqlDateDesign = new java.sql.Date(utilDateDesign.getTime());
        java.sql.Date sqlDateTechnology = new java.sql.Date(utilDateTechnology.getTime());
        java.sql.Date sqlDateSustain = new java.sql.Date(utilDateSustain.getTime());
        java.sql.Date sqlDateShipment = new java.sql.Date(utilDateShipment.getTime());

        statement.setDate(3,  sqlDateShipmentContract);
        statement.setDate(4,  sqlDateDesign);
        statement.setDate(5,  sqlDateTechnology);
        statement.setDate(6,  sqlDateSustain);
        statement.setDate(7,  sqlDateShipment);

        statement.setInt(8, order.getId());
        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Update User. Failed");
        }
        return order;
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

    public Order setIdAndReturn(PreparedStatement statement, Order order) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                order.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        }
        return order;
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