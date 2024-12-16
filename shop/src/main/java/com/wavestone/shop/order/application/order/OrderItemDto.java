package com.wavestone.shop.order.application.order;


import com.wavestone.shop.domain.order.OrderHeaderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderItemDto(Long id, LocalDateTime orderDate, OrderHeaderStatus status, String customerEmail, Long orderLineId, Integer orderLineQuantity, String productName) {
}
