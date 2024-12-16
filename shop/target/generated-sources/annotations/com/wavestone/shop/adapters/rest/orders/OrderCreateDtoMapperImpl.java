package com.wavestone.shop.adapters.rest.orders;

import com.wavestone.shop.order.application.order.OrderHeaderDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-16T01:01:41+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Homebrew)"
)
@Component
class OrderCreateDtoMapperImpl implements OrderCreateDtoMapper {

    @Override
    public OrderHeaderDto toOrderHeaderDto(OrderCreateDto from) {
        if ( from == null ) {
            return null;
        }

        String description = null;
        String customerEmail = null;

        description = from.getDescription();
        customerEmail = from.getCustomerEmail();

        Long id = null;

        OrderHeaderDto orderHeaderDto = new OrderHeaderDto( id, description, customerEmail );

        return orderHeaderDto;
    }
}
