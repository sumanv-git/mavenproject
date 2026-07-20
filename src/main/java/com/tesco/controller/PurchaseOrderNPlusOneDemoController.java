package com.tesco.controller;

import com.tesco.dto.PurchaseOrderDto;
import com.tesco.entity.PurchaseOrder;
import com.tesco.service.demo.PurchaseOrderNPlusOneDemoService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/n-plus-one/purchase-orders")
public class PurchaseOrderNPlusOneDemoController {

  private final PurchaseOrderNPlusOneDemoService demoService;

  public PurchaseOrderNPlusOneDemoController(PurchaseOrderNPlusOneDemoService demoService) {
    this.demoService = demoService;
  }

  /**
   * Demonstrates N+1:
   * Uses repository.findAll() and then touches po.getPurchaseOrderDetail() during DTO mapping.
   */
  @GetMapping("/problem")
  public List<PurchaseOrderDto> problem() {
    return demoService.nPlusOne_getAllPurchaseOrdersWithDetails();
  }

  /** Fix using @EntityGraph */
  @GetMapping("/solution/entity-graph")
  public List<PurchaseOrderDto> solutionEntityGraph() {
    return demoService.solution_entityGraph_getAllPurchaseOrdersWithDetails();
  }

  /** Fix using JPQL JOIN FETCH */
  @GetMapping("/solution/join-fetch")
  public List<PurchaseOrderDto> solutionJoinFetch() {
    return demoService.solution_joinFetch_getAllPurchaseOrdersWithDetails();
  }

  /**
   * Demo: status filter + pageable/sort while avoiding N+1 via fetch join in custom repository.
   *
   * Example:
   * GET /demo/n-plus-one/purchase-orders/solution/specification-criteria-paged?status=CREATED&page=0&size=10&sort=orderDate,desc
   */
  @GetMapping("/solution/specification-criteria-paged")
  public Page<PurchaseOrderDto> solutionSpecificationCriteriaPaged(
      @RequestParam(required = false) PurchaseOrder.Status status, Pageable pageable) {
    return demoService.solution_specificationCriteria_paginated(status, pageable);
  }
}