package com.rightminds.biller.service;

import com.rightminds.biller.entity.*;
import com.rightminds.biller.repository.BillRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.rightminds.biller.model.BillStatus.COMPLETED;
import static com.rightminds.biller.model.BillStatus.IN_PROGRESS;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillServiceTest {

    @InjectMocks
    private BillService billService;

    @Mock
    private BillRepository repository;

    @Mock
    private ConfigurationService configurationService;

    @Mock
    private ItemService itemService;

    @Mock
    private ElasticSearchService elasticSearchService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheOrder() throws Exception {
        Bill bill = new Bill(new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, new ArrayList());
        Bill savedBill = new Bill(1, new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, new ArrayList());
        when(repository.findById(any())).thenReturn(bill);
        Mockito.when(repository.save(bill)).thenReturn(savedBill);

        billService.save(bill);

        verify(repository).save(bill);
    }

    @Test
    public void getShouldReturnOrderBasedOnTheIdValue() throws Exception {
        Bill bill = new Bill(new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, new ArrayList());
        when(repository.findOne(any())).thenReturn(bill);

        billService.getById(1);

        verify(repository).findById(1);
    }

    @Test
    public void findAllShouldReturnBillWithTransientData() throws Exception {
        Bill bill = new Bill(1, new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, new ArrayList<>());
        Item item = new Item(1, "Coke", "Cool drink", "/item.jpg", BigDecimal.ONE, new Category(), true, 10);
        BillItem billItem = new BillItem(bill, item, 1, BigDecimal.ZERO, BigDecimal.ONE, null);
        billItem.setItemId(null);
        billItem.setItemName(null);
        bill.getBillItems().add(billItem);
        when(repository.findAll()).thenReturn(Arrays.asList(bill));

        List<Bill> bills = billService.getAll();

        assertThat(bills.get(0).getBillItems().get(0).getItemId(), is(1));
        assertThat(bills.get(0).getBillItems().get(0).getItemName(), is("Coke"));
        assertThat(bills.get(0).getBillItems().get(0).getUnitAmount(), is(BigDecimal.ONE));
        assertThat(bills.get(0).getBillItems().get(0).getBillId(), is(1));
    }

    @Test
    public void getOngoingOrderShouldReturnOrdersWithStatusAsFalse() throws Exception {
        Bill firstBill = new Bill(new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, new ArrayList());
        Bill secondBill = new Bill(new Customer(), "Order 2", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), COMPLETED, null, new ArrayList());
        when(repository.findAll()).thenReturn(Arrays.asList(firstBill, secondBill));

        List<Bill> ongoingOrders = billService.getOngoingBills();

        assertThat(ongoingOrders.size(), is(1));
        assertThat(ongoingOrders.get(0), is(firstBill));

    }

    @Test
    public void processOrderShouldComputeTheTotalAndSaveTheOrder() throws Exception {
        when(configurationService.getByKey("servicecharge")).thenReturn(new Configuration("servicecharge", "2"));
        when(configurationService.getByKey("servicetax")).thenReturn(new Configuration("servicetax", "1"));
        Bill bill = new Bill(new Customer(), "Order 1", null,
                null, new BigDecimal(100), new BigDecimal(0), null,
                null, null, IN_PROGRESS, null, new ArrayList<>());
        when(repository.findById(any())).thenReturn(bill);
        Mockito.when(repository.save(any(Bill.class))).thenReturn(bill);

        billService.processBill(bill);

        ArgumentCaptor<Bill> argumentCaptor = ArgumentCaptor.forClass(Bill.class);
        verify(repository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("Order 1"));
        assertThat(argumentCaptor.getValue().getServiceCharge(), is(new BigDecimal(2)));
        assertTrue(argumentCaptor.getValue().getServiceTax().compareTo(new BigDecimal(1)) == 0);
        assertTrue(argumentCaptor.getValue().getTotal().compareTo(new BigDecimal(103.00)) == 0);
        assertThat(argumentCaptor.getValue().getStatus(), is(COMPLETED));
    }

    @Test
    public void processOrderShouldComputeTheTotalAndDeductTheDiscountSaveTheOrder() throws Exception {
        when(configurationService.getByKey("servicecharge")).thenReturn(new Configuration("servicecharge", "2"));
        when(configurationService.getByKey("servicetax")).thenReturn(new Configuration("servicetax", "1"));
        Bill bill = new Bill(new Customer(), "Order 1", null,
                null, new BigDecimal(100), new BigDecimal(3), null,
                null, null, IN_PROGRESS, null, new ArrayList<>());
        when(repository.findById(any())).thenReturn(bill);
        Mockito.when(repository.save(any(Bill.class))).thenReturn(bill);

        billService.processBill(bill);

        ArgumentCaptor<Bill> argumentCaptor = ArgumentCaptor.forClass(Bill.class);
        verify(repository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("Order 1"));
        assertTrue(argumentCaptor.getValue().getTotal().compareTo(new BigDecimal(100)) == 0);
        assertThat(argumentCaptor.getValue().getStatus(), is(COMPLETED));
    }

    @Test
    public void processOrderShouldUpdateTheInventory() throws Exception {
        when(configurationService.getByKey("servicecharge")).thenReturn(new Configuration("servicecharge", "2"));
        when(configurationService.getByKey("servicetax")).thenReturn(new Configuration("servicetax", "1"));
        Bill bill = new Bill(new Customer(), "Order 1", null,
                null, new BigDecimal(100), new BigDecimal(3), null,
                null, null, IN_PROGRESS, null, null);
        Item item = new Item(1, "Coke", "Cool Drink", "/item.jpg", BigDecimal.TEN, new Category(), true, 15);
        BillItem billItem = new BillItem(bill, item, 1, BigDecimal.ZERO, null, null);
        bill.setBillItems(Arrays.asList(billItem));
        when(repository.findById(any())).thenReturn(bill);
        Mockito.when(repository.save(any(Bill.class))).thenReturn(bill);

        billService.processBill(bill);

        ArgumentCaptor<Item> argumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemService).reduceInventoryCount(argumentCaptor.capture(), eq(1));
        assertThat(argumentCaptor.getValue(), is(item));
    }


}