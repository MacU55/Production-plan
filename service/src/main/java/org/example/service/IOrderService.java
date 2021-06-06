package org.example.service;

import org.example.dto.OrderDTO;
import org.example.exception.ServiceException;

import java.util.List;

public interface IOrderService {

    OrderDTO save(OrderDTO order) throws ServiceException;

    OrderDTO update(OrderDTO order) throws ServiceException;

    OrderDTO findById(int id) throws ServiceException;

    List<OrderDTO> findOrders() throws ServiceException;
}
