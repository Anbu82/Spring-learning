package com.springboot.orders.repository;

import com.springboot.orders.model.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderRepository {
    private final Map<Long, Order> db = new HashMap<>();

    public Order save(Order order) {
        db.put(order.getId(), order);
        return order;
    }

    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }
}
