package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Order;
import com.rightminds.biller.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "save", method = POST, consumes = "application/json")
    public void save(@RequestBody Map request) {
        Order order = Order.fromMap(request);
        orderService.save(order);
    }

    @RequestMapping(value = "", method = GET, params = "id", produces = "application/json")
    public Order get(@PathParam(value = "id") Integer id) {
        return orderService.getById(id);
    }

    @RequestMapping(value = "", method = GET, params = "name", produces = "application/json")
    public Order getByName(@PathParam(value = "name") String name) {
        return orderService.getByName(name);
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<Order> getAll() {
        return orderService.getAll();
    }
}
