package com.rightminds.biller.service;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.BillItem;
import com.rightminds.biller.entity.Item;
import com.rightminds.biller.model.BillItemResponse;
import com.rightminds.biller.repository.BillItemRepository;
import com.rightminds.biller.util.CastUtil;
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

    @Autowired
    private ElasticSearchService elasticSearchService;

    public BillItemResponse save(BillItem billItem) {
        if (billItem.getQuantity() == 0) {
            BillItem billItemFromDb = repository.findById(billItem.getId());
            // TODO: Use delete column for deleting the item
            repository.delete(billItemFromDb);
            elasticSearchService.delete(billItemFromDb);
            Bill updatedBill = billService.getById(billItem.getBill().getId());
            return new BillItemResponse(billItemFromDb, null, updatedBill);
        } else {
            BillItem updatedBillItem = billItem.withTotal(getTotal(billItem));
            BillItem savedBillItem = repository.save(updatedBillItem);
            Bill bill = billService.getById(billItem.getBill().getId());
            Item item = itemService.getById(billItem.getItem().getId());
            elasticSearchService.save(bill);
            elasticSearchService.save(savedBillItem.withItemAndBill(item, bill));
            return new BillItemResponse(savedBillItem, item, bill);
        }
    }

    public BillItem getById(Integer id) {
        return repository.findById(id);
    }

    public List<BillItem> getAll() {
        return (List<BillItem>) repository.findAll();
    }

    // TODO: use JPA formula to compute the total
    private BigDecimal getTotal(BillItem billItem) {
        Item item = itemService.getById(billItem.getItem().getId());
        LOGGER.debug("Fetched the item {}", item);
        BigDecimal total = item.getPrice().multiply(getBigDecimal(billItem.getQuantity()));
        return total.subtract(getBigDecimal(billItem.getDiscount()));
    }
}
