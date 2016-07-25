package com.rightminds.biller.service;

import com.rightminds.biller.entity.Category;
import com.rightminds.biller.entity.Customer;
import com.rightminds.biller.repository.CategoryRepository;
import com.rightminds.biller.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository repository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheInventoryItem() throws Exception {
        Customer customer = new Customer("Thiru", "9876543210", "Perundurai");

        customerService.save(customer);

        verify(repository).save(customer);
    }

    @Test
    public void getShouldReturnInventoryBasedOnTheIdValue() throws Exception {
        Customer customer = new Customer("Thiru", "9876543210", "Perundurai");
        when(repository.findOne(any())).thenReturn(customer);

        customerService.getById(1);

        verify(repository).findById(1);
    }

}