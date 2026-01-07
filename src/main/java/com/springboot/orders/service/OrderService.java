package com.springboot.orders.service;

import com.springboot.orders.dto.OrderResponse;
import com.springboot.orders.event.OrderCreatedEvent;
import com.springboot.orders.exception.PaymentFailedException;
import com.springboot.orders.model.Order;
import com.springboot.orders.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher eventPublisher;
    private static final Logger log =
            LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository,
                        PaymentService paymentService,
                        ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional   // <--- Add this
    public OrderResponse createOrder(String email) {
        Order order = new Order("CREATED", email);;
        orderRepository.save(order);

        // Simulate payment failure
        if (email.endsWith("@fail.com")) {
            throw new PaymentFailedException("Payment failed for customer");
        }

                paymentService.processPayment(order.getId());

        order.setStatus("PAID");
        orderRepository.save(order);

        // Publish event
        eventPublisher.publishEvent(new OrderCreatedEvent(order.getId()));
        log.info("Order created and event published: {}", order.getId());

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getCustomerEmail()
        );
    }
}
