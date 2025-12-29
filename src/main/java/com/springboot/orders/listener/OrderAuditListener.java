package com.springboot.orders.listener;

import com.springboot.orders.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
public class OrderAuditListener {

    private static final Logger log =
            LoggerFactory.getLogger(OrderAuditListener.class);

    // Runs ONLY after successful transaction commit
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void audit(OrderCreatedEvent event) {
        log.info("📝 Audit log created for order {}", event.getOrderId());
    }
}
