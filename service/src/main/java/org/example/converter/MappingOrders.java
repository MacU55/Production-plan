package org.example.converter;

import org.example.domain.entity.Order;
import org.example.dto.OrderDTO;

public class MappingOrders implements Converter<Order, OrderDTO> {

    @Override
    public OrderDTO convert(Order entity) {
        OrderDTO converterToOrderDTO = new OrderDTO();
        converterToOrderDTO.setId(entity.getId());
        converterToOrderDTO.setName(entity.getName());
        converterToOrderDTO.setQuantity(entity.getQuantity());
        converterToOrderDTO.setDateShipmentContract(entity.getDateShipmentContract());
        converterToOrderDTO.setDateDesign(entity.getDateDesign());
        converterToOrderDTO.setDateTechnology(entity.getDateTechnology());
        converterToOrderDTO.setDateSustain(entity.getDateSustain());
        converterToOrderDTO.setDateShipment(entity.getDateShipment());
        return converterToOrderDTO;
    }

    @Override
    public Order convert(OrderDTO entity) {
        Order converterToOrderEntity = new Order();
        converterToOrderEntity.setId(entity.getId());
        converterToOrderEntity.setName(entity.getName());
        converterToOrderEntity.setQuantity(entity.getQuantity());
        converterToOrderEntity.setDateShipmentContract(entity.getDateShipmentContract());
        converterToOrderEntity.setDateDesign(entity.getDateDesign());
        converterToOrderEntity.setDateTechnology(entity.getDateTechnology());
        converterToOrderEntity.setDateSustain(entity.getDateSustain());
        converterToOrderEntity.setDateShipment(entity.getDateShipment());
        return converterToOrderEntity;
    }

}
