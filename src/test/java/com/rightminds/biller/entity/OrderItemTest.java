package com.rightminds.biller.entity;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderItemTest {

    @Test
    public void fromMapShouldReturnOrderedItem() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("quantity", "12");
        map.put("total", "120");
        map.put("discount", "0.5");
        HashMap<String, String> order = new HashMap<String, String>() {{
            put("id", "1");
        }};
        map.put("order", order);
        HashMap<String, String> item = new HashMap<String, String>() {{
            put("id", "2");
        }};
        map.put("item", item);

        OrderItem orderItem = OrderItem.fromMap(map);

        assertThat(orderItem.getQuantity(), is(12));
        assertThat(orderItem.getTotal(), is(new BigDecimal(120)));
        assertThat(orderItem.getDiscount(), is(new BigDecimal(0.5)));
        assertThat(orderItem.getOrder(), is(Order.fromMap(order)));
        assertThat(orderItem.getItem(), is(Item.fromMap(item)));
    }
}