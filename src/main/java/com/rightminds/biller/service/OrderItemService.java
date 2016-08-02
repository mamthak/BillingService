package com.rightminds.biller.service;

import com.rightminds.biller.entity.OrderItem;
import com.rightminds.biller.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository repository;

    public void save(OrderItem orderItem) {
        repository.save(orderItem);
    }

    public OrderItem getById(Integer id) {
        return repository.findById(id);
    }

    public List<OrderItem> getAll() {
        return (List<OrderItem>) repository.findAll();
    }
}
