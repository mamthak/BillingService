package com.rightminds.biller.repository;

import com.rightminds.biller.entity.BillItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillItemRepository extends CrudRepository<BillItem, String> {

    BillItem findById(Integer id);

}
