package com.wavestone.shop.adapters.rest.product;

import com.wavestone.shop.order.application.product.ProductDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-16T01:01:41+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Homebrew)"
)
@Component
class ProductCreateDtoMapperImpl implements ProductCreateDtoMapper {

    @Override
    public ProductDto toProductDto(ProductCreateDto from) {
        if ( from == null ) {
            return null;
        }

        String name = null;
        String description = null;

        name = from.getName();
        description = from.getDescription();

        Long id = null;

        ProductDto productDto = new ProductDto( id, name, description );

        return productDto;
    }
}
