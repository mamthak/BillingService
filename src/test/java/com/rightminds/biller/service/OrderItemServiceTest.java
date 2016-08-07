package com.rightminds.biller.service;

import com.rightminds.biller.entity.Category;
import com.rightminds.biller.entity.Item;
import com.rightminds.biller.entity.OrderItem;
import com.rightminds.biller.repository.OrderItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderItemServiceTest {

    @InjectMocks
    private OrderItemService service;

    @Mock
    private ItemService itemService;

    @Mock
    private OrderItemRepository repository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldFetchThePriceOfItemAndComputeTotalAndSave() throws Exception {
        Item itemFromMap = new Item(1, null, null, null, null, false, 0);
        Item item = new Item(1, "Coke", "Cool Drink", BigDecimal.TEN, new Category(), false, 15);
        when(itemService.getById(any())).thenReturn(item);
        OrderItem orderItemFromMap = new OrderItem(null, itemFromMap, 1, BigDecimal.ZERO, null);

        service.save(orderItemFromMap);

        ArgumentCaptor<OrderItem> captor = ArgumentCaptor.forClass(OrderItem.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTotal(), is(BigDecimal.TEN));
    }

    @Test
    public void saveShouldFetchThePriceOfItemAndComputeTotalWithDeductingDiscountAndSave() throws Exception {
        Item itemFromMap = new Item(1, null, null, null, null, false, 0);
        Item item = new Item(1, "Coke", "Cool Drink", BigDecimal.TEN, new Category(), false, 15);
        when(itemService.getById(any())).thenReturn(item);
        OrderItem orderItemFromMap = new OrderItem(null, itemFromMap, 1, new BigDecimal(2), null);

        service.save(orderItemFromMap);

        ArgumentCaptor<OrderItem> captor = ArgumentCaptor.forClass(OrderItem.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTotal(), is(new BigDecimal(8)));
    }
}