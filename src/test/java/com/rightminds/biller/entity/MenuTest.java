package com.rightminds.biller.entity;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class MenuTest {

    @Test
    public void fromMapShouldConvertMapToInventory() throws Exception {
        HashMap map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "Coke");
        map.put("description", "Cool Drink");
        map.put("amount", "1");
        HashMap inventory = new HashMap<>();
        inventory.put("id", "1");
        inventory.put("name", "Coke");
        inventory.put("description", "Cool Drink");
        inventory.put("quantity", "1");
        map.put("inventory", inventory);

        Menu menu = Menu.fromMap(map);

        assertThat(menu.getName(), is("Coke"));
        assertThat(menu.getDescription(), is("Cool Drink"));
        assertThat(menu.getAmount(), is(new BigDecimal(1)));
        assertThat(menu.getInventory(), is(Inventory.fromMap(inventory)));
    }

}