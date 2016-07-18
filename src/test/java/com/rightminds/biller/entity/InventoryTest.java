package com.rightminds.biller.entity;

import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class InventoryTest {

    @Test
    public void fromMapShouldConvertMapToInventory() throws Exception {
        HashMap map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "Coke");
        map.put("description", "Cool Drink");
        map.put("quantity", "1");

        Inventory inventory = Inventory.fromMap(map);

        assertThat(inventory.getName(), is("Coke"));
        assertThat(inventory.getDescription(), is("Cool Drink"));
        assertThat(inventory.getQuantity(), is(1));
    }
}