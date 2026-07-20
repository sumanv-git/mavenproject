package com.tesco.service.purchaseorder;

import com.tesco.dto.PurchaseOrderDto;
import java.util.List;
import java.util.UUID;

public interface PurchaseOrderService {
  List<PurchaseOrderDto> getAll();
  PurchaseOrderDto getById(UUID id);
}