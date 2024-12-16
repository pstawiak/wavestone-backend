package com.wavestone.shop.adapters.rest.orders;

import com.wavestone.shop.order.application.order.FindOrdersService;
import com.wavestone.shop.order.application.order.ManageOrderService;
import com.wavestone.shop.order.application.order.OrderHeaderDto;
import com.wavestone.shop.order.application.order.OrderItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class OrdersController {

    private final OrderCreateDtoMapper orderCreateDtoMapper;
    private final ManageOrderService manageOrderService;
    private final FindOrdersService findOrdersService;

    @PostMapping(path = "/find-orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItemDto> findOrders(@RequestBody OrderSearchDto orderSearchDto) {
        return findOrdersService.findAllOrders(orderSearchDto);
    }

    // I'd like to use Put instead of Post to create/update things, bud i havent managed to configure @RestApi tests to verify this.
    @PostMapping(path = "/orders")
    public ResponseEntity<Long> createOrder(@RequestBody OrderCreateDto order) {
        OrderHeaderDto orderHeaderDto = orderCreateDtoMapper.toOrderHeaderDto(order);
        Long orderHeaderId = manageOrderService.storeOrder(orderHeaderDto,order.getOrderLines());

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(orderHeaderId)
                        .toUri()
        ).body(orderHeaderId);
    }

}
