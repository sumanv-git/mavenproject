package com.tesco.repositories.jpa;

import com.tesco.entity.PurchaseOrder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrderJpaRepository extends JpaRepository<PurchaseOrder, UUID> {

  // CHANGED: attributePaths should point to the collection name: purchaseOrderDetails
  @EntityGraph(attributePaths = {"purchaseOrderDetails"})
  @Query("select po from PurchaseOrder po")
  List<PurchaseOrder> findAllWithDetail();

  @EntityGraph(attributePaths = {"purchaseOrderDetails"})
  @Query("select po from PurchaseOrder po where po.id = :id")
  Optional<PurchaseOrder> findByIdWithDetail(UUID id);

  // CHANGED: join fetch the collection
  @Query(
      """
      select distinct po from PurchaseOrder po
      left join fetch po.purchaseOrderDetails
      """)
  List<PurchaseOrder> findAllWithDetailJoinFetch();
}