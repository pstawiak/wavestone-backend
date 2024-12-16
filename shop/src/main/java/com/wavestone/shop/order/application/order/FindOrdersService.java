package com.wavestone.shop.order.application.order;

import com.wavestone.shop.adapters.rest.orders.OrderSearchDto;
import com.wavestone.shop.adapters.rest.orders.enums.AscendingOrDescending;
import com.wavestone.shop.adapters.rest.orders.enums.FieldToOrder;
import com.wavestone.shop.domain.order.OrderHeader;
import com.wavestone.shop.domain.order.OrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindOrdersService {

	public static final FieldToOrder DEFAULT_ORDER_FIELD = FieldToOrder.ID;
	public static final AscendingOrDescending DEFAULT_ORDER_DIRECTION = AscendingOrDescending.ASCENDING;
	public static final int DEFAULT_PAGE_SIZE = 100;
	public static final int STARTING_PAGE_NUMBER = 0;
	private final OrderHeaderRepository orderHeaderRepository;

	@Transactional(readOnly = true)
	public List<OrderItemDto> findAllOrders(OrderSearchDto orderSearchDto) {
		OrderSearchDto searchDto = Optional.ofNullable(orderSearchDto).orElseGet(OrderSearchDto::new);
		initDefaults(searchDto);
		Sort.Order order;
		if(AscendingOrDescending.DESCENDING.equals(searchDto.getOrder())){
			order = Sort.Order.desc( searchDto.getOrderField().getFieldpath());
		}else{
			order = Sort.Order.asc(searchDto.getOrderField().getFieldpath());
		}
		Sort sort = Sort.by(order );
		PageRequest pageRequest = PageRequest.of(searchDto.getPageNumber(), searchDto.getPageSize(), sort);
		return orderHeaderRepository.findByIdAndStatusAndCustomerEmailAndProductName(searchDto.getOrderId(),searchDto.getStatus(),searchDto.getCustomerEmail(),searchDto.getProductName(),pageRequest);
	}

	private static void initDefaults(OrderSearchDto searchDto) {
		searchDto.setPageNumber(searchDto.getPageNumber() == null ? STARTING_PAGE_NUMBER : searchDto.getPageNumber());
		searchDto.setPageSize(searchDto.getPageSize() == null ? DEFAULT_PAGE_SIZE : searchDto.getPageSize());
		searchDto.setOrderField(searchDto.getOrderField() == null ? DEFAULT_ORDER_FIELD : searchDto.getOrderField());
		searchDto.setOrder(searchDto.getOrder() == null ? DEFAULT_ORDER_DIRECTION : searchDto.getOrder());
	}

}
