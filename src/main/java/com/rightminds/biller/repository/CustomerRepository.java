package com.rightminds.biller.repository;

import com.rightminds.biller.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

    Customer findById(Integer id);

    Customer findByName(String name);
}
