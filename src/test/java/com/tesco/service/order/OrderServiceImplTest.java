package com.tesco.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.tesco.model.Order;
import com.tesco.repositories.InMemoryOrderRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceImplTest {

    private InMemoryOrderRepository orderRepository;
    private OrderServiceImpl orderService;

    private UUID ORDER_ID;
    private Order order;

    @BeforeEach
    void setUp() {
        orderRepository = mock(InMemoryOrderRepository.class);
        orderService = new OrderServiceImpl(orderRepository);

        ORDER_ID = UUID.randomUUID();
        order = new Order(ORDER_ID, "Alice", "Laptop", 1, 999.99);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // given
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // when
        Order createdOrder = orderService.createOrder(order);

        // then
        assertThat(createdOrder).isSameAs(order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void shouldGetOrderSuccessfully() {
        // given
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));

        // when
        Order foundOrder = orderService.getOrder(ORDER_ID);

        // then
        assertThat(foundOrder).isSameAs(order);
        verify(orderRepository, times(1)).findById(ORDER_ID);
    }

    @Test
    void shouldReturnNullIfOrderNotFound() {
        // given
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        // when
        Order foundOrder = orderService.getOrder(ORDER_ID);

        // then
        assertThat(foundOrder).isNull();
        verify(orderRepository, times(1)).findById(ORDER_ID);
    }

    @Test
    void shouldGetAllOrdersSuccessfully() {
        // given
        Order order2 = new Order(UUID.randomUUID(), "Bob", "Phone", 2, 599.99);
        List<Order> allOrders = Arrays.asList(order, order2);
        when(orderRepository.findAll()).thenReturn(allOrders);

        // when
        List<Order> result = orderService.getAllOrders();

        // then
        assertThat(result).containsExactlyInAnyOrder(order, order2);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateOrderStatusSuccessfully() {
        // given
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // when
        Order.Status newStatus = Order.Status.SHIPPED;
        Order updatedOrder = orderService.updateStatus(ORDER_ID, newStatus);

        // then
        assertThat(updatedOrder.getStatus()).isEqualTo(Order.Status.SHIPPED);
        verify(orderRepository, times(1)).findById(ORDER_ID);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void shouldReturnNullWhenUpdatingStatusIfOrderNotFound() {
        // given
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        // when
        Order updatedOrder = orderService.updateStatus(ORDER_ID, Order.Status.SHIPPED);

        // then
        assertThat(updatedOrder).isNull();
        verify(orderRepository, times(1)).findById(ORDER_ID);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void shouldDeleteOrderSuccessfully() {
        // when
        orderService.deleteOrder(ORDER_ID);

        // then
        verify(orderRepository, times(1)).deleteById(ORDER_ID);
    }
}
