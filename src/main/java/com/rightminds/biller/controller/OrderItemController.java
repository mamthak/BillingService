package com.rightminds.biller.controller;

import com.rightminds.biller.entity.OrderItem;
import com.rightminds.biller.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping(value = "save", method = POST, consumes = "application/json")
    public void save(@RequestBody Map request) {
        OrderItem order = OrderItem.fromMap(request);
        orderItemService.save(order);
    }

    @RequestMapping(value = "get", method = GET, params = "id", produces = "application/json")
    public OrderItem get(@RequestParam(value = "id") Integer id) {
        return orderItemService.getById(id);
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<OrderItem> getAll() {
        return orderItemService.getAll();
    }
}
