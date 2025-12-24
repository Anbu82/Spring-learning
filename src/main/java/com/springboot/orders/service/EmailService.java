package com.springboot.orders.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendOrderConfirmation(String email, Long orderId) {
        System.out.println("📧 Sending email to " + email +
                " for order " + orderId);

        // simulate failure (optional)
        // if (orderId == 10L) {
        //     throw new RuntimeException("Email server down");
        // }

        System.out.println("✅ Email sent successfully");
    }
}
