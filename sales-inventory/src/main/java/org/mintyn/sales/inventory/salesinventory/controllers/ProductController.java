package org.mintyn.sales.inventory.salesinventory.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.CreateProductRequest;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.ProductRequest;
import org.mintyn.sales.inventory.salesinventory.dto.responsedto.ProductResponse;
import org.mintyn.sales.inventory.salesinventory.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @RequestMapping(
            value = "", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody final CreateProductRequest createProductRequest) {
        return new ResponseEntity<>(productService.createProduct(createProductRequest), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/{productId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<ProductResponse> getProduct(@PathVariable final long productId) {
        return ResponseEntity.ok(productService.findProduct(productId));
    }

    @RequestMapping(
            value = "", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @RequestMapping(
            value = "/{productId}", method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<ProductResponse> updateProduct(
            @RequestBody final ProductRequest productRequest,
            @PathVariable final long productId
            ) {
        return ResponseEntity.ok(productService.updateProduct(productRequest, productId));
    }

}
