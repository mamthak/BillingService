package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Inventory;
import com.rightminds.biller.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(value = "save", method = POST, consumes = "application/json")
    public void save(@RequestBody Map request) {
        Inventory inventory = Inventory.fromMap(request);
        inventoryService.save(inventory);
    }

    @RequestMapping(value = "{id}", method = GET, produces = "application/json")
    public Inventory get(@PathVariable Integer id) {
        return inventoryService.getById(id);
    }

    @RequestMapping(value = "all", method = GET, produces = "application/json")
    public List<Inventory> getAll() {
        return inventoryService.getAll();
    }
}
