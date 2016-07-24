package com.rightminds.biller.service;

import com.rightminds.biller.entity.Item;
import com.rightminds.biller.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public void save(Item item) {
        repository.save(item);
    }

    public List<Item> getAll() {
        return (List<Item>) repository.findAll();
    }
}
