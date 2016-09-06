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
        map.put("imagepath", "/category.jpg");
        map.put("created", "2016-09-03 20:47:12");

        Category category = Category.fromMap(map);

        assertThat(category.getName(), is("Coke"));
        assertThat(category.getDescription(), is("Cool Drink"));
        assertThat(category.getCreatedOn().getTime(), is(1472915832000L));
        assertThat(category.getImagePath(), is("/category.jpg"));
    }
}