package com.rightminds.biller.repository;

import com.rightminds.biller.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, String> {

    Item findById(Integer id);
}
