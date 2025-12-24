package com.springboot.orders.controller;

import com.springboot.orders.dto.OrderRequest;
import com.springboot.orders.model.Order;
import com.springboot.orders.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(
                request.getOrderId(),
                request.getCustomerEmail()
        );
    }
}
