package com.tesco.controller;

import com.tesco.dto.PurchaseOrderDto;
import com.tesco.service.purchaseorder.PurchaseOrderService;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

  private final PurchaseOrderService purchaseOrderService;

  public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
    this.purchaseOrderService = purchaseOrderService;
  }

  @GetMapping
  public List<PurchaseOrderDto> getAll() {
    return purchaseOrderService.getAll();
  }

  @GetMapping("/{id}")
  public PurchaseOrderDto getById(@PathVariable UUID id) {
    return purchaseOrderService.getById(id);
  }
}