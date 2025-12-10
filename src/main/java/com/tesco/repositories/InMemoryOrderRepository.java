package com.tesco.repositories;

import com.tesco.model.Order;
import java.util.*;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> store = new HashMap<>();

    @Override
    public Order save(Order order) {
        store.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }

    @Override
    public void updateOrder(UUID id, Order updatedOrder) {
        store.put(id, updatedOrder);
    }
}
