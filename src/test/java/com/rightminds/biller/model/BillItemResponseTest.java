package com.rightminds.biller.model;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.BillItem;
import com.rightminds.biller.entity.Category;
import com.rightminds.biller.entity.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BillItemResponseTest {

    @Test
    public void toMapShouldReturnMap() throws Exception {
        Bill bill = new Bill(1);
        Item item = new Item(1, "Coke", "Cool Drink", "/item.jpg", BigDecimal.TEN, new Category(), false, 15, null);
        BillItem billItem = new BillItem(bill, item, 1, new BigDecimal(2), BigDecimal.TEN, null);

        BillItemResponse response = new BillItemResponse(billItem, item, bill);

        Map map = response.itemMap();
        assertThat(map.get("id"), is(billItem.getId()));
        assertThat(map.get("billid"), is(1));
        assertThat(map.get("item"), is(item));
        assertThat(map.get("itemid"), is(1));
        assertThat(map.get("itemname"), is("Coke"));
        assertThat(map.get("unitamount"), is(BigDecimal.TEN));
        assertThat(map.get("quantity"), is(1));
        assertThat(map.get("discount"), is(new BigDecimal(2)));
        assertThat(map.get("total"), is(BigDecimal.TEN));
    }

    @Test
    public void toMapShouldReturnEmptyHashMapWhenItemIsNull() throws Exception {
        Bill bill = new Bill(1);
        BillItem billItem = new BillItem(bill, null, 1, new BigDecimal(2), null, null);

        BillItemResponse response = new BillItemResponse(billItem, null, bill);

        Map map = response.itemMap();
        assertThat(map, is(new HashMap()));
    }
}