package org.example.service;

import org.apache.log4j.Logger;
import org.example.converter.MappingOrders;
import org.example.domain.dal.IOrderRepo;
import org.example.domain.dal.JDBCOrderRepo;
import org.example.domain.entity.Order;
import org.example.dto.OrderDTO;
import org.example.exception.ServiceException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService implements IOrderService {

    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());

    private static OrderService INSTANCE;

    public static synchronized OrderService get() {
        if (INSTANCE == null) {
            INSTANCE = new OrderService();
        }
        return INSTANCE;
    }

    private final IOrderRepo orderRepo = JDBCOrderRepo.get();
    private final MappingOrders converter = new MappingOrders();

    @Override
    public OrderDTO save(OrderDTO order) throws ServiceException {
        if (order == null) {
            throw new ServiceException("Order can't be null");
        }
        try (Connection connection = DBConnection.connect()) {
            Order orderToSave = converter.convert(order);
            Order persistedOrder = orderRepo.save(connection, orderToSave);
            return converter.convert(persistedOrder);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
    }

    @Override
    public OrderDTO update(OrderDTO order) throws ServiceException {
        if (order == null) {
            throw new ServiceException("order can't be null");
        }
        try (Connection connection = DBConnection.connect()) {
            Order orderToUpdate = converter.convert(order);
            Order persistedOrder = orderRepo.update(connection, orderToUpdate);
            return converter.convert(persistedOrder);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
    }

    @Override
    public OrderDTO findById(int orderId) throws ServiceException {
        Order order;
        try (Connection connection = DBConnection.connect()) {
            order = orderRepo.findById(connection, orderId);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
        if (order == null) {
            throw new ServiceException("Order with id: " + orderId + " Not Found.");
        }
        return converter.convert(order);
    }

    @Override
    public List<OrderDTO> findOrders() throws ServiceException {
        try (Connection connection = DBConnection.connect()) {
            List<Order> orders = orderRepo.findAll(connection);
            return orders.stream().map(converter::convert)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
    }
}

