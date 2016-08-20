package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/bill")
@CrossOrigin
public class BillController {

    @Autowired
    private BillService billService;

    @RequestMapping(value = "save", method = POST, consumes = "application/x-www-form-urlencoded", produces = "application/json")
    public Bill save(@RequestParam Map request) {
        Bill bill = Bill.fromMap(request);
        return billService.save(bill);
    }

    @RequestMapping(value = "", method = GET, params = "id", produces = "application/json")
    public Bill get(@PathParam(value = "id") Integer id) {
        return billService.getById(id);
    }

    @RequestMapping(value = "", method = GET, params = "name", produces = "application/json")
    public Bill getByName(@PathParam(value = "name") String name) {
        return billService.getByName(name);
    }

    @RequestMapping(value = "all", method = GET, produces = "application/json")
    public List<Bill> getAll() {
        return billService.getAll();
    }

    @RequestMapping(value = "ongoingOrders", method = GET, produces = "application/json")
    public List<Bill> getOngoingBills() {
        return billService.getOngoingBills();
    }

    @RequestMapping(value = "process", method = POST, consumes = "application/json")
    public void processOrder(HashMap<String, Object> request) {
        Bill bill = Bill.fromMap(request);
        billService.processBill(bill);
    }
}
