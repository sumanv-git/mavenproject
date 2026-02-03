package com.tesco.service.order;

import com.tesco.dto.OrderDto;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    OrderDto getOrder(UUID id);

    List<OrderDto> getAllOrders();

    OrderDto updateStatus(UUID id, String status);

    OrderDto updateOrder(UUID id, OrderDto updatedOrder);

    void deleteOrder(UUID id);
}
