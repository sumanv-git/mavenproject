package com.tesco.repositories.jpa;

import com.tesco.entity.PurchaseOrder;
import org.springframework.data.jpa.domain.Specification;

public final class PurchaseOrderSpecifications {

  private PurchaseOrderSpecifications() {}

  /** Predicate-only spec (no fetch join here; fetch is handled by PurchaseOrderPaginatedRepository). */
  public static Specification<PurchaseOrder> hasStatus(PurchaseOrder.Status status) {
    return (root, query, cb) ->
        status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
  }
}