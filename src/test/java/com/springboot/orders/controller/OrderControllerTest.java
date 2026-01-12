package com.springboot.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.orders.dto.OrderRequest;
import com.springboot.orders.dto.OrderResponse;
import com.springboot.orders.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ 1. SUCCESS CASE
    @Test
    void createOrder_success() throws Exception {

        OrderResponse response =
                new OrderResponse(1L, "PAID", "user@gmail.com");

        Mockito.when(orderService.createOrder("user@gmail.com"))
                .thenReturn(response);

        OrderRequest request = new OrderRequest();
        request.setCustomerEmail("user@gmail.com");

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.status").value("PAID"))
                .andExpect(jsonPath("$.customerEmail").value("user@gmail.com"));
    }

    // ❌ 2. VALIDATION FAILURE
    @Test
    void createOrder_invalidEmail_shouldReturn400() throws Exception {

        OrderRequest request = new OrderRequest();
        request.setCustomerEmail("invalid-email");

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
