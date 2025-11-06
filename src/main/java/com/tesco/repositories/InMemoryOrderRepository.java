package com.tesco.repositories;

import com.tesco.model.Order;

import java.util.*;

public class InMemoryOrderRepository {
    private final Map<UUID, Order> store = new HashMap<>();

    public Order save(Order order) {
        store.put(order.getId(), order);
        return order;
    }

    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Order> findAll() {
        return new ArrayList<>(store.values());
    }

    public void deleteById(UUID id) {
        store.remove(id);
    }

    public void updateOrder(UUID id, Order updatedOrder) {
        store.put(id, updatedOrder);
    }
}
