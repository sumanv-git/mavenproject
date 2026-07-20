package com.tesco.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class PurchaseOrderDetailDto {
  private UUID id;
  private String itemName;
  private Integer quantity;
  private BigDecimal price;
  private BigDecimal discount;

  public PurchaseOrderDetailDto() {}

  public PurchaseOrderDetailDto(
      UUID id, String itemName, Integer quantity, BigDecimal price, BigDecimal discount) {
    this.id = id;
    this.itemName = itemName;
    this.quantity = quantity;
    this.price = price;
    this.discount = discount;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getDiscount() {
    return discount;
  }

  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }
}