package com.rightminds.biller.repository;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.model.BillStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BillRepository extends CrudRepository<Bill, String> {

    Bill findById(Integer id);

    Bill findByName(String name);

    @Modifying
    @Query("UPDATE Bill b SET name = :name WHERE b.id = :id")
    void updateName(@Param("id") Integer id, @Param("name") String name);
}
