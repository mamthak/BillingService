package com.rightminds.biller.service;

import com.rightminds.biller.entity.Inventory;
import com.rightminds.biller.entity.Menu;
import com.rightminds.biller.repository.MenuRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository repository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheMenu() throws Exception {
        Inventory inventory = new Inventory("Coke", "Cool drink", 1);
        Menu menu = new Menu("Coke", "Cool drink", BigDecimal.ONE, inventory);

        menuService.save(menu);

        verify(repository).save(menu);
    }

    @Test
    public void getAllShouldReturnAllMenuItems() throws Exception {
        Inventory inventory = new Inventory("Coke", "Cool drink", 1);
        Menu menu = new Menu("Coke", "Cool drink", BigDecimal.ONE, inventory);
        when(repository.findAll()).thenReturn(Arrays.asList(menu));

        List<Menu> menus = menuService.getAll();

        verify(repository).findAll();
        assertThat(menus, is(Arrays.asList(menu)));

    }
}