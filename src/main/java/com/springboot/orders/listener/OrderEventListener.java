package com.springboot.orders.listener;

import com.springboot.orders.event.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @Async  // Run asynchronously
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Async Event received for orderId: " + event.getOrderId());
        // Additional tasks: email, inventory, logging
    }
}
