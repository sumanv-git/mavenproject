package com.tesco.controller;

import com.tesco.dto.OrderDto;
import com.tesco.service.order.OrderService;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable UUID id) {
        return orderService.getOrder(id);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PatchMapping("/{id}/status")
    public OrderDto updateStatus(@PathVariable UUID id, @RequestParam String status) {
        return orderService.updateStatus(id, status);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable UUID id, @RequestBody OrderDto updatedOrder) {
        return orderService.updateOrder(id, updatedOrder);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
    }
}
