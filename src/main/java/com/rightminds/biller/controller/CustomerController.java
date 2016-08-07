package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Customer;
import com.rightminds.biller.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "save", method = POST, consumes = "application/json")
    public void save(@RequestBody Map request) {
        Customer customer = Customer.fromMap(request);
        customerService.save(customer);
    }

    @RequestMapping(value = "", method = GET, params = "id", produces = "application/json")
    public Customer get(@PathParam(value = "id") Integer id) {
        return customerService.getById(id);
    }

    @RequestMapping(value = "", method = GET, params = "name", produces = "application/json")
    public Customer getByName(@PathParam(value = "name") String name) {
        return customerService.getByName(name);
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<Customer> getAll() {
        return customerService.getAll();
    }
}
