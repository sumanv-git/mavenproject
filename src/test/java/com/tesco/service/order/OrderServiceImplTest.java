package com.tesco.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.tesco.dto.OrderDto;
import com.tesco.entity.Order;
import com.tesco.repositories.jpa.OrderJpaRepository;
import com.tesco.service.IdGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceImplTest {

    private OrderJpaRepository orderJpaRepository;
    private IdGenerator idGenerator;
    private OrderServiceImpl orderService;

    private UUID orderId;
    private Order order;

    @BeforeEach
    void setUp() {
        orderJpaRepository = mock(OrderJpaRepository.class);
        idGenerator = new IdGenerator();
        orderService = new OrderServiceImpl(orderJpaRepository, idGenerator);

        orderId = UUID.randomUUID();
        order =
                new Order(
                        orderId,
                        "john",
                        "iphone",
                        2,
                        new BigDecimal("999.99"),
                        Order.Status.CREATED,
                        LocalDateTime.now());
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // Arrange
        LocalDateTime orderDate = LocalDateTime.now();
        OrderDto request =
                new OrderDto(
                        null, "john", "iphone", 2, new BigDecimal("999.99"), "CREATED", orderDate);

        when(orderJpaRepository.save(any(Order.class)))
                .thenAnswer(
                        invocation -> {
                            Order toSave = invocation.getArgument(0);
                            toSave.setId(orderId);
                            return toSave;
                        });

        // Act
        OrderDto created = orderService.createOrder(request);

        // Assert
        assertThat(created.getId()).isEqualTo(orderId);
        assertThat(created.getCustomerName()).isEqualTo("john");
        assertThat(created.getItemName()).isEqualTo("iphone");
        assertThat(created.getQuantity()).isEqualTo(2);
        assertThat(created.getPrice()).isEqualByComparingTo(new BigDecimal("999.99"));
        assertThat(created.getStatus()).isEqualTo("CREATED");
        assertThat(created.getOrderDate()).isEqualTo(orderDate);

        verify(orderJpaRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldGetOrderSuccessfully() {
        // Arrange
        when(orderJpaRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        OrderDto found = orderService.getOrder(orderId);

        // Assert
        assertThat(found.getId()).isEqualTo(orderId);
        assertThat(found.getCustomerName()).isEqualTo("john");
        assertThat(found.getItemName()).isEqualTo("iphone");
        assertThat(found.getQuantity()).isEqualTo(2);
        assertThat(found.getPrice()).isEqualByComparingTo(new BigDecimal("999.99"));
        assertThat(found.getStatus()).isEqualTo("CREATED");
        verify(orderJpaRepository, times(1)).findById(orderId);
    }

    @Test
    void shouldThrowIfOrderNotFound() {
        // Arrange
        when(orderJpaRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> orderService.getOrder(orderId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Order not found");
        verify(orderJpaRepository, times(1)).findById(orderId);
    }

    @Test
    void shouldGetAllOrdersSuccessfully() {
        // Arrange
        Order order2 =
                new Order(
                        UUID.randomUUID(),
                        "mary",
                        "ipad",
                        1,
                        new BigDecimal("499.00"),
                        Order.Status.SHIPPED,
                        LocalDateTime.now());

        when(orderJpaRepository.findAll()).thenReturn(Arrays.asList(order, order2));

        // Act
        List<OrderDto> result = orderService.getAllOrders();

        // Assert
        assertThat(result).hasSize(2);
        verify(orderJpaRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateOrderStatusSuccessfully() {
        // Arrange
        when(orderJpaRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderJpaRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OrderDto updated = orderService.updateStatus(orderId, "SHIPPED");

        // Assert
        assertThat(updated.getId()).isEqualTo(orderId);
        assertThat(updated.getStatus()).isEqualTo("SHIPPED");
        verify(orderJpaRepository, times(1)).findById(orderId);
        verify(orderJpaRepository, times(1)).save(order);
    }

    @Test
    void shouldThrowWhenUpdatingStatusIfOrderNotFound() {
        // Arrange
        when(orderJpaRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> orderService.updateStatus(orderId, "SHIPPED"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Order not found");

        verify(orderJpaRepository, times(1)).findById(orderId);
        verify(orderJpaRepository, never()).save(any(Order.class));
    }

    @Test
    void shouldDeleteOrderSuccessfully() {
        // Arrange
        when(orderJpaRepository.existsById(orderId)).thenReturn(true);

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderJpaRepository, times(1)).existsById(orderId);
        verify(orderJpaRepository, times(1)).deleteById(orderId);
    }

    @Test
    void shouldThrowWhenDeletingMissingOrder() {
        // Arrange
        when(orderJpaRepository.existsById(orderId)).thenReturn(false);

        // Act + Assert
        assertThatThrownBy(() -> orderService.deleteOrder(orderId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Order not found");

        verify(orderJpaRepository, times(1)).existsById(orderId);
        verify(orderJpaRepository, never()).deleteById(any(UUID.class));
    }
}
