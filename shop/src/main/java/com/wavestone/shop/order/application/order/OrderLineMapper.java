package com.wavestone.shop.order.application.order;

import com.wavestone.shop.domain.order.OrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {

	OrderLine toEntity(@MappingTarget OrderLine entity, OrderLineDto from);

}
