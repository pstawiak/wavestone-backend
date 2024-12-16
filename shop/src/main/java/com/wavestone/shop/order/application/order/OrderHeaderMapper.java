package com.wavestone.shop.order.application.order;

import com.wavestone.shop.domain.order.OrderHeader;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderHeaderMapper {

	OrderHeader toEntity(@MappingTarget OrderHeader entity, OrderHeaderDto from);

}
