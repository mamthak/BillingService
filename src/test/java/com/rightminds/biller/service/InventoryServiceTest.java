package com.rightminds.biller.service;

import com.rightminds.biller.entity.Inventory;
import com.rightminds.biller.repository.InventoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private InventoryRepository repository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheInventoryItem() throws Exception {
        Inventory inventory = new Inventory("Coke", "Cool drink", 1);

        inventoryService.save(inventory);

        verify(repository).save(inventory);
    }

    @Test
    public void getShouldReturnInventoryBasedOnTheIdValue() throws Exception {
        Inventory inventory = new Inventory("Coke", "Cool drink", 1);
        when(repository.findOne(any())).thenReturn(inventory);

        inventoryService.getById(1);

        verify(repository).findById(1);
    }

}