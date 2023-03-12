package org.mintyn.sales.inventory.salesinventory.repositories;

import org.mintyn.sales.inventory.salesinventory.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
