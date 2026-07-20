package com.tesco.dto;

import java.util.UUID;

public interface OrderUserView {
    UUID getId();
    String getCustomerName();
    String getUsername();
}