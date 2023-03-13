package org.mintyn.sales.inventory.salesinventory.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.CreateProductRequest;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.ProductRequest;
import org.mintyn.sales.inventory.salesinventory.dto.responsedto.ProductResponse;
import org.mintyn.sales.inventory.salesinventory.entities.Product;
import org.mintyn.sales.inventory.salesinventory.repositories.ProductRepository;
import org.mintyn.sales.inventory.salesinventory.services.ProductService;
import org.mintyn.sales.inventory.salesinventory.services.impl.ProductServiceImpl;
import org.mintyn.sales.inventory.salesinventory.utils.MapstructMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    private CreateProductRequest createProductRequest;
    ProductResponse productResponse = new ProductResponse();

    @Mock
    private MapstructMapper mapper;

    @BeforeEach
    void init() {
        createProductRequest = new CreateProductRequest("mintyn product",
                BigDecimal.valueOf(20), 3, "A product");

        productService = new ProductServiceImpl(productRepository, mapper);

        productResponse.setProductName("mintyn product");
        productResponse.setId(1L);
        productResponse.setPrice(BigDecimal.valueOf(20));
        productResponse.setProductQuantity(3);
        productResponse.setDescription("A product");
    }

    @Test
    void createProductTest() {
        when(mapper.createProductFromRequest(any(CreateProductRequest.class))).thenReturn(createProduct());
        when(mapper.createResponseFromProduct(any(Product.class))).thenReturn(productResponse);
        ProductResponse response = productService.createProduct(createProductRequest);
        assertEquals(response, productResponse);
    }

    @Test
    void findProduct() {
        Product product = createProduct();
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(mapper.createResponseFromProduct(any(Product.class))).thenReturn(productResponse);
        ProductResponse response = productService.findProduct(1L);
        assertEquals(productResponse, response);
    }

    @Test
    void testUpdateProduct() {
        final ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("productName");
        productRequest.setDescription("productDescription");
        productRequest.setPrice(new BigDecimal(10));
        productRequest.setProductQuantity(12);

        Product product = createProduct();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        final Product product2 = new Product();
        product2.setId(1L);
        product2.setProductName("productName");
        product2.setDescription("productDescription");
        product2.setPrice(new BigDecimal(10));
        product2.setProductQuantity(12);

        productResponse.setProductName("productName");
        productResponse.setId(1L);
        productResponse.setPrice(BigDecimal.valueOf(10));
        productResponse.setProductQuantity(12);
        productResponse.setDescription("productDescription");

        when(mapper.createProductFromRequest(productRequest, product)).thenReturn(product2);
        when(productRepository.save(any(Product.class)))
                .thenReturn(product2);
        when(mapper.createResponseFromProduct(product2)).thenReturn(productResponse);

        ProductResponse response = productService.updateProduct(productRequest, 1L);
        assertEquals(1L, response.getId());
        assertEquals("productName", response.getProductName());
        assertEquals("productDescription", response.getDescription());
        assertEquals(BigDecimal.valueOf(10), response.getPrice());
        assertEquals(12, response.getProductQuantity());

    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setProductName(createProductRequest.getProductName());
        product.setPrice(createProductRequest.getPrice());
        product.setProductQuantity(createProductRequest.getProductQuantity());
        product.setDescription(createProductRequest.getDescription());
        return product;
    }
}
