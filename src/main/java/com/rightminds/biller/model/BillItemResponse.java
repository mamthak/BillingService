package com.rightminds.biller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.BillItem;
import com.rightminds.biller.entity.Item;
import com.rightminds.biller.util.CastUtil;

import java.util.HashMap;
import java.util.Map;

import static com.rightminds.biller.util.CastUtil.formatDate;

public class BillItemResponse {

    @JsonIgnore
    private BillItem billItem;

    @JsonProperty("bill")
    private Bill bill;

    @JsonIgnore
    private Item item;

    public BillItemResponse(BillItem billItem, Item item, Bill bill) {
        this.billItem = billItem;
        this.bill = bill;
        this.item = item;
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
        if (item != null) {
            map.put("id", billItem.getId());
            map.put("billid", bill.getId());
            map.put("item", item);
            map.put("itemid", item.getId());
            map.put("itemname", item.getName());
            map.put("unitamount", item.getPrice());
            map.put("quantity", billItem.getQuantity());
            map.put("discount", billItem.getDiscount());
            map.put("total", billItem.getTotal());
            map.put("created", formatDate(billItem.getCreatedOn()));
        }
        return map;
    }
}
