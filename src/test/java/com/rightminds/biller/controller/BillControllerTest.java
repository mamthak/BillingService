package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.Customer;
import com.rightminds.biller.service.BillService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.HashMap;

import static com.rightminds.biller.model.BillStatus.IN_PROGRESS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillControllerTest {

    @InjectMocks
    private BillController billController;

    @Mock
    private BillService billService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheOrderItem() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "order 1");
        map.put("serviceCharge", "1");
        map.put("serviceTax", "2");
        map.put("subTotal", "10");
        map.put("total", "110");
        map.put("cash", "50");
        map.put("card", "60");
        map.put("status", "COMPLETED");
        HashMap<String, String> customer = new HashMap<String, String>() {{
            put("id", "1");
        }};
        map.put("customer", customer);

        billController.save(map);

        ArgumentCaptor<Bill> argumentCaptor = ArgumentCaptor.forClass(Bill.class);
        verify(billService).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("order 1"));
    }

    @Test
    public void getShouldReturnOrderBasedOnTheIdValue() throws Exception {
        Bill bill = new Bill(new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(11), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, null);
        when(billService.getById(any())).thenReturn(bill);

        billController.get(1);

        verify(billService).getById(1);
    }

    @Test
    public void updateNameShouldUpdateNameOfTheBill() throws Exception {
        HashMap map = new HashMap<Object, Object>() {{
            put("id", "1");
            put("name", "new name");
        }};
        billController.updateName(map);

        verify(billService).updateName(1, "new name");
    }

    @Test
    public void deleteShouldDeleteTheBill() throws Exception {
        HashMap map = new HashMap<Object, Object>() {{
            put("id", "1");
        }};
        billController.delete(map);

        verify(billService).delete(1);
    }

    @Test
    public void getAllShouldReturnAllOrderItems() throws Exception {
        Bill bill = new Bill(new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(11), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, null);
        when(billService.getById(any())).thenReturn(bill);

        billController.getAll();

        verify(billService).getAll();
    }

    @Test
    public void getOngoingBillShouldReturnOngoingBills() throws Exception {
        Bill bill = new Bill(new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(11), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, null);
        when(billService.getById(any())).thenReturn(bill);

        billController.getOngoingBills();

        verify(billService).getOngoingBills();
    }

    @Test
    public void processOrderShouldConfirmTheOrder() throws Exception {
        billController.processOrder(1);

        verify(billService).processBill(1);
    }

    @Test
    public void recentBillsShouldReturnTheRecentBills() throws Exception {
        billController.recentBills();

        verify(billService).recentBills();
    }
}