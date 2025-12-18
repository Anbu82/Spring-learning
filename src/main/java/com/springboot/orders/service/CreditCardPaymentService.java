package com.springboot.orders.service;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("card")
public class CreditCardPaymentService implements PaymentService {

    @Override
    public void processPayment(Long orderId) {
        System.out.println("Credit Card payment processed for order " + orderId);
    }
}
