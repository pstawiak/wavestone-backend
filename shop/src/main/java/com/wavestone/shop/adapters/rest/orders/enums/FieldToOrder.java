package com.wavestone.shop.adapters.rest.orders.enums;

public enum FieldToOrder {
    ID("order.id"),ORDERDATE("order.orderdate"),STATUS("order.status"),CUSTOMEREMAIL("order.customer.email"),PRODUCTNAME("p.name"),ORDER_LINE_QUANTITY("ol.quantity"),ORDER_LINE_ID("ol.id");

    private final String fieldpath;

    FieldToOrder(String fieldpath) {
        this.fieldpath = fieldpath;
    }

    public String getFieldpath() {
        return fieldpath;
    }
}
