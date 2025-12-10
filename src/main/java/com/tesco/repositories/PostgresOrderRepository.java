package com.tesco.repositories;

import com.tesco.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public class PostgresOrderRepository implements OrderRepository {

    @PersistenceContext private EntityManager em;

    public PostgresOrderRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Order save(Order order) {
        em.getTransaction().begin();
        em.persist(order);
        em.getTransaction().commit();
        return order;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(em.find(Order.class, id));
    }

    @Override
    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }

    @Override
    public void deleteById(UUID id) {
        findById(id).ifPresent(order -> em.remove(order));
    }

    @Override
    public void updateOrder(UUID id, Order updatedOrder) {
        updatedOrder.setId(id);
        em.merge(updatedOrder);
    }
}
