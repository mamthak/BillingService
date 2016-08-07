package com.rightminds.biller.service;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.BillItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.rightminds.biller.AllConstants.SERVICE_CHARGE;
import static com.rightminds.biller.AllConstants.SERVICE_TAX;
import static com.rightminds.biller.util.CastUtil.getBigDecimal;
import static java.math.BigDecimal.valueOf;

@Service
public class OrderService {

    @Autowired
    private BillService billService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ItemService itemService;

    public void processBill(Bill bill) {
        BigDecimal serviceCharge = getBigDecimal(configurationService.getByKey(SERVICE_CHARGE).getValue());
        BigDecimal serviceTax = getBigDecimal(configurationService.getByKey(SERVICE_TAX).getValue());
        BigDecimal total = getTotal(bill, serviceCharge, serviceTax);

        billService.save(bill.withComputedValues(serviceCharge, serviceTax, total));
        List<BillItem> billItems = bill.getBillItems();
        for (BillItem billItem : billItems) {
            Integer orderedQuantity = billItem.getQuantity();
            itemService.reduceInventoryCount(billItem.getItem(), orderedQuantity);
        }
    }

    private BigDecimal getTotal(Bill bill, BigDecimal serviceCharge, BigDecimal serviceTax) {
        return bill.getSubTotal()
                .add(bill.getSubTotal().multiply(serviceCharge.divide(valueOf(100))))
                .add(bill.getSubTotal().multiply(serviceTax.divide(valueOf(100))))
                .subtract(bill.getDiscount());
    }

}
