package com.rightminds.biller.entity;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BillItemTest {

    @Test
    public void fromMapShouldReturnOrderedItem() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("quantity", "12");
        map.put("total", "120");
        map.put("discount", "0.5");
        map.put("billid", "1");
        map.put("itemid", "1");

        BillItem billItem = BillItem.fromMap(map);

        assertThat(billItem.getQuantity(), is(12));
        assertThat(billItem.getTotal(), is(new BigDecimal(120)));
        assertThat(billItem.getDiscount(), is(new BigDecimal(0.5)));
        assertThat(billItem.getBill(), is(new Bill(1)));
        assertThat(billItem.getItem(), is(new Item(1)));
    }
}