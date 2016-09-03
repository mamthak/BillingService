package com.rightminds.biller.service;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.BillItem;
import com.rightminds.biller.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.rightminds.biller.AllConstants.SERVICE_CHARGE;
import static com.rightminds.biller.AllConstants.SERVICE_TAX;
import static com.rightminds.biller.model.BillStatus.IN_PROGRESS;
import static com.rightminds.biller.util.CastUtil.getBigDecimal;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

@Service
public class BillService {

    @Autowired
    private BillRepository repository;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    public Bill save(Bill bill) {
        Bill savedBill = repository.save(bill);
        elasticSearchService.save(bill);
        return savedBill;
    }

    public void updateName(Integer id, String name) {
        repository.updateName(id, name);
    }

    public void delete(Integer id) {
        Bill bill = repository.findById(id);
        repository.delete(bill);
    }

    public Bill getById(Integer id) {
        Bill bill = repository.findById(id);
        enrichBillItem(bill);
        return bill;
    }

    public Bill getByName(String name) {
        return repository.findByName(name);
    }

    public List<Bill> getAll() {
        List<Bill> bills = (List<Bill>) repository.findAll();
        enrichBills(bills);
        return bills;
    }

    public List<Bill> getOngoingBills() {
        List<Bill> allBills = (List<Bill>) repository.findAll();
        List<Bill> ongoingBills = allBills
                .stream()
                .filter(bill -> bill.getStatus().equals(IN_PROGRESS))
                .collect(Collectors.toList());
        enrichBills(ongoingBills);
        return ongoingBills;
    }

    public void processBill(Integer id) {
        Bill bill = repository.findById(id);
        BigDecimal serviceCharge = getBigDecimal(configurationService.getByKey(SERVICE_CHARGE).getValue());
        BigDecimal serviceTax = getBigDecimal(configurationService.getByKey(SERVICE_TAX).getValue());
        BigDecimal total = getTotal(bill, serviceCharge, serviceTax);

        Bill withComputedValues = bill.withComputedValues(serviceCharge, serviceTax, total);
        Bill savedBill = save(withComputedValues);
        elasticSearchService.save(savedBill);
        List<BillItem> billItems = savedBill.getBillItems();
        for (BillItem billItem : billItems) {
            Integer orderedQuantity = billItem.getQuantity();
            elasticSearchService.save(billItem);
            itemService.reduceInventoryCount(billItem.getItem(), orderedQuantity);
        }
    }

    public List<Bill> recentBills() {
        return repository.findTop10ByOrderByLastModifiedOnDesc();
    }

    private BigDecimal getTotal(Bill bill, BigDecimal serviceCharge, BigDecimal serviceTax) {
        BigDecimal discount = bill.getDiscount() == null ? ZERO : bill.getDiscount();
        return bill.getSubTotal()
                .add(bill.getSubTotal().multiply(serviceCharge.divide(valueOf(100))))
                .add(bill.getSubTotal().multiply(serviceTax.divide(valueOf(100))))
                .subtract(discount);
    }

    private void enrichBills(List<Bill> bills) {
        bills.stream().forEach(this::enrichBillItem);
    }

    private void enrichBillItem(Bill bill) {
        List<BillItem> billItems = bill.getBillItems()
                .stream().map(BillItem::withTransientData).collect(Collectors.toList());
        bill.getBillItems().clear();
        bill.getBillItems().addAll(billItems);
    }

}