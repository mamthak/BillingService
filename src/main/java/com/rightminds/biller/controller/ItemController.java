package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Item;
import com.rightminds.biller.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/item")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "save", method = POST, consumes = "application/x-www-form-urlencoded")
    public void save(@RequestParam Map map) {
        Item item = Item.fromMap(map);
        itemService.save(item);
    }

    @RequestMapping(value = "all", method = GET, produces = "application/json")
    public List<Item> all() {
        return itemService.getAll();
    }

    @RequestMapping(value = "", method = GET, params = "id", produces = "application/json")
    public Item get(@PathParam(value = "id") Integer id) {
        return itemService.getById(id);
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<Item> getByCategory(@RequestParam(value = "categoryid") Integer id, @RequestParam(value = "isinventory") boolean isInventory) {
        return itemService.getByCategoryId(id, isInventory);
    }


}
