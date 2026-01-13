package com.springboot.orders.integration;

import com.springboot.orders.repository.OrderRepository;
import com.springboot.orders.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    // Mock external dependency (payment system)
    @MockBean
    private PaymentService paymentService;

    // ✅ 1. SUCCESS CASE – End-to-End flow
    @Test
    void createOrder_success_shouldPersistOrder() throws Exception {

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerEmail": "user@gmail.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").exists())
                .andExpect(jsonPath("$.status").value("PAID"))
                .andExpect(jsonPath("$.customerEmail").value("user@gmail.com"));

        // Verify DB state
        assertThat(orderRepository.count()).isEqualTo(1);
    }

    // ❌ 2. FAILURE CASE – Transaction rollback
    @Test
    void createOrder_paymentFailure_shouldRollbackTransaction() throws Exception {

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerEmail": "user@fail.com"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // Verify rollback (no data persisted)
        assertThat(orderRepository.count()).isEqualTo(0);
    }
}
