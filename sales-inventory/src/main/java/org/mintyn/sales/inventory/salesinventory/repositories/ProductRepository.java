package org.mintyn.sales.inventory.salesinventory.repositories;

import org.mintyn.sales.inventory.salesinventory.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
