package com.springboot.orders.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("upi")
public class UpiPaymentService implements PaymentService {
    @Override
    public void processPayment(Long orderId) {
        System.out.println("UPI payment processed for order " + orderId);
    }
}
