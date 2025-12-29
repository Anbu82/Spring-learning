package com.springboot.orders.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger log =
            LoggerFactory.getLogger(EmailService.class);

    public void sendOrderConfirmation(String email, Long orderId) {
        log.info("📧 Sending email to {} for order {}", email, orderId);

        // simulate failure (optional)
        // if (orderId == 10L) {
        //     throw new RuntimeException("Email server down");
        // }

        log.info("✅ Email sent successfully");
    }
}
