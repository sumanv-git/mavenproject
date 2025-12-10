package com.tesco.service.order;

import com.tesco.model.Order;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order createOrder(Order order);

    Order getOrder(UUID id);

    List<Order> getAllOrders();

    Order updateStatus(UUID id, Order.Status status);

    void deleteOrder(UUID id);

    void updateOrder(UUID id, Order updatedOrder);
}
