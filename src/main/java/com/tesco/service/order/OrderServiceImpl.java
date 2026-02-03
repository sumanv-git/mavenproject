package com.tesco.service.order;

import com.tesco.dto.OrderDto;
import com.tesco.entity.Order;
import com.tesco.repositories.jpa.OrderJpaRepository;
import com.tesco.service.IdGenerator;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final IdGenerator idGenerator;

    public OrderServiceImpl(OrderJpaRepository orderJpaRepository, IdGenerator idGenerator) {
        this.orderJpaRepository = orderJpaRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = toEntity(orderDto);
        Order saved = orderJpaRepository.save(order);
        return toDto(saved);
    }

    @Override
    public OrderDto getOrder(UUID id) {
        Order order =
                orderJpaRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        return toDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderJpaRepository.findAll().stream().map(OrderServiceImpl::toDto).toList();
    }

    @Override
    public OrderDto updateStatus(UUID id, String status) {
        Order order =
                orderJpaRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        order.setStatus(Order.Status.valueOf(status));
        Order saved = orderJpaRepository.save(order);
        return toDto(saved);
    }

    private static OrderDto toDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getCustomerName(),
                order.getItemName(),
                order.getQuantity(),
                order.getPrice(),
                order.getStatus().name(),
                order.getOrderDate());
    }

    private Order toEntity(OrderDto dto) {
        Order order = new Order();
        order.setId(UUID.fromString(idGenerator.generateId()));
        order.setCustomerName(dto.getCustomerName());
        order.setItemName(dto.getItemName());
        order.setQuantity(dto.getQuantity());
        order.setPrice(dto.getPrice());
        if (dto.getStatus() != null) {
            order.setStatus(Order.Status.valueOf(dto.getStatus()));
        }
        order.setOrderDate(dto.getOrderDate());
        return order;
    }

    @Override
    public OrderDto updateOrder(UUID id, OrderDto updatedOrder) {
        Order order =
                orderJpaRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        if (updatedOrder.getCustomerName() != null) {
            order.setCustomerName(updatedOrder.getCustomerName());
        }
        if (updatedOrder.getItemName() != null) {
            order.setItemName(updatedOrder.getItemName());
        }
        if (updatedOrder.getQuantity() != null) {
            order.setQuantity(updatedOrder.getQuantity());
        }
        if (updatedOrder.getPrice() != null) {
            order.setPrice(updatedOrder.getPrice());
        }
        if (updatedOrder.getStatus() != null) {
            order.setStatus(Order.Status.valueOf(updatedOrder.getStatus()));
        }
        if (updatedOrder.getOrderDate() != null) {
            order.setOrderDate(updatedOrder.getOrderDate());
        }

        Order saved = orderJpaRepository.save(order);
        return toDto(saved);
    }

    @Override
    public void deleteOrder(UUID id) {
        if (!orderJpaRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found: " + id);
        }
        orderJpaRepository.deleteById(id);
    }
}
