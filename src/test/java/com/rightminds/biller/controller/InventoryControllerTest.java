package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Inventory;
import com.rightminds.biller.service.InventoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class InventoryControllerTest {

    @InjectMocks
    private InventoryController inventoryController;

    @Mock
    private InventoryService inventoryService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheInventoryItem() throws Exception {
        HashMap map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "Coke");
        map.put("description", "Cool Drink");
        map.put("quantity", "1");

        Inventory inventory = new Inventory("Coke", "Cool drink", 1);

        inventoryController.save(map);

        ArgumentCaptor<Inventory> argumentCaptor = ArgumentCaptor.forClass(Inventory.class);
        verify(inventoryService).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("Coke"));
        assertThat(argumentCaptor.getValue().getDescription(), is("Cool Drink"));
        assertThat(argumentCaptor.getValue().getQuantity(), is(1));
    }

    @Test
    public void getShouldReturnInventoryBasedOnTheIdValue() throws Exception {
        Inventory inventory = new Inventory("Coke", "Cool drink", 1);
        when(inventoryService.getById(any())).thenReturn(inventory);

        inventoryController.get(1);

        verify(inventoryService).getById(1);
    }
}