package com.rightminds.biller.service;

import com.rightminds.biller.entity.Inventory;
import com.rightminds.biller.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    public void save(Inventory inventory) {
        repository.save(inventory);
    }

    public Inventory getById(Integer id) {
        return repository.findById(id);
    }

    public List<Inventory> getAll() {
        return (List<Inventory>) repository.findAll();
    }
}
