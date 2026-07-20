package com.tesco.repositories;

import com.tesco.entity.PurchaseOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseOrderPaginatedRepository {

  @PersistenceContext private EntityManager entityManager;

  public Page<PurchaseOrder> findAll(Specification<PurchaseOrder> spec, Pageable pageable) {
    var builder = entityManager.getCriteriaBuilder();

    var query = builder.createQuery(PurchaseOrder.class);
    var root = query.from(PurchaseOrder.class);

    // fetch join to avoid N+1 when mapping to DTOs (loads purchaseOrderDetails in same query)
    root.fetch("purchaseOrderDetails");
    query.distinct(true);

    if (spec != null) {
      var predicate = spec.toPredicate(root, query, builder);
      if (predicate != null) {
        query.where(predicate);
      }
    }

    if (pageable != null && pageable.getSort().isSorted()) {
      var orders =
          pageable.getSort().stream()
              .map(
                  order ->
                      order.isAscending()
                          ? builder.asc(root.get(order.getProperty()))
                          : builder.desc(root.get(order.getProperty())))
              .toList();
      query.orderBy(orders);
    }

    var typedQuery = entityManager.createQuery(query);

    if (pageable != null && !pageable.isUnpaged()) {
      typedQuery.setFirstResult((int) pageable.getOffset());
      typedQuery.setMaxResults(pageable.getPageSize());
    }

    List<PurchaseOrder> results = typedQuery.getResultList();

    var countQuery = builder.createQuery(Long.class);
    var countRoot = countQuery.from(PurchaseOrder.class);
    countQuery.select(builder.countDistinct(countRoot));

    if (spec != null) {
      var countPredicate = spec.toPredicate(countRoot, countQuery, builder);
      if (countPredicate != null) {
        countQuery.where(countPredicate);
      }
    }

    Long total = entityManager.createQuery(countQuery).getSingleResult();

    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return new PageImpl<>(results, pageable, total);
  }
}