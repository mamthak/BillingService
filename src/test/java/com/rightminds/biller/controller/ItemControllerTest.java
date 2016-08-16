package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Category;
import com.rightminds.biller.entity.Item;
import com.rightminds.biller.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheMenu() throws Exception {
        Map map = new HashMap<>();
        map.put("name", "Coke");
        map.put("description", "Cool drink");
        map.put("price", "30");

        itemController.save(map);

        ArgumentCaptor<Item> argumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemService).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("Coke"));
        assertThat(argumentCaptor.getValue().getDescription(), is("Cool drink"));
        assertThat(argumentCaptor.getValue().getPrice(), is(new BigDecimal(30)));
    }

    @Test
    public void getByIdShouldReturnItemBasedOnId() throws Exception {
        Category category = new Category("Coke", "Cool drink", "/category.jpg");
        Item item = new Item("Coke", "Cool drink", "/item.jpg", BigDecimal.ONE, category);
        when(itemService.getById(1)).thenReturn(item);

        Item actualItem = itemController.get(1);

        verify(itemService).getById(1);
        assertThat(actualItem, is(item));
    }

    @Test
    public void allShouldReturnAllMenuItems() throws Exception {
        itemController.all();

        verify(itemService).getAll();
    }

    @Test
    public void deleteShouldDeleteTheITem() throws Exception {
        Map id = new HashMap() {{
            put("id", 1);
        }};
        itemController.delete(id);

        verify(itemService).delete(Item.fromMap(id));
    }
}