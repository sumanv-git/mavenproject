package com.tesco.dto;

import com.tesco.entity.PurchaseOrder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PurchaseOrderDto {
  private UUID id;
  private String customerName;
  private String shippingAddress;
  private String notes;
  private PurchaseOrder.Status status;
  private LocalDateTime orderDate;

  // CHANGED: was PurchaseOrderDetailDto detail;
  private List<PurchaseOrderDetailDto> details;

  public PurchaseOrderDto() {}

  public PurchaseOrderDto(
      UUID id,
      String customerName,
      String shippingAddress,
      String notes,
      PurchaseOrder.Status status,
      LocalDateTime orderDate,
      List<PurchaseOrderDetailDto> details) {
    this.id = id;
    this.customerName = customerName;
    this.shippingAddress = shippingAddress;
    this.notes = notes;
    this.status = status;
    this.orderDate = orderDate;
    this.details = details;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public PurchaseOrder.Status getStatus() {
    return status;
  }

  public void setStatus(PurchaseOrder.Status status) {
    this.status = status;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public List<PurchaseOrderDetailDto> getDetails() {
    return details;
  }

  public void setDetails(List<PurchaseOrderDetailDto> details) {
    this.details = details;
  }
}