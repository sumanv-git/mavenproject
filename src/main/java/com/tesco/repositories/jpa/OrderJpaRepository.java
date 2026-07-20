package com.tesco.repositories.jpa;

import com.tesco.dto.OrderUserView;
import com.tesco.entity.Order;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerName(String customerName);

    @Query("select o from Order o join fetch o.user")
    List<Order> findAllWithUserJoinFetch();

    @EntityGraph(attributePaths = {"user"})
    @Query("select o from Order o")
    List<Order> findAllWithUserEntityGraph();

    @Query("""
           select o.id as id, o.customerName as customerName, u.username as username
           from Order o
           join o.user u
           """)
    List<OrderUserView> findAllOrderUserViews();
}
