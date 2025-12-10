package com.tesco.service.order;

import com.tesco.model.Order;
import com.tesco.repositories.OrderRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;


    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        if (order.getId() == null) {
            order =
                    new Order(
                            UUID.randomUUID(),
                            order.getCustomerName(),
                            order.getItemName(),
                            order.getQuantity(),
                            order.getPrice());
        }
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(UUID id) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        return existingOrder.orElse(null);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateStatus(UUID id, Order.Status status) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setStatus(status);
            orderRepository.save(order);
            return order;
        }
        return null;
    }

    @Override
    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void updateOrder(UUID id, Order updatedOrder) {
        orderRepository.updateOrder(id, updatedOrder);
    }
}