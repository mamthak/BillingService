package com.rightminds.biller.controller;

import com.rightminds.biller.entity.BillItem;
import com.rightminds.biller.model.BillItemResponse;
import com.rightminds.biller.service.BillItemService;
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
@RequestMapping("/billItem")
@CrossOrigin
public class BillItemController {

    @Autowired
    private BillItemService billItemService;

    @RequestMapping(value = "save", method = POST, consumes = "application/x-www-form-urlencoded")
    public BillItemResponse save(@RequestParam Map<String, String> request) {
        BillItem order = BillItem.fromMap(request);
        return billItemService.save(order);
    }

    @RequestMapping(value = "", method = GET, params = "id", produces = "application/json")
    public BillItem get(@PathParam(value = "id") Integer id) {
        return billItemService.getById(id);
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<BillItem> getAll() {
        return billItemService.getAll();
    }
}
