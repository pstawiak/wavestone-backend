package com.wavestone.shop.order.application.product;

import com.wavestone.shop.domain.product.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-16T01:01:41+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toDto(Product from) {
        if ( from == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;

        id = from.getId();
        name = from.getName();
        description = from.getDescription();

        ProductDto productDto = new ProductDto( id, name, description );

        return productDto;
    }

    @Override
    public List<ProductDto> toDtoList(List<Product> from) {
        if ( from == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( from.size() );
        for ( Product product : from ) {
            list.add( toDto( product ) );
        }

        return list;
    }

    @Override
    public Product toEntity(Product entity, ProductDto from) {
        if ( from == null ) {
            return entity;
        }

        entity.setId( from.id() );
        entity.setName( from.name() );
        entity.setDescription( from.description() );

        return entity;
    }
}
