package com.springboot.orders.exception;

import com.springboot.orders.controller.OrderController;
import com.springboot.orders.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    // ✅ 1. Payment failure test
    @Test
    void paymentFailedException_shouldReturn400() throws Exception {

        Mockito.when(orderService.createOrder(Mockito.anyString()))
                .thenThrow(new PaymentFailedException("Payment failed for customer"));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerEmail": "user@fail.com"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Payment failed for customer"))
                .andExpect(jsonPath("$.path").value("/orders"));
    }

    // ✅ 2. Validation error test
    @Test
    void validationFailure_shouldReturnFieldErrors() throws Exception {

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerEmail": "invalid-email"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.customerEmail").exists());
    }
}
