package com.tesco.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {

    private final UUID id;
    private String customerName;
    private String itemName;
    private int quantity;
    private double price;
    private Status status;
    private LocalDateTime orderDate;

    public enum Status {
        NEW, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Order(UUID id, String customerName, String itemName, int quantity, double price) {
        this.id = id;
        this.customerName = customerName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.status = Status.NEW;
        this.orderDate = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public Status getStatus() { return status; }
    public LocalDateTime getOrderDate() { return orderDate; }

    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", status=" + status +
                ", orderDate=" + orderDate +
                '}';
    }
}
