package com.tesco.service.demo;

import com.tesco.entity.Order;
import com.tesco.repositories.jpa.OrderJpaRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderUserNPlusOneDemoService {

    private final OrderJpaRepository orderJpaRepository;

    public OrderUserNPlusOneDemoService(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Transactional(readOnly = true)
    public List<String> nPlusOneOrdersToUsernames() {
        List<Order> orders = orderJpaRepository.findAll(); // 1 + N queries
        return orders.stream().map(o -> o.getUser().getUsername()).toList();
    }

    @Transactional(readOnly = true)
    public List<String> fixedJoinFetchOrdersToUsernames() {
        List<Order> orders = orderJpaRepository.findAllWithUserJoinFetch(); // 1 query
        return orders.stream().map(o -> o.getUser().getUsername()).toList();
    }

    @Transactional(readOnly = true)
    public List<String> fixedEntityGraphOrdersToUsernames() {
        List<Order> orders = orderJpaRepository.findAllWithUserEntityGraph(); // 1 query
        return orders.stream().map(o -> o.getUser().getUsername()).toList();
    }

    public List<String> findAllOrderUserViews() {
        List<String> usernames = orderJpaRepository.findAllOrderUserViews().stream() // 1 query
               .map(view -> view.getUsername())
               .collect(Collectors.toList());
        return usernames;
    }
}