package org.mintyn.sales.inventory.salesinventory.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mintyn.inventory.response.exception.ApiResourceNotFoundException;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.CreateProductRequest;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.ProductRequest;
import org.mintyn.sales.inventory.salesinventory.entities.Product;
import org.mintyn.sales.inventory.salesinventory.services.ProductService;
import org.mintyn.sales.inventory.salesinventory.utils.MapstructMapper;
import org.mintyn.sales.inventory.salesinventory.dto.responsedto.ProductResponse;
import org.mintyn.sales.inventory.salesinventory.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MapstructMapper mapper;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ProductResponse createProduct(final CreateProductRequest request) {
        Product product = mapper.createProductFromRequest(request);
        product.setCreatedAt(LocalDate.now());
        productRepository.save(product);
        return  mapper.createResponseFromProduct(product);
    }

    @Override
    public ProductResponse findProduct(final long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiResourceNotFoundException("Product does not exist"));
        return mapper.createResponseFromProduct(product);
    }

    @Override
    public List<ProductResponse> findAllProducts() {
        TypedQuery<ProductResponse> query = entityManager.createQuery("SELECT new org.mintyn.sales.inventory.salesinventory" +
                ".dto.responsedto.ProductResponse(p.id, p.productName, p.price, p.productQuantity, p.description) " +
                "FROM Product p order by p.id asc ", ProductResponse.class);
        return query.getResultList();
    }

    @Override
    public ProductResponse updateProduct(final ProductRequest request, final long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiResourceNotFoundException("Product does not exist"));

        product = mapper.createProductFromRequest(request, product);

        productRepository.save(product);
        return mapper.createResponseFromProduct(product);
    }

}
