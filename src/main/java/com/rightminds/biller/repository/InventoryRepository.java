package com.rightminds.biller.repository;

import com.rightminds.biller.entity.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, String> {

    Inventory findById(Integer id);
}
