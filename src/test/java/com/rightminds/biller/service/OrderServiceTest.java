package com.rightminds.biller.service;

import com.rightminds.biller.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.rightminds.biller.model.BillStatus.COMPLETED;
import static com.rightminds.biller.model.BillStatus.IN_PROGRESS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderServiceTest {


    @InjectMocks
    private OrderService orderService;

    @Mock
    private BillService billService;

    @Mock
    private ItemService itemService;

    @Mock
    private ConfigurationService configurationService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void processOrderShouldComputeTheTotalAndSaveTheOrder() throws Exception {
        when(configurationService.getByKey("serviceCharge")).thenReturn(new Configuration("serviceCharge", "2"));
        when(configurationService.getByKey("serviceTax")).thenReturn(new Configuration("serviceTax", "1"));
        Bill bill = new Bill(new Customer(), "Order 1", null,
                null, new BigDecimal(100), new BigDecimal(0), null,
                null, null, IN_PROGRESS);

        orderService.processBill(bill);

        ArgumentCaptor<Bill> argumentCaptor = ArgumentCaptor.forClass(Bill.class);
        verify(billService).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("Order 1"));
        assertThat(argumentCaptor.getValue().getServiceCharge(), is(new BigDecimal(2)));
        assertTrue(argumentCaptor.getValue().getServiceTax().compareTo(new BigDecimal(1)) == 0);
        assertTrue(argumentCaptor.getValue().getTotal().compareTo(new BigDecimal(103.00)) == 0);
        assertThat(argumentCaptor.getValue().getStatus(), is(COMPLETED));
    }

    @Test
    public void processOrderShouldComputeTheTotalAndDeductTheDiscountSaveTheOrder() throws Exception {
        when(configurationService.getByKey("serviceCharge")).thenReturn(new Configuration("serviceCharge", "2"));
        when(configurationService.getByKey("serviceTax")).thenReturn(new Configuration("serviceTax", "1"));
        Bill bill = new Bill(new Customer(), "Order 1", null,
                null, new BigDecimal(100), new BigDecimal(3), null,
                null, null, IN_PROGRESS);

        orderService.processBill(bill);

        ArgumentCaptor<Bill> argumentCaptor = ArgumentCaptor.forClass(Bill.class);
        verify(billService).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("Order 1"));
        assertTrue(argumentCaptor.getValue().getTotal().compareTo(new BigDecimal(100)) == 0);
        assertThat(argumentCaptor.getValue().getStatus(), is(COMPLETED));
    }

    @Test
    public void processOrderShouldUpdateTheInventory() throws Exception {
        when(configurationService.getByKey("serviceCharge")).thenReturn(new Configuration("serviceCharge", "2"));
        when(configurationService.getByKey("serviceTax")).thenReturn(new Configuration("serviceTax", "1"));
        Bill bill = new Bill(new Customer(), "Order 1", null,
                null, new BigDecimal(100), new BigDecimal(3), null,
                null, null, IN_PROGRESS);
        Item item = new Item(1, "Coke", "Cool Drink", BigDecimal.TEN, new Category(), true, 15);
        BillItem billItem = new BillItem(bill, item, 1, BigDecimal.ZERO, null);
        bill.setBillItems(Arrays.asList(billItem));

        orderService.processBill(bill);

        ArgumentCaptor<Item> argumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemService).reduceInventoryCount(argumentCaptor.capture(), eq(1));
        assertThat(argumentCaptor.getValue(), is(item));
    }
}
