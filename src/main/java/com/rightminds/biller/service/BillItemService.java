package com.rightminds.biller.service;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.BillItem;
import com.rightminds.biller.entity.Item;
import com.rightminds.biller.model.BillItemResponse;
import com.rightminds.biller.repository.BillItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.rightminds.biller.util.CastUtil.getBigDecimal;

@Service
public class BillItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillItemService.class);

    @Autowired
    private BillItemRepository repository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private BillService billService;

    public BillItemResponse save(BillItem billItem) {
        BigDecimal total = getTotal(billItem);
        BillItem updatedBillItem = billItem.withTotal(total);
        Bill bill = billService.getById(billItem.getBill().getId());
        if (billItem.getQuantity() == 0) {
            repository.delete(billItem);
            return new BillItemResponse(billItem, null, bill);
        } else {
            BillItem savedBillItem = repository.save(updatedBillItem);
            Item item = itemService.getById(billItem.getItem().getId());
            return new BillItemResponse(savedBillItem, item, bill);
        }
    }

    public BillItem getById(Integer id) {
        return repository.findById(id);
    }

    public List<BillItem> getAll() {
        return (List<BillItem>) repository.findAll();
    }

    private BigDecimal getTotal(BillItem billItem) {
        Item item = itemService.getById(billItem.getItem().getId());
        LOGGER.debug("Fetched the item {}", item);
        BigDecimal total = item.getPrice().multiply(getBigDecimal(billItem.getQuantity()));
        return total.subtract(getBigDecimal(billItem.getDiscount()));
    }
}
