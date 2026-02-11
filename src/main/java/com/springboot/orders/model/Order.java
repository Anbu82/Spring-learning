package com.springboot.orders.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String customerEmail;

    @Version
    private Long version; //For Optimistic Locking

    protected Order() {
        // REQUIRED by JPA — do not remove
    }

    public Order(String status, String customerEmail) {
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

    public Long getVersion() {
        return version;
    }

}
