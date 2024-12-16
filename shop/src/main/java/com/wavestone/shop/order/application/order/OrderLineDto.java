package com.wavestone.shop.order.application.order;


public record OrderLineDto(Long id, String description, Integer quantity, String productName) {
}
