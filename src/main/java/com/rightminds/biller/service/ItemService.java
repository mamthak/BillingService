package com.rightminds.biller.service;

import com.rightminds.biller.entity.Item;
import com.rightminds.biller.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Item getById(Integer id) {
        return repository.findById(id);
    }


    public void reduceInventoryCount(Item item, int quantity) {
        item = repository.findById(item.getId());

        if (item.isInventory()) {
            Item updatedItem = item.withUpdatedQuantity(quantity);
            repository.save(updatedItem);
        }
    }

    public List<Item> getByCategoryId(Integer categoryId, boolean isInventory) {
        List<Item> items = repository.findAllActiveItemsByCategoryId(categoryId);
        if (isInventory) {
            return items.stream().filter(Item::isInventory).collect(Collectors.toList());
        }
        return items;
    }

    public void delete(Item item) {
        repository.delete(item);
    }
}
