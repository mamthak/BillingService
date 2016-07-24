package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Item;
import com.rightminds.biller.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/menu")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "save", method = POST, consumes = "application/json")
    public void save(@RequestBody Map map) {
        Item item = Item.fromMap(map);
        itemService.save(item);
    }

    @RequestMapping(value = "all", method = GET, consumes = "application/json")
    public List<Item> all() {
        return itemService.getAll();
    }
}
