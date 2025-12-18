package com.springboot.orders.controller;

import com.springboot.orders.model.order;
import com.springboot.orders.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class orderController {

    private final OrderService orderService;

    public orderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{id}")
    public order createOrder(@PathVariable Long id) {
        return orderService.createOrder(id);
    }
}
