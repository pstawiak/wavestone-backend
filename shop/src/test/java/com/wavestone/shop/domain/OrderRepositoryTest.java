package com.wavestone.shop.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import com.wavestone.shop.domain.customer.Customer;
import com.wavestone.shop.domain.order.OrderHeader;
import com.wavestone.shop.domain.order.OrderHeaderRepository;
import com.wavestone.shop.domain.order.OrderHeaderStatus;
import com.wavestone.shop.domain.order.OrderLine;
import com.wavestone.shop.domain.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;

@DataJpaTest
class OrderRepositoryTest {

	@Autowired
	private OrderHeaderRepository orderRepository;

	@Autowired
	private EntityManager em;

	@Test
	void shouldCreateOrder() {
		// given
		Customer customer = Customer.builder()
				.email("test@email.com")
				.build();
		em.persist(customer);

		Product product = Product.builder()
				.name("test product")
				.build();
		em.persist(product);

		OrderHeader order = OrderHeader.builder()
				.customer(customer)
				.description("Test order")
				.status(OrderHeaderStatus.CREATED)
				.orderDate(LocalDateTime.of(2024, 1, 1, 0, 0))
				.build();

		OrderLine line = OrderLine.builder()
				.product(product)
				.quantity(10)
				.description("Test line")
				.build();

		order.addLine(line);

		// when
		orderRepository.saveAndFlush(order);

		// then
		OrderHeader actualOrder = em.createQuery("select o from OrderHeader o where o.id = :id", OrderHeader.class)
				.setParameter("id", order.getId())
				.getSingleResult();

		assertNotNull(actualOrder);
		assertNotNull(actualOrder.getCustomer());
		assertNotNull(actualOrder.getOrderLines());
		assertEquals(1, actualOrder.getOrderLines().size());
	}

}
