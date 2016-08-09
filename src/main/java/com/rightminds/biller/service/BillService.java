package com.rightminds.biller.service;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.model.BillStatus;
import com.rightminds.biller.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rightminds.biller.model.BillStatus.IN_PROGRESS;

@Service
public class BillService {

    @Autowired
    private BillRepository repository;

    public void save(Bill bill) {
        repository.save(bill);
    }

    public Bill getById(Integer id) {
        return repository.findById(id);
    }

    public Bill getByName(String name) {
        return repository.findByName(name);
    }

    public List<Bill> getAll() {
        return (List<Bill>) repository.findAll();
    }

    public List<Bill> getOngoingBills() {
        List<Bill> allBills = (List<Bill>) repository.findAll();
        return allBills
                .stream()
                .filter(bill -> bill.getStatus().equals(IN_PROGRESS))
                .collect(Collectors.toList());
    }
}
