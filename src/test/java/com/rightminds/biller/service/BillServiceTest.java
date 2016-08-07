package com.rightminds.biller.service;

import com.rightminds.biller.entity.Customer;
import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.repository.BillRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static com.rightminds.biller.model.BillStatus.IN_PROGRESS;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillServiceTest {

    @InjectMocks
    private BillService billService;

    @Mock
    private BillRepository repository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheOrder() throws Exception {
        Bill bill = new Bill(new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS);

        billService.save(bill);

        verify(repository).save(bill);
    }

    @Test
    public void getShouldReturnOrderBasedOnTheIdValue() throws Exception {
        Bill bill = new Bill(new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS);
        when(repository.findOne(any())).thenReturn(bill);

        billService.getById(1);

        verify(repository).findById(1);
    }

}