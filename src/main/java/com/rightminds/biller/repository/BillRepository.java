package com.rightminds.biller.repository;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.model.BillStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends CrudRepository<Bill, String> {

    Bill findById(Integer id);

    Bill findByName(String name);
}
