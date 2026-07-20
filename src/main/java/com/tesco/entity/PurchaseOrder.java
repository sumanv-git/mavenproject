package com.tesco.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

  public enum Status {
    CREATED,
    SHIPPED,
    DELIVERED,
    CANCELLED
  }

  @Id
  private UUID id;

  @Column(name = "customer_name", nullable = false)
  private String customerName;

  @Column(name = "shipping_address")
  private String shippingAddress;

  @Column(name = "notes")
  private String notes;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status = Status.CREATED;

  @Column(name = "order_date", nullable = false)
  private LocalDateTime orderDate;

  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      mappedBy = "purchaseOrder",
      orphanRemoval = true)
  private List<PurchaseOrderDetail> purchaseOrderDetails = new ArrayList<>();

  public PurchaseOrder() {}

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

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public List<PurchaseOrderDetail> getPurchaseOrderDetails() {
    return purchaseOrderDetails;
  }

  public void setPurchaseOrderDetails(List<PurchaseOrderDetail> purchaseOrderDetails) {
    this.purchaseOrderDetails.clear();
    if (purchaseOrderDetails != null) {
      purchaseOrderDetails.forEach(this::addPurchaseOrderDetail);
    }
  }

  public void addPurchaseOrderDetail(PurchaseOrderDetail detail) {
    if (detail == null) {
      return;
    }
    purchaseOrderDetails.add(detail);
    detail.setPurchaseOrder(this);
  }

  public void removePurchaseOrderDetail(PurchaseOrderDetail detail) {
    if (detail == null) {
      return;
    }
    purchaseOrderDetails.remove(detail);
    detail.setPurchaseOrder(null);
  }
}