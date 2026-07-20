package com.tesco.service.purchaseorder;

import com.tesco.dto.PurchaseOrderDetailDto;
import com.tesco.dto.PurchaseOrderDto;
import com.tesco.entity.PurchaseOrder;
import com.tesco.entity.PurchaseOrderDetail;
import com.tesco.repositories.jpa.PurchaseOrderJpaRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

  private final PurchaseOrderJpaRepository purchaseOrderJpaRepository;

  public PurchaseOrderServiceImpl(PurchaseOrderJpaRepository purchaseOrderJpaRepository) {
    this.purchaseOrderJpaRepository = purchaseOrderJpaRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<PurchaseOrderDto> getAll() {
    return purchaseOrderJpaRepository.findAllWithDetail().stream()
        .map(PurchaseOrderServiceImpl::toDto)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public PurchaseOrderDto getById(UUID id) {
    PurchaseOrder po =
        purchaseOrderJpaRepository
            .findByIdWithDetail(id)
            .orElseThrow(() -> new IllegalArgumentException("PurchaseOrder not found: " + id));
    return toDto(po);
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