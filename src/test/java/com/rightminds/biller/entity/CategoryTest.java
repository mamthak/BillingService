package com.rightminds.biller.entity;

import com.rightminds.biller.util.CastUtil;
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
        assertThat(category.getCreatedOn(), is(CastUtil.getDate("2016-09-03 20:47:12")));
        assertThat(category.getImagePath(), is("/category.jpg"));
    }
}