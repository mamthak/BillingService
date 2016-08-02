package com.rightminds.biller.service;

import com.rightminds.biller.entity.Order;
import com.rightminds.biller.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public void save(Order order) {
        repository.save(order);
    }

    public Order getById(Integer id) {
        return repository.findById(id);
    }

    public Order getByName(String name) {
        return repository.findByName(name);
    }

    public List<Order> getAll() {
        return (List<Order>) repository.findAll();
    }
}
