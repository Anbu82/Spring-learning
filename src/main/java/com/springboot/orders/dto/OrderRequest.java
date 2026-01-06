package com.springboot.orders.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull
    @Email
    private String customerEmail;

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}


