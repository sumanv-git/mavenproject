package com.tesco.service.demo;

import com.tesco.dto.PurchaseOrderDetailDto;
import com.tesco.dto.PurchaseOrderDto;
import com.tesco.entity.PurchaseOrder;
import com.tesco.entity.PurchaseOrderDetail;
import com.tesco.repositories.PurchaseOrderPaginatedRepository;
import com.tesco.repositories.jpa.PurchaseOrderJpaRepository;
import com.tesco.repositories.jpa.PurchaseOrderSpecifications;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderNPlusOneDemoService {

  private final PurchaseOrderJpaRepository purchaseOrderJpaRepository;
  private final PurchaseOrderPaginatedRepository purchaseOrderPaginatedRepository;

  public PurchaseOrderNPlusOneDemoService(
      PurchaseOrderJpaRepository purchaseOrderJpaRepository,
      PurchaseOrderPaginatedRepository purchaseOrderPaginatedRepository) {
    this.purchaseOrderJpaRepository = purchaseOrderJpaRepository;
    this.purchaseOrderPaginatedRepository = purchaseOrderPaginatedRepository;
  }

  /**
   * N+1 problem (now on @OneToMany):
   * - 1 query to fetch PurchaseOrder rows
   * - N extra queries to fetch purchaseOrderDetails collections (LAZY) during DTO mapping
   */
  @Transactional(readOnly = true)
  public List<PurchaseOrderDto> nPlusOne_getAllPurchaseOrdersWithDetails() {
    List<PurchaseOrder> purchaseOrders = purchaseOrderJpaRepository.findAll(); // 1 + N queries
    return purchaseOrders.stream().map(PurchaseOrderNPlusOneDemoService::toDto).toList();
  }

  @Transactional(readOnly = true)
  public List<PurchaseOrderDto> solution_entityGraph_getAllPurchaseOrdersWithDetails() {
    List<PurchaseOrder> purchaseOrders = purchaseOrderJpaRepository.findAllWithDetail(); // 1 query
    return purchaseOrders.stream().map(PurchaseOrderNPlusOneDemoService::toDto).toList();
  }

  @Transactional(readOnly = true)
  public List<PurchaseOrderDto> solution_joinFetch_getAllPurchaseOrdersWithDetails() {
    List<PurchaseOrder> purchaseOrders = purchaseOrderJpaRepository.findAllWithDetailJoinFetch(); // 1 query
    return purchaseOrders.stream().map(PurchaseOrderNPlusOneDemoService::toDto).toList();
  }

  @Transactional(readOnly = true)
  public PurchaseOrderDto getByIdAlwaysFetchingDetails(UUID id) {
    PurchaseOrder po =
        purchaseOrderJpaRepository
            .findByIdWithDetail(id)
            .orElseThrow(() -> new IllegalArgumentException("PurchaseOrder not found: " + id));
    return toDto(po);
  }

  /**
   * Solution: Specification (status predicate) + custom Criteria repository (fetch join + distinct)
   * + pageable/sort. Avoids N+1 while allowing a filter.
   */
  @Transactional(readOnly = true)
  public Page<PurchaseOrderDto> solution_specificationCriteria_paginated(
      PurchaseOrder.Status status, Pageable pageable) {
    Specification<PurchaseOrder> spec = PurchaseOrderSpecifications.hasStatus(status);

    return purchaseOrderPaginatedRepository
        .findAll(spec, pageable)
        .map(PurchaseOrderNPlusOneDemoService::toDto);
  }

  private static PurchaseOrderDto toDto(PurchaseOrder po) {
    return new PurchaseOrderDto(
        po.getId(),
        po.getCustomerName(),
        po.getShippingAddress(),
        po.getNotes(),
        po.getStatus(),
        po.getOrderDate(),
        toDetailDtos(po.getPurchaseOrderDetails()));
  }

  private static List<PurchaseOrderDetailDto> toDetailDtos(List<PurchaseOrderDetail> details) {
    if (details == null || details.isEmpty()) {
      return Collections.emptyList();
    }
    return details.stream()
        .map(
            d ->
                new PurchaseOrderDetailDto(
                    d.getId(), d.getItemName(), d.getQuantity(), d.getPrice(), d.getDiscount()))
        .toList();
  }
}