package com.rightminds.biller.repository;

import com.rightminds.biller.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, String> {

    OrderItem findById(Integer id);

}
