package com.wavestone.shop.adapters.rest.orders;

import com.wavestone.shop.order.application.order.OrderHeaderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface OrderCreateDtoMapper {

	OrderHeaderDto toOrderHeaderDto(OrderCreateDto from);

}
