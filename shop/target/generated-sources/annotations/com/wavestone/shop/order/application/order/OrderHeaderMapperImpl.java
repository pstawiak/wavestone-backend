package com.wavestone.shop.order.application.order;

import com.wavestone.shop.domain.order.OrderHeader;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-16T01:01:41+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class OrderHeaderMapperImpl implements OrderHeaderMapper {

    @Override
    public OrderHeader toEntity(OrderHeader entity, OrderHeaderDto from) {
        if ( from == null ) {
            return entity;
        }

        entity.setId( from.id() );
        entity.setDescription( from.description() );

        return entity;
    }
}
