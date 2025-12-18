package com.springboot.orders.repository;

import com.springboot.orders.model.order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepository {

    private final Map<Long, order> db = new HashMap<>();

    public order save(order order) {
        db.put(order.getId(), order);
        return order;
    }

    public order findById(Long id) {
        return db.get(id);
    }
}
