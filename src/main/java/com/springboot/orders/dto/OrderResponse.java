package com.springboot.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrderResponse {

    @Schema(
            description = "Unique identifier of the order",
            example = "1"
    )
    private Long orderId;

    @Schema(
            description = "Current status of the order",
            example = "PAID"
    )
    private String status;

    @Schema(
            description = "Customer email associated with the order",
            example = "user@gmail.com"
    )
    private String customerEmail;

    public OrderResponse(Long orderId, String status, String customerEmail) {
        this.orderId = orderId;
        this.status = status;
        this.customerEmail = customerEmail;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}
