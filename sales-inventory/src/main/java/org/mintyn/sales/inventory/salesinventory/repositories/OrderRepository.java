package org.mintyn.sales.inventory.salesinventory.repositories;

import org.mintyn.sales.inventory.salesinventory.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
