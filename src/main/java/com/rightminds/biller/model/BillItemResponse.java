package com.rightminds.biller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.BillItem;

import java.util.HashMap;
import java.util.Map;

public class BillItemResponse {

    @JsonIgnore
    private BillItem billItem;

    @JsonProperty("bill")
    private Bill bill;

    public BillItemResponse(BillItem billItem, Bill bill) {
        this.billItem = billItem;
        this.bill = bill;
    }

    public BillItem getBillItem() {
        return billItem;
    }

    public Bill getBill() {
        return bill;
    }

    @JsonProperty("order")
    public Map itemMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("billItem", billItem.getItem());
        map.put("itemid", billItem.getItem().getId());
        map.put("itemname", billItem.getItem().getName());
        map.put("quantity", billItem.getQuantity());
        map.put("discount", billItem.getDiscount());
        map.put("total", billItem.getTotal());
        map.put("created", billItem.getCreatedOn());
        return map;
    }
}
