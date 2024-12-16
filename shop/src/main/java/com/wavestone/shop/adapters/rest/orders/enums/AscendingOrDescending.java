package com.wavestone.shop.adapters.rest.orders.enums;

public enum AscendingOrDescending {
    ASCENDING("ASC"), DESCENDING("DESC");

    private final String orderDirection;

    AscendingOrDescending(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getOrderDirection() {
        return orderDirection;
    }
}
