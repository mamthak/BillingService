package com.rightminds.biller.service;

import com.rightminds.biller.entity.Category;
import com.rightminds.biller.entity.Item;
import com.rightminds.biller.repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository repository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheMenu() throws Exception {
        Category category = new Category("Coke", "Cool drink");
        Item item = new Item("Coke", "Cool drink", BigDecimal.ONE, category);

        itemService.save(item);

        verify(repository).save(item);
    }

    @Test
    public void getAllShouldReturnAllMenuItems() throws Exception {
        Category category = new Category("Coke", "Cool drink");
        Item item = new Item("Coke", "Cool drink", BigDecimal.ONE, category);
        when(repository.findAll()).thenReturn(Arrays.asList(item));

        List<Item> items = itemService.getAll();

        verify(repository).findAll();
        assertThat(items, is(Arrays.asList(item)));

    }
}