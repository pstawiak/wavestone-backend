package com.wavestone.shop.adapters.rest.orders;

import com.wavestone.shop.adapters.rest.orders.enums.AscendingOrDescending;
import com.wavestone.shop.adapters.rest.orders.enums.FieldToOrder;
import com.wavestone.shop.domain.order.OrderHeaderStatus;
import com.wavestone.shop.order.application.order.OrderHeaderDto;
import com.wavestone.shop.order.application.order.OrderLineDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderSearchDto {

	private Long orderId;
	private String customerEmail;
	private OrderHeaderStatus status;
	private String productName;
	private Integer pageNumber;
	private Integer pageSize;
	private FieldToOrder orderField;
	private AscendingOrDescending order;

}
