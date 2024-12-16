package com.wavestone.shop.order.application.order;

import com.wavestone.shop.domain.customer.Customer;
import com.wavestone.shop.domain.customer.CustomerRepository;
import com.wavestone.shop.domain.order.*;
import com.wavestone.shop.domain.product.Product;
import com.wavestone.shop.domain.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManageOrderService {

	private final OrderHeaderRepository orderHeaderRepository;
	private final OrderLineRepository orderLineRepository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	private final OrderHeaderMapper orderHeaderMapper;
	private final OrderLineMapper orderLineMapper;

	@Transactional
	public Long storeOrder(OrderHeaderDto orderHeaderDto, List<OrderLineDto> orderLines) {
		OrderHeader entity = Optional.ofNullable(orderHeaderDto.id())
				.map(id -> orderHeaderRepository.findById(id)
						.orElseThrow(() -> new EntityNotFoundException("Order not found")))
				.orElseGet(OrderHeader::new);

		fillCustomer(orderHeaderDto, entity);
		processOrderLines(orderLines, entity);
		fillEntity(orderHeaderDto, entity);
		orderHeaderRepository.save(entity);

		log.info("Saved order {}", entity.getId());
		return entity.getId();
	}

	private void fillCustomer(OrderHeaderDto orderHeaderDto, OrderHeader entity) {
		Customer customerEntity = customerRepository.findByEmail(orderHeaderDto.customerEmail()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
		entity.setCustomer(customerEntity);
	}

	private void fillEntity(OrderHeaderDto orderHeaderDto, OrderHeader entity) {
		orderHeaderMapper.toEntity(entity, orderHeaderDto);
		entity.setOrderDate(LocalDateTime.now());
		entity.setStatus(OrderHeaderStatus.CREATED);
	}

	private void processOrderLines(List<OrderLineDto> orderLines, OrderHeader entity) {
		orderLines.forEach(orderLineDto -> {
			OrderLine orderLineEntity = Optional.ofNullable(orderLineDto.id()).map(id -> orderLineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("OrderLine not found"))).orElseGet(OrderLine::new);
			addProduct(orderLineDto, orderLineEntity);
			entity.addLine(orderLineMapper.toEntity(orderLineEntity,orderLineDto));
		});
	}

	private void addProduct(OrderLineDto orderLineDto, OrderLine orderLineEntity) {
		Product productEntity = productRepository.findByName(orderLineDto.productName()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
		orderLineEntity.setProduct(productEntity);
	}

}
