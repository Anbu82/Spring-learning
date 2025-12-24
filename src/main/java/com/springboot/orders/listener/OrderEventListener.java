package com.springboot.orders.listener;

import com.springboot.orders.event.OrderCreatedEvent;
import com.springboot.orders.model.Order;
import com.springboot.orders.repository.OrderRepository;
import com.springboot.orders.service.EmailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
public class OrderEventListener {

    private final OrderRepository orderRepository;
    private final EmailService emailService;

    public OrderEventListener(OrderRepository orderRepository,
                              EmailService emailService) {
        this.orderRepository = orderRepository;
        this.emailService = emailService;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(OrderCreatedEvent event) {
        try {
            Order order = orderRepository.findById(event.getOrderId());

            emailService.sendOrderConfirmation(
                    order.getCustomerEmail(),
                    order.getId()
            );

        } catch (Exception ex) {
            System.err.println("Async email failed for order " + event.getOrderId());
            ex.printStackTrace();
        }
    }
}
