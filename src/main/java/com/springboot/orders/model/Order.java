package com.springboot.orders.model;

public class Order {

    private Long id;
    private String status;
    private String customerEmail;

    public Order(Long id, String status, String customerEmail) {
        this.id = id;
        this.status = status;
        this.customerEmail = customerEmail;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
