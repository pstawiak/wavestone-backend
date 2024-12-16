package com.wavestone.shop.adapters.rest.product;

import org.mapstruct.*;

import com.wavestone.shop.order.application.product.ProductDto;

@Mapper(componentModel = "spring")
interface ProductCreateDtoMapper {

	ProductDto toProductDto(ProductCreateDto from);

}
