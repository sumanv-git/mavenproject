package com.tesco.repositories.jpa;

import com.tesco.entity.Order;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerName(String customerName);
}
