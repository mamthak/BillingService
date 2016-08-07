package com.rightminds.biller.service;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
