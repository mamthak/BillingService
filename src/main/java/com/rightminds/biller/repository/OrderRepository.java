package com.rightminds.biller.repository;

import com.rightminds.biller.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {

    Order findById(Integer id);

    Order findByName(String name);
}
