package com.rightminds.biller.service;

import com.rightminds.biller.entity.Customer;
import com.rightminds.biller.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public void save(Customer customer) {
        repository.save(customer);
    }

    public Customer getById(Integer id) {
        return repository.findById(id);
    }

    public Customer getByName(String name) {
        return repository.findByName(name);
    }

    public List<Customer> getAll() {
        return (List<Customer>) repository.findAll();
    }
}
