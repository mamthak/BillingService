package com.rightminds.biller.entity;

import com.rightminds.biller.util.CastUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BillItemTest {

    @Test
    public void fromMapShouldReturnOrderedItem() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("quantity", "12");
        map.put("total", "120");
        map.put("discount", "0.5");
        map.put("billid", "1");
        map.put("created", "2016-08-20T06:34:17.000+0530");
        map.put("itemid", "1");

        BillItem billItem = BillItem.fromMap(map);

        assertThat(billItem.getId(), is(1));
        assertThat(billItem.getQuantity(), is(12));
        assertThat(billItem.getTotal(), is(new BigDecimal(120)));
        assertThat(billItem.getDiscount(), is(new BigDecimal(0.5)));
        assertThat(billItem.getCreatedOn(), is(CastUtil.getDate(map.get("created"))));
        assertThat(billItem.getBill(), is(new Bill(1)));
        assertThat(billItem.getItem(), is(new Item(1)));
    }

    @Test
    public void fromMapShouldReturnBillItemWithQuantityAsOneAndDiscountAsZeroIfItIsNotThere() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("total", "120");
        map.put("billid", "1");
        map.put("itemid", "1");

        BillItem billItem = BillItem.fromMap(map);

        assertThat(billItem.getQuantity(), is(1));
        assertThat(billItem.getTotal(), is(new BigDecimal(120)));
        assertThat(billItem.getDiscount(), is(new BigDecimal(0)));
        assertThat(billItem.getBill(), is(new Bill(1)));
        assertThat(billItem.getItem(), is(new Item(1)));
    }
}