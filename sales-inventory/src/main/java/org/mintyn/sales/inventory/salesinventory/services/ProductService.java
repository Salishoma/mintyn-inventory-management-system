package org.mintyn.sales.inventory.salesinventory.services;

import org.mintyn.sales.inventory.salesinventory.dto.requestdto.CreateProductRequest;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.ProductRequest;
import org.mintyn.sales.inventory.salesinventory.dto.responsedto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(final CreateProductRequest request);
    ProductResponse findProduct(final long productId);
    List<ProductResponse> findAllProducts();
    ProductResponse updateProduct(final ProductRequest request, final long productId);
}
