package com.rightminds.biller.service;

import com.rightminds.biller.entity.Customer;
import com.rightminds.biller.entity.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static com.rightminds.biller.model.OrderStatus.COMPLETED;
import static com.rightminds.biller.model.OrderStatus.IN_PROGRESS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillingServiceTest {


    @InjectMocks
    private BillingService billingService;

    @Mock
    private OrderService orderService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void processOrderShouldComputeTheTotalAndSaveTheOrder() throws Exception {
        Order order = new Order(new Customer(), "Order 1", new BigDecimal(5),
                new BigDecimal(2), new BigDecimal(100), new BigDecimal(0), null,
                null, null, IN_PROGRESS);

        billingService.processBill(order);

        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderService).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("Order 1"));
        assertThat(argumentCaptor.getValue().getTotal(), is(new BigDecimal(107)));
        assertThat(argumentCaptor.getValue().getStatus(), is(COMPLETED));
    }

    @Test
    public void processOrderShouldComputeTheTotalAndDeductTheDiscountSaveTheOrder() throws Exception {
        Order order = new Order(new Customer(), "Order 1", new BigDecimal(5),
                new BigDecimal(2), new BigDecimal(100), new BigDecimal(3), null,
                null, null, IN_PROGRESS);

        billingService.processBill(order);

        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderService).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("Order 1"));
        assertThat(argumentCaptor.getValue().getTotal(), is(new BigDecimal(104)));
        assertThat(argumentCaptor.getValue().getStatus(), is(COMPLETED));
    }
}
