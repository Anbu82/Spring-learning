package com.springboot.orders.service;

import com.springboot.orders.model.order;
import com.springboot.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService; // ✅ correct TYPE

    public OrderService(OrderRepository orderRepository,
                        PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
    }

    public order createOrder(Long id) {
        order order = new order(id, "CREATED");
        orderRepository.save(order);

        paymentService.processPayment(id); // ambiguity will happen later

        order.setStatus("PAID");
        return order;
    }
}
