package com.rightminds.biller.entity;

import com.rightminds.biller.util.CastUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ItemTest {

    @Test
    public void fromMapShouldConvertMapToItem() throws Exception {
        HashMap map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "Coke");
        map.put("description", "Cool Drink");
        map.put("price", "1");
        map.put("isinventory", "true");
        map.put("imagepath", "/item.jpg");
        map.put("quantity", "10");
        map.put("created", "2016-09-03T20:47:12.000+0530");
        map.put("categoryid", 1);

        Item item = Item.fromMap(map);

        assertThat(item.getName(), is("Coke"));
        assertThat(item.getDescription(), is("Cool Drink"));
        assertThat(item.getPrice(), is(new BigDecimal(1)));
        assertThat(item.isInventory(), is(true));
        assertThat(item.getQuantity(), is(10));
        assertThat(item.getImagePath(), is("/item.jpg"));
        assertThat(item.getCategory(), is(new Category(1)));
        assertThat(item.getCreatedOn(), is(CastUtil.getDate("2016-09-03T20:47:12.000+0530")));
    }

}