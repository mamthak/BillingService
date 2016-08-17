package com.rightminds.biller.model;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.BillItem;
import com.rightminds.biller.entity.Category;
import com.rightminds.biller.entity.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BillItemResponseTest {

    @Test
    public void toMapShouldReturnMap() throws Exception {
        Bill bill = new Bill(1);
        Item item = new Item(1, "Coke", "Cool Drink", "/item.jpg", BigDecimal.TEN, new Category(), false, 15);
        BillItem billItem = new BillItem(bill, item, 1, new BigDecimal(2), null);

        BillItemResponse response = new BillItemResponse(billItem, bill);

        Map map = response.itemMap();
        assertThat(map.get("itemname"), is("Coke"));
        assertThat(map.get("itemid"), is(1));
    }
}