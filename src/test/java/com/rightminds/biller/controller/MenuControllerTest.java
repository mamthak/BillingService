package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Menu;
import com.rightminds.biller.service.MenuService;
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
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class MenuControllerTest {

    @InjectMocks
    private MenuController menuController;

    @Mock
    private MenuService menuService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheMenu() throws Exception {
        Map map = new HashMap<>();
        map.put("name", "Coke");
        map.put("description", "Cool drink");
        map.put("amount", "30");

        menuController.save(map);

        ArgumentCaptor<Menu> argumentCaptor = ArgumentCaptor.forClass(Menu.class);
        verify(menuService).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("Coke"));
        assertThat(argumentCaptor.getValue().getDescription(), is("Cool drink"));
        assertThat(argumentCaptor.getValue().getAmount(), is(new BigDecimal(30)));
    }

    @Test
    public void allShouldReturnAllMenuItems() throws Exception {
        menuController.all();

        verify(menuService).getAll();
    }
}