package com.wavestone.shop.domain.order;

import com.wavestone.shop.adapters.rest.orders.enums.AscendingOrDescending;
import com.wavestone.shop.adapters.rest.orders.enums.FieldToOrder;
import com.wavestone.shop.order.application.order.OrderItemDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {

    @Query("SELECT new com.wavestone.shop.order.application.order.OrderItemDto(order.id,order.orderDate,order.status,order.customer.email,ol.id,ol.quantity,p.name) " +
            "FROM OrderHeader order JOIN OrderLine ol on ol.order.id = order.id JOIN Product p on p.id = ol.product.id " +
            "WHERE (:id IS NULL OR order.id = :id) " +
            "and (:status IS NULL OR order.status = :status) " +
            "and (:customerEmail IS NULL OR order.customer.email = :customerEmail) " +
            "and (:productName IS NULL OR EXISTS (SELECT ol FROM OrderLine ol WHERE ol.product.name = :productName) ) ")
    List<OrderItemDto> findByIdAndStatusAndCustomerEmailAndProductName(Long id, OrderHeaderStatus status, String customerEmail, String productName, Pageable pageable);

}
