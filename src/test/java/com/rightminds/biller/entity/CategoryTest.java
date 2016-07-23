package com.rightminds.biller.entity;

import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CategoryTest {

    @Test
    public void fromMapShouldConvertMapToInventory() throws Exception {
        HashMap map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "Coke");
        map.put("description", "Cool Drink");

        Category category = Category.fromMap(map);

        assertThat(category.getName(), is("Coke"));
        assertThat(category.getDescription(), is("Cool Drink"));
    }
}