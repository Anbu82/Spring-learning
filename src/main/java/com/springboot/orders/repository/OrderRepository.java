package com.springboot.orders.repository;

import com.springboot.orders.model.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepository {
    private final Map<Long, Order> db = new HashMap<>();

    public Order save(Order order) {
        db.put(order.getId(), order);
        return order;
    }

    public Order findById(Long id) {
        return db.get(id);
    }
}
