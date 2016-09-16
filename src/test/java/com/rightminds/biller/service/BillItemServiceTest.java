package com.rightminds.biller.service;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.BillItem;
import com.rightminds.biller.entity.Category;
import com.rightminds.biller.entity.Item;
import com.rightminds.biller.model.BillItemResponse;
import com.rightminds.biller.repository.BillItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillItemServiceTest {

    @InjectMocks
    private BillItemService service;

    @Mock
    private ItemService itemService;

    @Mock
    private BillService billService;

    @Mock
    private BillItemRepository repository;

    @Mock
    private ElasticSearchService elasticSearchService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldFetchThePriceOfItemAndComputeTotalAndSave() throws Exception {
        Item itemFromMap = new Item(1, null, null, null, null, null, false, 0, null);
        Item item = new Item(1, "Coke", "Cool Drink", "/item.jpg", BigDecimal.TEN, new Category(), false, 15, null);
        when(itemService.getById(any())).thenReturn(item);
        Bill bill = new Bill(1);
        when(billService.getById(any())).thenReturn(bill);
        BillItem billItemFromMap = new BillItem(bill, itemFromMap, 1, BigDecimal.ZERO, null, null);
        Mockito.when(repository.save(any(BillItem.class))).thenReturn(billItemFromMap);

        service.save(billItemFromMap);

        ArgumentCaptor<BillItem> captor = ArgumentCaptor.forClass(BillItem.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTotal(), is(BigDecimal.TEN));
    }

    @Test
    public void saveShouldFetchThePriceOfItemAndComputeTotalWithDeductingDiscountAndSave() throws Exception {
        Item itemFromMap = new Item(1, null, null, null, null, null, false, 0, null);
        Item item = new Item(1, "Coke", "Cool Drink", "/item.jpg", BigDecimal.TEN, new Category(), false, 15, null);
        Bill bill = new Bill(1);
        when(itemService.getById(any())).thenReturn(item);
        when(billService.getById(any())).thenReturn(bill);
        BillItem billItemFromMap = new BillItem(bill, itemFromMap, 1, new BigDecimal(2), null, null);
        Mockito.when(repository.save(any(BillItem.class))).thenReturn(billItemFromMap);

        BillItemResponse response = service.save(billItemFromMap);

        ArgumentCaptor<BillItem> captor = ArgumentCaptor.forClass(BillItem.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTotal(), is(new BigDecimal(8)));
        assertThat(response.getBillItem(), is(billItemFromMap));
        assertThat(response.getBill(), is(bill));
    }

    @Test
    public void saveShouldDeleteTheItemWhenTheQuantityIsZero() throws Exception {
        Item itemFromMap = new Item(1, null, null, null, null, null, false, 0, null);
        Item item = new Item(1, "Coke", "Cool Drink", "/item.jpg", BigDecimal.TEN, new Category(), false, 15, null);
        Bill bill = new Bill(1);
        when(itemService.getById(any())).thenReturn(item);
        when(billService.getById(any())).thenReturn(bill);
        BillItem billItemFromMap = new BillItem(1, bill, itemFromMap, 0, new BigDecimal(2), null, null, null);
        when(repository.findById(any())).thenReturn(billItemFromMap);

        BillItemResponse response = service.save(billItemFromMap);

        verify(repository).findById(1);
        verify(repository).delete(billItemFromMap);
        verify(billService).getById(1);
        assertThat(response.itemMap(), is(new HashMap()));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void saveShouldStoreWithDeltaInElasticSearch() throws Exception {
        Item itemFromMap = new Item(1, null, null, null, null, null, false, 0, null);
        Item item = new Item(1, "Coke", "Cool Drink", "/item.jpg", BigDecimal.TEN, new Category(), false, 15, null);
        Bill bill = new Bill(1);
        when(itemService.getById(any())).thenReturn(item);
        when(billService.getById(any())).thenReturn(bill);
        BillItem billItemFromMap = new BillItem(1, bill, itemFromMap, 5, new BigDecimal(2), null, null, null);
        BillItem oldBillItem = new BillItem(1, bill, itemFromMap, 2, new BigDecimal(2), null, null, null);
        when(repository.findById(any())).thenReturn(oldBillItem);
        Mockito.when(repository.save(any(BillItem.class))).thenReturn(billItemFromMap);

        service.save(billItemFromMap);

        verify(repository).findById(1);
        verify(billService).getById(1);
        ArgumentCaptor<Map> argumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(elasticSearchService).saveBillItemDelta(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().get("quantity"), is(3));
    }

}