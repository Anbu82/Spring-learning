package com.springboot.orders.service;

import com.springboot.orders.event.OrderCreatedEvent;
import com.springboot.orders.model.Order;
import com.springboot.orders.repository.OrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService; // Profile-based
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository,
                        PaymentService paymentService,
                        ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.eventPublisher = eventPublisher;
    }

    public Order createOrder(Long id) {
        // 1️⃣ Create order
        Order order = new Order(id, "CREATED");
        orderRepository.save(order);

        // 2️⃣ Process payment via profile-based service
        paymentService.processPayment(id);

        // 3️⃣ Update order status
        order.setStatus("PAID");
        orderRepository.save(order);

        // 4️⃣ Publish Spring Event
        eventPublisher.publishEvent(new OrderCreatedEvent(order.getId()));
        System.out.println("Order created and event published: " + order.getId());

        return order;
    }
}
