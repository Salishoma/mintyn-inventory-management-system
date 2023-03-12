package org.mintyn.sales.inventory.salesinventory.utils;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.CreateProductRequest;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.ProductRequest;
import org.mintyn.sales.inventory.salesinventory.entities.Product;
import org.mintyn.sales.inventory.salesinventory.dto.responsedto.ProductResponse;

@Mapper(
        componentModel = "spring"
)
public interface MapstructMapper {
    Product createProductFromRequest(CreateProductRequest request);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product createProductFromRequest(ProductRequest request, @MappingTarget Product product);
    ProductResponse createResponseFromProduct(Product product);
}
