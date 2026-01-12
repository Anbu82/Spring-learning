package com.springboot.orders.Service;

import com.springboot.orders.dto.OrderResponse;
import com.springboot.orders.event.OrderCreatedEvent;
import com.springboot.orders.exception.PaymentFailedException;
import com.springboot.orders.model.Order;
import com.springboot.orders.repository.OrderRepository;
import com.springboot.orders.service.OrderService;
import com.springboot.orders.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private OrderService orderService;

    // ✅ SUCCESS CASE
    @Test
    void createOrder_success() {

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);

                    // Simulate JPA-generated ID
                    Field idField = Order.class.getDeclaredField("id");
                    idField.setAccessible(true);
                    if (idField.get(order) == null) {
                        idField.set(order, 1L);
                    }

                    return order;
                });

        OrderResponse response =
                orderService.createOrder("user@gmail.com");

        assertNotNull(response);
        assertEquals(1L, response.getOrderId());
        assertEquals("PAID", response.getStatus());
        assertEquals("user@gmail.com", response.getCustomerEmail());

        verify(orderRepository, times(2)).save(any(Order.class));
        verify(paymentService, times(1)).processPayment(1L);
        verify(eventPublisher, times(1))
                .publishEvent(any(OrderCreatedEvent.class));
    }

    // ❌ PAYMENT FAILURE CASE
    @Test
    void createOrder_paymentFailure_shouldThrowException() {

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);

                    Field idField = Order.class.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.set(order, 2L);

                    return order;
                });

        assertThrows(PaymentFailedException.class, () ->
                orderService.createOrder("test@fail.com")
        );

        verify(paymentService, never()).processPayment(anyLong());
        verify(eventPublisher, never()).publishEvent(any());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
