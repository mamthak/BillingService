package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Customer;
import com.rightminds.biller.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheInventoryItem() throws Exception {
        Map<String, String> map = new HashMap() {{
            put("name", "Thiru");
            put("phoneNumber", "9876543210");
            put("address", "perundurai");
        }};

        customerController.save(map);

        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("Thiru"));
        assertThat(argumentCaptor.getValue().getPhoneNumber(), is("9876543210"));
        assertThat(argumentCaptor.getValue().getAddress(), is("perundurai"));
    }

    @Test
    public void getShouldReturnInventoryBasedOnTheIdValue() throws Exception {
        Customer customer = new Customer("Thiru", "9876543210", "Perundurai");
        when(customerService.getById(any())).thenReturn(customer);

        customerController.get(1);

        verify(customerService).getById(1);
    }

    @Test
    public void getAllShouldReturnAllInventoryItems() throws Exception {
        Customer customer = new Customer("Thiru", "9876543210", "Perundurai");
        when(customerService.getById(any())).thenReturn(customer);

        customerController.getAll();

        verify(customerService).getAll();
    }
}