package com.wavestone.shop.adapters.rest.orders;

import com.wavestone.shop.order.application.order.OrderLineDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderCreateDto {

	private String customerEmail;
	private String description;
	private List<OrderLineDto> orderLines;

}
