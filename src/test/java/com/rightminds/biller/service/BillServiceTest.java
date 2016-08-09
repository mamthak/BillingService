package com.rightminds.biller.service;

import com.rightminds.biller.entity.Customer;
import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.repository.BillRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.rightminds.biller.model.BillStatus.COMPLETED;
import static com.rightminds.biller.model.BillStatus.IN_PROGRESS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNotNull;
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

    @Test
    public void getOngoingOrderShouldReturnOrdersWithStatusAsFalse() throws Exception {
        Bill firstBill = new Bill(new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS);
        Bill secondBill = new Bill(new Customer(), "Order 2", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), COMPLETED);
        when(repository.findAll()).thenReturn(Arrays.asList(firstBill, secondBill));

        List<Bill> ongoingOrders = billService.getOngoingBills();

        assertThat(ongoingOrders.size(), is(1));
        assertThat(ongoingOrders.get(0), is(firstBill));

    }
}