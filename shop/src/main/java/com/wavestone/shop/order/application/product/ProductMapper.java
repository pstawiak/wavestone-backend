package com.wavestone.shop.order.application.product;

import java.util.List;

import org.mapstruct.*;

import com.wavestone.shop.domain.product.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductDto toDto(Product from);

	List<ProductDto> toDtoList(List<Product> from);

	Product toEntity(@MappingTarget Product entity, ProductDto from);

}
