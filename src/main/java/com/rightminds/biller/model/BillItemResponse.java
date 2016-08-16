package com.rightminds.biller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.BillItem;

public class BillItemResponse {

    @JsonProperty("order")
    private BillItem item;

    @JsonProperty("bill")
    private Bill bill;

    public BillItemResponse(BillItem item, Bill bill) {
        this.item = item;
        this.bill = bill;
    }

    public BillItem getItem() {
        return item;
    }

    public Bill getBill() {
        return bill;
    }
}
