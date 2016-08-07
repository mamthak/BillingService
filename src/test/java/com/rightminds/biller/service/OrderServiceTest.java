package com.rightminds.biller.service;

import com.rightminds.biller.entity.Customer;
import com.rightminds.biller.entity.Order;
import com.rightminds.biller.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static com.rightminds.biller.model.OrderStatus.IN_PROGRESS;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository repository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheOrder() throws Exception {
        Order order = new Order(new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS);

        orderService.save(order);

        verify(repository).save(order);
    }

    @Test
    public void getShouldReturnOrderBasedOnTheIdValue() throws Exception {
        Order order = new Order(new Customer(), "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS);
        when(repository.findOne(any())).thenReturn(order);

        orderService.getById(1);

        verify(repository).findById(1);
    }

}