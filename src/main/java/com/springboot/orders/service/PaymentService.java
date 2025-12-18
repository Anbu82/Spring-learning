package com.springboot.orders.service;

public interface PaymentService {
    void processPayment(Long orderId);
}
