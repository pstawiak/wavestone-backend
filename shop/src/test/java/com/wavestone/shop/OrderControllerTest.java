package com.wavestone.shop;

import com.wavestone.shop.adapters.rest.orders.OrderCreateDto;
import com.wavestone.shop.adapters.rest.orders.OrderSearchDto;
import com.wavestone.shop.adapters.rest.orders.OrdersController;
import com.wavestone.shop.adapters.rest.orders.enums.AscendingOrDescending;
import com.wavestone.shop.adapters.rest.orders.enums.FieldToOrder;
import com.wavestone.shop.domain.customer.Customer;
import com.wavestone.shop.domain.customer.CustomerRepository;
import com.wavestone.shop.domain.order.OrderHeader;
import com.wavestone.shop.domain.order.OrderHeaderStatus;
import com.wavestone.shop.domain.order.OrderLine;
import com.wavestone.shop.domain.product.Product;
import com.wavestone.shop.domain.product.ProductRepository;
import com.wavestone.shop.order.application.order.OrderItemDto;
import com.wavestone.shop.order.application.order.OrderLineDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerTest {

	@Autowired
	private OrdersController ordersController;

	@Autowired
	private EntityManager em;

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ProductRepository productRepository;



	@Test()
	@Order(1)
	void shouldCreateOrder() {
		// given
		Customer customer = Customer.builder()
				.email("test@email.com")
				.build();
		customerRepository.save(customer);

		Product product = Product.builder()
				.name("test product")
				.build();

		Product product2 = Product.builder()
				.name("test product2")
				.build();
		productRepository.save(product);
		productRepository.save(product2);

		OrderCreateDto orderCreateDto = generateOrderItem();
		ordersController.createOrder(orderCreateDto);

		// then
		OrderHeader actualOrder = em.createQuery("select o from OrderHeader o where o.id = :id", OrderHeader.class)
				.setParameter("id", 1L)
				.getSingleResult();

		assertNotNull(actualOrder);
		assertNotNull(actualOrder.getCustomer());
		assertNotNull(actualOrder.getOrderLines());
		assertThat(actualOrder.getDescription()).isEqualTo("testDescription");

		OrderLine actualOrderLine = em.createQuery("select o from OrderLine o where o.id = :id", OrderLine.class)
				.setParameter("id", 1L)
				.getSingleResult();
		assertThat(actualOrderLine).isNotNull();
		assertThat(actualOrderLine.getQuantity()).isEqualTo(5);
		assertThat(actualOrderLine.getDescription()).isEqualTo("testOrderLine1");

		OrderLine actualOrderLine2 = em.createQuery("select o from OrderLine o where o.id = :id", OrderLine.class)
				.setParameter("id", 2L)
				.getSingleResult();
		assertThat(actualOrderLine2).isNotNull();
		assertThat(actualOrderLine2.getQuantity()).isEqualTo(10);
		assertThat(actualOrderLine2.getDescription()).isEqualTo("testOrderLine2");


	}

	@Test
	@Order(2)
	void shouldFindAllOrders() {
		List<OrderItemDto> orders = ordersController.findOrders(null);
		assertOneOrderTwoOrderLines(orders);
	}

	private static void assertOneOrderTwoOrderLines(List<OrderItemDto> orders) {
		assertThat(orders.size()).isEqualTo(2);
		OrderItemDto firstItem = orders.get(0);
		assertThat(firstItem).isNotNull();
		assertThat(firstItem.id()).isEqualTo(1L);
		assertThat(firstItem.customerEmail()).isEqualTo("test@email.com");
		assertThat(firstItem.status()).isEqualTo(OrderHeaderStatus.CREATED);
		assertThat(firstItem.orderDate()).isBefore(LocalDateTime.now());
		assertThat(firstItem.productName()).isEqualTo("test product");
		assertThat(firstItem.orderLineQuantity()).isEqualTo(5);
		assertThat(firstItem.orderLineId()).isEqualTo(1L);

		OrderItemDto secondItem = orders.get(1);
		assertThat(secondItem).isNotNull();
		assertThat(secondItem.id()).isEqualTo(1L);
		assertThat(secondItem.customerEmail()).isEqualTo("test@email.com");
		assertThat(secondItem.status()).isEqualTo(OrderHeaderStatus.CREATED);
		assertThat(secondItem.orderDate()).isBefore(LocalDateTime.now());
		assertThat(secondItem.productName()).isEqualTo("test product2");
		assertThat(secondItem.orderLineQuantity()).isEqualTo(10);
		assertThat(secondItem.orderLineId()).isEqualTo(2L);
	}

	@Test
	@Order(2)
	void shouldFindAllOrdersWithEmptyDto() {
		List<OrderItemDto> orders = ordersController.findOrders(new OrderSearchDto());
		assertOneOrderTwoOrderLines(orders);
	}
	@Test
	@Order(2)
	void shouldFindAllOrdersWithId() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setOrderId(1L);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertOneOrderTwoOrderLines(orders);
	}
	@Test
	@Order(2)
	void shouldFindNoOrdersWithWrongId() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setOrderId(666L);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(0);
	}
	@Test
	@Order(2)
	void shouldFindNoOrdersWithStatusInDelievery() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setStatus(OrderHeaderStatus.IN_DELIVERY);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(0);
	}
	@Test
	@Order(2)
	void shouldFindOrdersWithStatusInitial() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setStatus(OrderHeaderStatus.CREATED);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertOneOrderTwoOrderLines(orders);
	}
	@Test
	@Order(2)
	void shouldFindOrdersWithCustomerEmail() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setCustomerEmail("test@email.com");
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertOneOrderTwoOrderLines(orders);
	}
	@Test
	@Order(2)
	void shouldFindNoOrdersWithCustomerEmailWrong() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setCustomerEmail("wrongtest@email.com");
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(0);
	}
	@Test
	@Order(2)
	void shouldFindOrdersWithProductName1() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setProductName("test product");
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertOneOrderTwoOrderLines(orders);
	}
	@Test
	@Order(2)
	void shouldFindOrdersWithProductName2() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setProductName("test product2");
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertOneOrderTwoOrderLines(orders);
	}

	@Test
	@Order(2)
	void shouldFindNoOrdersWithProductName666() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setProductName("test product666");
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(0);
	}
	@Test
	@Order(3)
	void shouldTestPagination() {
		//given
		for (int i = 0; i < 10; i++) {
			OrderCreateDto orderCreateDto = generateOrderItem();
			ordersController.createOrder(orderCreateDto);
		}

		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setPageSize(3);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(3);
	}


	@Test
	@Order(4)
	void shouldTestPaginationWith4page2nd() {

		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setPageNumber(1);
		orderSearchDto.setPageSize(4);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(4);
	}
	@Test
	@Order(4)
	void shouldTestPaginationWith20() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setPageNumber(0);
		orderSearchDto.setPageSize(20);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(20);
	}

	@Test
	@Order(5)
	void shouldFilterWithComplexFilter() {

		Customer customer = Customer.builder()
				.email("test2@email.com")
				.build();
		customerRepository.save(customer);


		Product product = Product.builder()
				.name("test product4")
				.build();

		productRepository.save(product);
		for (int i = 0; i < 20; i++) {
			OrderCreateDto orderCreateDto = generateOtherOrderItem();
			ordersController.createOrder(orderCreateDto);
		}
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(62);
	}
	@Test
	@Order(6)
	void shouldFilterWithComplexFilterCustomer2() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setCustomerEmail("test2@email.com");
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(40);
	}
	@Test
	@Order(6)
	void shouldFilterWithComplexFilterCustomer1() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setCustomerEmail("test@email.com");
		orderSearchDto.setProductName("test product2");
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(22);
	}
	@Test
	@Order(6)
	void shouldFilterWithComplexFilterProduct2() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setProductName("test product2");
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(62);
	}
	@Test
	@Order(6)
	void shouldSortByIdDescending() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setOrderField(FieldToOrder.ID);
		orderSearchDto.setOrder(AscendingOrDescending.DESCENDING);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(62);
		assertThat(orders.get(0).id()).isEqualTo(31);
	}

	@Test
	@Order(6)
	void shouldSortByOrderLineQuantityDescending() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setOrderField(FieldToOrder.PRODUCTNAME);
		orderSearchDto.setOrder(AscendingOrDescending.DESCENDING);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(62);
		assertThat(orders.get(0).orderLineQuantity()).isEqualTo(15);
	}
	@Test
	@Order(6)
	void shouldSortByProductNameDescending() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setOrderField(FieldToOrder.PRODUCTNAME);
		orderSearchDto.setOrder(AscendingOrDescending.DESCENDING);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(62);
		assertThat(orders.get(0).productName()).isEqualTo("test product4");
	}

	@Test
	@Order(6)
	@Disabled
	void shouldSortByCustomerEmailDescending() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setOrderField(FieldToOrder.CUSTOMEREMAIL);
		orderSearchDto.setOrder(AscendingOrDescending.DESCENDING);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(62);
		assertThat(orders.get(0).customerEmail()).isEqualTo("test2@email.com");
	}
	@Test
	@Order(6)
	void shouldSortByOrderLineIdDescending() {
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setOrderField(FieldToOrder.ORDER_LINE_ID);
		orderSearchDto.setOrder(AscendingOrDescending.DESCENDING);
		List<OrderItemDto> orders = ordersController.findOrders(orderSearchDto);
		assertThat(orders.size()).isEqualTo(62);
		assertThat(orders.get(0).orderLineId()).isEqualTo(62L);
	}

	private static OrderCreateDto generateOrderItem() {
		OrderCreateDto orderCreateDto = new OrderCreateDto();
		orderCreateDto.setCustomerEmail("test@email.com");
		orderCreateDto.setDescription("testDescription");
		ArrayList<OrderLineDto> orderLines = new ArrayList<>();
		orderLines.add(new OrderLineDto(null,"testOrderLine1",5, "test product"));
		orderLines.add(new OrderLineDto(null,"testOrderLine2",10,"test product2"));
		orderCreateDto.setOrderLines(orderLines);
		return orderCreateDto;
	}
	private static OrderCreateDto generateOtherOrderItem() {
		OrderCreateDto orderCreateDto = new OrderCreateDto();
		orderCreateDto.setCustomerEmail("test2@email.com");
		orderCreateDto.setDescription("testDescription2");
		ArrayList<OrderLineDto> orderLines = new ArrayList<>();
		orderLines.add(new OrderLineDto(null,"testOrderLine2",5, "test product2"));
		orderLines.add(new OrderLineDto(null,"testOrderLine4",15,"test product4"));
		orderCreateDto.setOrderLines(orderLines);
		return orderCreateDto;
	}
}
